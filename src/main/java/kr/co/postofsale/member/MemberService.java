package kr.co.postofsale.member;

import kr.co.postofsale.member.memberDto.CreateDto;
import kr.co.postofsale.member.memberDto.DeleteDto;
import kr.co.postofsale.member.memberDto.SignInDto;
import kr.co.postofsale.member.memberDto.UpdatePasswordDto;

public interface MemberService {

    void signUp(CreateDto create); //회원가입 서비스
    void deleteMember(DeleteDto delete); //회원 삭제 서비스

    void signIn(SignInDto login); //로그인 서비스

    void updatePassword(UpdatePasswordDto update); //비밀번호 수정 서비스
    void updateRole(String identity); //권한 변경 서비스

    void printMember(String identity); //회원 조회 서비스
    void printAllMember(); //총 회원 조회 서비스

}
