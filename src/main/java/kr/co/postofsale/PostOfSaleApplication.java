package kr.co.postofsale;

import kr.co.postofsale.member.MemberRole;
import kr.co.postofsale.member.MemberServiceImpl;
import kr.co.postofsale.member.memberDto.CreateDto;
import kr.co.postofsale.member.memberDto.DeleteDto;
import kr.co.postofsale.member.memberDto.SignInDto;
import kr.co.postofsale.member.memberDto.UpdatePasswordDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class PostOfSaleApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
        MemberServiceImpl memberServiceImpl = ctx.getBean("memberServiceImpl", MemberServiceImpl.class);

        CreateDto memberCreate1 = CreateDto.builder()
                .identity("mId1")
                .password("1234")
                .checkPassword("1234")
                .memberRole(MemberRole.ROLE_MANAGER)
                .build();
        memberServiceImpl.signUp(memberCreate1);

        CreateDto memberCreate2 = CreateDto.builder()
                .identity("mId2")
                .password("1234")
                .checkPassword("1234")
                .memberRole(MemberRole.ROLE_MEMBER)
                .build();
        memberServiceImpl.signUp(memberCreate2);

        memberServiceImpl.printMember("mId1");
        memberServiceImpl.printAllMember();

        UpdatePasswordDto updatePassword1 = UpdatePasswordDto.builder()
                .identity("mId2")
                .oldPassword("1234")
                .newPassword("2345")
                .checkPassword("2345")
                .build();
        memberServiceImpl.updatePassword(updatePassword1);

        memberServiceImpl.printMember("mId2");

        memberServiceImpl.updateRole("mId1");

        DeleteDto memberDelete1 = DeleteDto.builder()
                .identity("mId2")
                .password("2345")
                .build();

        memberServiceImpl.printMember("mId1");

        SignInDto login = SignInDto.builder()
                .identity("mId1")
                .password("1234")
                .build();
        memberServiceImpl.signIn(login);

        memberServiceImpl.deleteMember(memberDelete1);
        memberServiceImpl.printMember("mId2");
    }

}
