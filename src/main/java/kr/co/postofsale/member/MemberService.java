package kr.co.postofsale.member;

public interface MemberService {

    MemberDto.TOKEN login(MemberDto.LOGIN login);  //로그인 서비스

    //create
    void signUp(MemberDto.CREATE create);    //회원 가입 서비스
    boolean checkIdentity(String identity);  //Id 중복 체크 서비스

    //update
    void updateMember(MemberDto.UPDATE update); //회원 정보 수정 서비스
    void updatePassword(MemberDto.UPDATE_PASSWORD update_password); //비밀번호 변경 서비스
    boolean reCheckPassword(String password);   //비밀번호 재확인 서비스
    boolean resetPasswordCheck(MemberDto.RESET_CHECK reset_check); //비밀번호 초기화 확인 서비스
    void resetPassword(MemberDto.RESET_PASSWORD reset_password); //비밀번호 초기화 서비스

}
