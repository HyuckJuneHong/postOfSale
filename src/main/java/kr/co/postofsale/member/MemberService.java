package kr.co.postofsale.member;

public interface MemberService {

    //common service
    MemberDto.TOKEN login(MemberDto.LOGIN login);                   //로그인
    boolean checkIdentity(String identity);                         //Id 중복 체크
    boolean reCheckPassword(String password);                       //비밀번호 재확인
    boolean resetPasswordCheck(MemberDto.RESET_CHECK reset_check);  //비밀번호 초기화 확인

    //create service
    void signUp(MemberDto.CREATE create);                           //회원 가입

    //read service

    //update service
    void updateMember(MemberDto.UPDATE update);                     //회원 정보
    void updatePassword(MemberDto.UPDATE_PASSWORD update_password); //비밀번호 변경
    void resetPassword(MemberDto.RESET_PASSWORD reset_password);    //비밀번호 초기화

}
