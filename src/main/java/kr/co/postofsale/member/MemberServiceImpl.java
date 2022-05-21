package kr.co.postofsale.member;

import kr.co.postofsale.infrastructure.exception.BadRequestException;
import kr.co.postofsale.infrastructure.exception.NotFoundException;
import kr.co.postofsale.infrastructure.interceptor.MemberThreadLocal;
import kr.co.postofsale.infrastructure.security.jwt.JwtTokenProvider;
import kr.co.postofsale.member.enumClass.Gender;
import kr.co.postofsale.member.enumClass.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepositoryImpl;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        memberRepositoryImpl = memberRepository;
    }

    /**
     * 로그인 서비스
     * @param login
     * @return
     */
    @Override
    public MemberDto.TOKEN login(MemberDto.LOGIN login){

        MemberEntity memberEntity = memberRepositoryImpl.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new NotFoundException("MemberEntity"));

        //boolean matches(rP, eP) : 저장소에서 얻은 인코딩된 암호도 인코딩된 원시 암호화 일치하는지 확인하는 메소드 (절대 디코딩되지 않음)
        if(!passwordEncoder.matches(login.getPassword(), memberEntity.getPassword())){
            throw new BadRequestException("비밀번호 일치하지 않음");
        }

        String[] tokens = generateToken(memberEntity);

        memberEntity.updateAccessToken(tokens[0]);
        memberEntity.updateRefreshToken(tokens[1]);

        return new MemberDto.TOKEN(tokens[0], tokens[1]);
    }

    /**
     * 아이디 중복 체크
     * @param identity
     * @return
     */
    @Override
    public Boolean checkIdentity(String identity){

        Boolean exist = memberRepositoryImpl.existByIdentity(identity);

        return exist;
    }


    /**
     * 비밀번호 재확인 메소드
     * @param password
     * @return
     */
    @Override
    public Boolean reCheckPassword(String password){
        //boolean matches(rP, eP) : 저장소에서 얻은 인코딩된 암호도 인코딩된 원시 암호화 일치하는지 확인하는 메소드 (절대 디코딩되지 않음)
        return passwordEncoder.matches(password, MemberThreadLocal.get().getPassword());
    }

    /**
     * 회원 가입 서비스
     * @param create
     * @return
     */
    @Override
    @Transactional
    public void signUp(MemberDto.CREATE create){

        Boolean exist = memberRepositoryImpl.existByPhone(create.getPhone());

        if(checkIdentity(create.getIdentity())){
            throw new BadRequestException("ID 중복");
        }
        if(exist){
            throw new BadRequestException("전화번호 중복");
        }
        if(!create.getPassword().equals(create.getCheckPassword())){
            throw new BadRequestException("비밀번호와 확인 비밀번호가 서로 다릅니다.");
        }

        MemberEntity memberEntity = MemberEntity.builder()
                .identity(create.getIdentity())
                .password(passwordEncoder.encode(create.getPassword()))
                .name(create.getName())
                .gender(Gender.of(create.getGender()))
                .birth(create.getBirth())
                .phone(create.getPhone())
                .memberRole(MemberRole.of(create.getMemberRole()))
                .build();

        memberEntity.setInsertDate(new Timestamp(System.currentTimeMillis()));

        memberRepositoryImpl.save(memberEntity);
    }

    /**
     * 자기 자신 정보 조회
     * @return
     */
    public MemberDto.READ getMemberSelf(){
        MemberEntity memberEntity = MemberThreadLocal.get();

        MemberDto.READ member = MemberDto.READ.builder()
                .identity(memberEntity.getIdentity())
                .name(memberEntity.getName())
                .birth(memberEntity.getBirth())
                .gender(memberEntity.getGender())
                .memberRole(memberEntity.getMemberRole())
                .phone(memberEntity.getPhone())
                .build();

        return member;
    }

    /**
     * 해당 아이디 회원 조회
     * @param identity
     * @return
     */
    @Override
    public MemberDto.READ getMemberIdentity(String identity) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(!memberEntity.getMemberRole().equals(MemberRole.ROLE_MANAGER)
                || !memberEntity.getMemberRole().equals(MemberRole.ROLE_ADMIN)){
            throw new BadRequestException("권한이 없습니다. (매니저 혹은 관리자만 가능)");
        }

        Optional<MemberEntity> member = memberRepositoryImpl.findByIdentity(identity);

        if(!member.isPresent()){
            throw new BadRequestException("해당 아이디를 가진 회원은 존재하지 않습니다.");
        }

        MemberDto.READ memberIdentity = MemberDto.READ.builder()
                .identity(member.get().getIdentity())
                .name(member.get().getName())
                .birth(member.get().getBirth())
                .gender(member.get().getGender())
                .memberRole(member.get().getMemberRole())
                .phone(member.get().getPhone())
                .build();

        return memberIdentity;
    }

    /**
     * 모든 회원 조회
     * @return
     */
    @Override
    public List<MemberDto.READ> getMemberAll() {
        List<MemberEntity> memberList = memberRepositoryImpl.findAll();

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(!memberEntity.getMemberRole().equals(MemberRole.ROLE_MANAGER)
                || !memberEntity.getMemberRole().equals(MemberRole.ROLE_ADMIN)){
            throw new BadRequestException("권한이 없습니다. (매니저 혹은 관리자만 가능)");
        }

        List<MemberDto.READ> memberReadList = new ArrayList<>();
        for(MemberEntity list : memberList){
            MemberDto.READ member = MemberDto.READ.builder()
                    .identity(list.getIdentity())
                    .name(list.getName())
                    .birth(list.getBirth())
                    .gender(list.getGender())
                    .memberRole(list.getMemberRole())
                    .phone(list.getPhone())
                    .build();
            memberReadList.add(member);
        }

        return memberReadList;
    }

    /**
     * 고객 정보 수정 서비스
     * @param update
     * @return
     */
    @Override
    @Transactional
    public void updateMember(MemberDto.UPDATE update){

        MemberEntity memberEntity = MemberThreadLocal.get();
        memberEntity.updateMember(update);
        memberRepositoryImpl.update(memberEntity);
    }

    /**
     * 비밀번호 변경 서비스
     * @param update_password
     * @return
     */
    @Override
    @Transactional
    public void updatePassword(MemberDto.UPDATE_PASSWORD update_password) {

        MemberEntity memberEntity = MemberThreadLocal.get();
        if(!passwordEncoder.matches(update_password.getPassword(), memberEntity.getPassword())){
            throw new BadRequestException("비밀번호가 올바르지 않습니다.");
        }

        if(!update_password.getNewPassword().equals(update_password.getReNewPassword())){
            throw new BadRequestException("새 비밀번호가 일치하지 않습니다.");
        }

        memberEntity.updatePassword(passwordEncoder.encode(update_password.getNewPassword()));
        memberRepositoryImpl.update(memberEntity);
    }

    /**
     * 회원 권한 정보 변경
     * @param update_role
     */
    @Override
    @Transactional
    public void updateMemberRoLe(MemberDto.UPDATE_ROLE update_role) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(!memberEntity.getMemberRole().equals(MemberRole.ROLE_ADMIN)){
            throw new BadRequestException("관리자만 권한을 변경할 수 있습니다.");
        }

        Optional<MemberEntity> member = memberRepositoryImpl.findByIdentity(update_role.getIdentity());

        if(!member.isPresent()){
            throw new BadRequestException("해당 아이디를 가진 회원은 존재하지 않습니다.");
        }

        MemberEntity memberEntity1 = member.get();
        if(update_role.getMemberRole().equals(MemberRole.ROLE_MEMBER)){
            memberEntity1.updateRole(MemberRole.ROLE_MEMBER);
        }else if(update_role.getMemberRole().equals(MemberRole.ROLE_MANAGER)){
            memberEntity1.updateRole(MemberRole.ROLE_MANAGER);
        }else{
            throw new BadRequestException("없는 권한입니다.");
        }

        memberRepositoryImpl.update(memberEntity1);
    }

    @Override
    public void deleteMember(MemberDto.DELETE delete) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        Optional<MemberEntity> member = memberRepositoryImpl.findByIdentity(delete.getIdentity());

        if(member.isPresent()){
            throw new BadRequestException("삭제할 아이디와 정보가 일치하지 않습니다.");
        }

        if(!memberEntity.getIdentity().equals(member.get().getIdentity())){
            throw new BadRequestException("삭제할 아이디와 정보가 일치하지 않습니다.");
        }

        if(!memberEntity.getPassword().equals(member.get().getPassword())){
            throw new BadRequestException("삭제할 아이디와 정보가 일치하지 않습니다.");
        }

        memberRepositoryImpl.update(memberEntity);

    }

    @Override
    public void deleteAll() {
        //to do
    }


    /**
     * 토큰 발급 서비스
     * @param memberEntity
     * @return
     */
    private String[] generateToken(MemberEntity memberEntity) {
        String accessToken = jwtTokenProvider.createAccessToken(memberEntity.getIdentity()
                , memberEntity.getMemberRole(), memberEntity.getName());
        String refreshToken = jwtTokenProvider.createRefreshToken(memberEntity.getIdentity()
                , memberEntity.getMemberRole(), memberEntity.getName());

        return new String[]{accessToken, refreshToken};
    }
}
