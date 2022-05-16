package kr.co.postofsale.member;

import java.util.List;

public interface MemberService {

    //common service
    MemberDto.TOKEN login(MemberDto.LOGIN login);                   //로그인
    boolean checkIdentity(String identity);                         //Id 중복 체크
    boolean reCheckPassword(String password);                       //비밀번호 재확인

    //create service
    void signUp(MemberDto.CREATE create);                           //회원 가입

    //read service
    MemberDto.READ getMemberSelf();                                 //자기 자신 정보 조회
    MemberDto.READ getMemberIdentity(String identity);              //아이디로 조회
    List<MemberDto.READ> getMemberAll();                            //모든 회원 정보 조회

    //update service
    void updateMember(MemberDto.UPDATE update);                     //회원 정보
    void updatePassword(MemberDto.UPDATE_PASSWORD update_password); //비밀번호 변경

}
