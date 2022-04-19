package kr.co.postofsale.member;

public interface MemberService {

    public void signUp(MemberDto.CREATE create); //회원가입 서비스
    public void updatePassword(MemberDto.UPDATE update); //비밀번호 수정 서비스
    public void deleteMember(MemberDto.DELETE delete); //회원 삭제 서비스
    public void printMember(String identity); //회원 조회 서비스
    public void printAllMember(); //총 회원 조회 서비스

}
