package kr.co.postofsale.member;

import java.util.List;

public interface MemberService {

    //common service
    MemberDto.TOKEN login(MemberDto.LOGIN login);                   //로그인
    Boolean checkIdentity(String identity);                         //Id 중복 체크
    Boolean reCheckPassword(String password);                       //비밀번호 재확인

    //create service
    void signUp(MemberDto.CREATE create);                           //회원 가입

    //read service
    MemberDto.READ getMemberSelf();                                 //자기 자신 정보 조회
    MemberDto.READ getMemberIdentity(String identity);              //아이디로 조회
    List<MemberDto.READ> getMemberAll();                            //모든 회원 정보 조회

    //update service
    void updateMember(MemberDto.UPDATE update);                     //자신의 회원 정보 변경
    void updatePassword(MemberDto.UPDATE_PASSWORD update_password); //비밀번호 변경
    void updateMemberRoLe(MemberDto.UPDATE_ROLE update_role);       //권한 변경

    //delete service
    void deleteMember(MemberDto.DELETE delete);                     //회원 탈퇴

}
