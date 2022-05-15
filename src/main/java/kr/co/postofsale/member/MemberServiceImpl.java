package kr.co.postofsale.member;

import kr.co.postofsale.infrastructure.exception.BadRequestException;
import kr.co.postofsale.infrastructure.exception.NotFoundException;
import kr.co.postofsale.infrastructure.interceptor.MemberThreadLocal;
import kr.co.postofsale.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepositoryImpl memberRepositoryImpl;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        memberEntity.updateRefreshToken(tokens[1]);

        return new MemberDto.TOKEN(tokens[0], tokens[1]);
    }

    /**
     * 아이디 중복 체크
     * @param identity
     * @return
     */
    @Override
    public boolean checkIdentity(String identity){
        return memberRepositoryImpl.existsByIdentity(identity);
    }


    /**
     * 비밀번호 재확인 메소드
     * @param password
     * @return
     */
    @Override
    public boolean reCheckPassword(String password){
        //boolean matches(rP, eP) : 저장소에서 얻은 인코딩된 암호도 인코딩된 원시 암호화 일치하는지 확인하는 메소드 (절대 디코딩되지 않음)
        return passwordEncoder.matches(password, MemberThreadLocal.get().getPassword());
    }

    /**
     * 비밀번호 초기화 확인 메서드
     * @param reset
     * @return
     */
    @Override
    public boolean resetPasswordCheck(MemberDto.RESET_CHECK reset){
        return memberRepositoryImpl.existsByIdentityAndNameAndBirth(reset.getIdentity()
                , reset.getName(), reset.getBirth());
    }

    /**
     * 회원 가입 서비스
     * @param create
     * @return
     */
    @Override
    public void signUp(MemberDto.CREATE create){

        if(checkIdentity(create.getIdentity())){
            throw new BadRequestException("중복");
        }

        memberRepositoryImpl.save(MemberEntity.builder()
                .identity(create.getIdentity())
                .password(create.getPassword())
                .name(create.getName())
                .gender(create.getGender())
                .birth(create.getBirth())
                .phone(create.getPhone())
                .memberRole(create.getMemberRole())
                .build());
    }

//    //To do
//    public MemberDto.READ getMember(){
//        Member member = MemberThreadLocal.get();
//    }
//
//    //To do
//    public List<String> findIdentity(MemberDto.ID_READ read){
//        return memberDao.findByNameAndBirth(read.getName(), read.getBirth())
//    }
//    //To do
//    public Member getMember(String identity){
//        return memberDao.findByIdentity(identity).orEleThrow() -> new NotFoundException("Member");
//    }

    /**
     * 고객 정보 수정 서비스
     * @param update
     * @return
     */
    @Override
    public void updateMember(MemberDto.UPDATE update){

        MemberEntity memberEntity = MemberThreadLocal.get();
        memberEntity.updateMember(update);
        memberRepositoryImpl.save(memberEntity);
    }

    /**
     * 비밀번호 변경 서비스
     * @param update_password
     * @return
     */
    @Override
    public void updatePassword(MemberDto.UPDATE_PASSWORD update_password) {

        MemberEntity memberEntity = MemberThreadLocal.get();
        if(!passwordEncoder.matches(update_password.getPassword(), memberEntity.getPassword())){
            throw new BadRequestException("비밀번호가 올바르지 않습니다.");
        }

        if(!update_password.getNewPassword().equals(update_password.getReNewPassword())){
            throw new BadRequestException("새 비밀번호가 일치하지 않습니다.");
        }

        memberEntity.updatePassword(passwordEncoder.encode(update_password.getNewPassword()));
        memberRepositoryImpl.save(memberEntity);
    }

    /**
     * 비밀번호 초기화 메소드
     * @param reset_password
     */
    @Override
    public void resetPassword(MemberDto.RESET_PASSWORD reset_password) {
        if(!reset_password.getNewPassword().equals(reset_password.getReNewPassword())) {
            throw new BadRequestException("변경하려는 비밀번호가 서로 일치하지 않습니다.");
        }

        MemberEntity memberEntity = memberRepositoryImpl.findByIdentity(reset_password.getIdentity())
                .orElseThrow(() -> new NotFoundException("MemberEntity"));
        memberEntity.updatePassword(passwordEncoder.encode(reset_password.getNewPassword()));
        memberRepositoryImpl.save(memberEntity);
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
