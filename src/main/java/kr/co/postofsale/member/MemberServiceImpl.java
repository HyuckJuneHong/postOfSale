package kr.co.postofsale.member;

import kr.co.postofsale.common.BadRequestException;
import kr.co.postofsale.member.memberDto.CreateDto;
import kr.co.postofsale.member.memberDto.DeleteDto;
import kr.co.postofsale.member.memberDto.SignInDto;
import kr.co.postofsale.member.memberDto.UpdatePasswordDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class MemberServiceImpl implements MemberService{

    private MemberDao memberDao;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void signUp(CreateDto create) {
        MemberEntity member = memberDao.findByMember(create.getIdentity());

        System.out.println("\n<회원가입 서비스>");
        if(member != null){
            throw new BadRequestException("해당 아이디는 이미 존재하는 아이디입니다.");
        }

        if(!create.getCheckPassword().equals(create.getPassword())){
            throw new BadRequestException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        MemberEntity newMember = new MemberEntity(
                create.getIdentity(), create.getPassword(), create.getMemberRole());

        memberDao.insertMember(newMember);
        System.out.println("[아이디: " + newMember.getIdentity() + " 회원가입 완료]");

    }

    @Override
    public void updatePassword(UpdatePasswordDto update) {
        MemberEntity member = memberDao.findByMember(update.getIdentity());

        System.out.println("\n<비밀번호 변경 서비스>");
        if(member == null){
            throw new BadRequestException("해당 아이디는 존재하지 않습니다.");
        }

        if(!member.getPassword().equals(update.getOldPassword())){
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        if(member.getPassword().equals(update.getNewPassword())){
            throw new BadRequestException("예전 비밀번호와 일치합니다.");
        }

        if(!update.getNewPassword().equals(update.getCheckPassword())){
            throw new BadRequestException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(update.getNewPassword());

        memberDao.updateMember(member);
        System.out.println("[" + member.getIdentity() + "님의 비밀번호가 변경되었습니다.]");
    }

    @Override
    public void updateRole(String identity) {
        MemberEntity member = memberDao.findByMember(identity);

        System.out.println("\n<권한 변경 서비스>");
        if(member == null){
            throw new BadRequestException("해당 아이디는 존재하지 않습니다.");
        }

        if(member.getMemberRole().equals(MemberRole.ROLE_MEMBER)) {
            member.updateRole(MemberRole.ROLE_MANAGER);
        }
        if(member.getMemberRole().equals(MemberRole.ROLE_MANAGER)){
            member.updateRole(MemberRole.ROLE_MEMBER);
        }
        System.out.println("[" + member.getIdentity() + "님의 권한이 변경되었습니다.]");
        memberDao.updateMember(member);

    }

    @Override
    public void deleteMember(DeleteDto delete) {
        MemberEntity member = memberDao.findByMember(delete.getIdentity());

        System.out.println("\n<삭제 서비스>");
        if(member == null){
            throw new BadRequestException("해당 아이디는 존재하지 않습니다.");
        }
        if(!member.getPassword().equals(delete.getPassword())){
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        memberDao.deleteMember(member);
        System.out.println("[아이디 :" + member.getIdentity() + "삭제 완료]");
    }

    @Override
    public void signIn(SignInDto login) {
        MemberEntity member = memberDao.findByMember(login.getIdentity());

        System.out.println("\n<로그인 서비스>");
        if(member == null){
            throw new BadRequestException("존재하지 않는 아이디입니다.");
        }

        if(!member.getPassword().equals(login.getPassword())){
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        System.out.println("[" + member.getIdentity() + "님이 로그인하셨습니다.]");
    }

    @Override
    public void printMember(String identity) {
        MemberEntity member = memberDao.findByMember(identity);
        System.out.println("\n<직원 조회 서비스>");
        if(member == null){
            throw new BadRequestException("해당 아이디는 존재하지 않습니다.");
        }

        System.out.println("멤버 코드: " + member.getCode() + "\n멤버 아이디: " + member.getIdentity() + "\n멤버 권한: " + member.getMemberRole());
    }

    @Override
    public void printAllMember() {

        ArrayList<MemberEntity> list =  new ArrayList<>(memberDao.findAllMember());

        System.out.println("\n<총 직원 조회 서비스>");
        System.out.println("---------------------------");
        for(MemberEntity member : list){
            System.out.println("멤버 코드: " + member.getCode() + "\n멤버 아이디: " + member.getIdentity() + "\n멤버 권한: " + member.getMemberRole());
            System.out.println("---------------------------");
        }
        System.out.println("[총 직원 수: " + list.size() + "]");
        System.out.println("---------------------------");

    }


}
