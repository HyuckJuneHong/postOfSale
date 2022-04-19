package kr.co.postofsale.member;

import kr.co.postofsale.common.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class MemberServiceImpl implements MemberService{

    private MemberDao memberDao;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void signUp(MemberDto.CREATE create) {
        MemberEntity member = memberDao.findByMember(create.getIdentity());

        if(member != null){
            throw new BadRequestException("해당 아이디는 이미 존재하는 아이디입니다.");
        }

        if(!create.getCheckPassword().equals(create.getPassword())){
            throw new BadRequestException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        MemberEntity newMember = new MemberEntity(
                create.getIdentity(), create.getPassword(), create.getMemberRole());

        memberDao.insertMember(newMember);
        System.out.println("회원가입 완료");

    }

    @Override
    public void updatePassword(MemberDto.UPDATE update) {
        MemberEntity member = memberDao.findByMember(update.getIdentity());

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
        System.out.println("비밀번호가 변경되었습니다.");
    }

    @Override
    public void deleteMember(MemberDto.DELETE delete) {
        MemberEntity member = memberDao.findByMember(delete.getIdentity());

        if(member == null){
            throw new BadRequestException("해당 아이디는 존재하지 않습니다.");
        }
        if(!member.getPassword().equals(delete.getPassword())){
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        memberDao.deleteMember(member);
        System.out.println("아이디 삭제 완료");
    }

    @Override
    public void printMember(String identity) {
        MemberEntity member = memberDao.findByMember(identity);
        if(member == null){
            throw new BadRequestException("해당 아이디는 존재하지 않습니다.");
        }

        System.out.println("멤버 아이디: " + member.getIdentity() + "\n멤버 권한: " + member.getMemberRole());
    }

    @Override
    public void printAllMember() {

        ArrayList<MemberEntity> list =  new ArrayList<>(memberDao.findAllMember());

        for(MemberEntity member : list){
            System.out.println("멤버 아이디: " + member.getIdentity() + "\n멤버 권한: " + member.getMemberRole());
        }
        System.out.println("총 직원 수: " + list.size());

    }


}
