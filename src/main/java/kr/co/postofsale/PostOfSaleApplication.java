package kr.co.postofsale;

import kr.co.postofsale.member.MemberDto;
import kr.co.postofsale.member.MemberRole;
import kr.co.postofsale.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class PostOfSaleApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
        MemberServiceImpl memberServiceImpl = ctx.getBean("memberServiceImpl", MemberServiceImpl.class);

        MemberDto.CREATE memberDtoCreate1 = new MemberDto.CREATE();
        memberDtoCreate1.setIdentity("mId1");
        memberDtoCreate1.setPassword("1234");
        memberDtoCreate1.setCheckPassword("1234");
        memberDtoCreate1.setMemberRole(MemberRole.ROLE_MEMBER);
        memberServiceImpl.signUp(memberDtoCreate1);

        MemberDto.CREATE memberDtoCreate2 = new MemberDto.CREATE();
        memberDtoCreate2.setIdentity("mId2");
        memberDtoCreate2.setPassword("1234");
        memberDtoCreate2.setCheckPassword("1234");
        memberDtoCreate2.setMemberRole(MemberRole.ROLE_MANAGER);
        memberServiceImpl.signUp(memberDtoCreate2);

        memberServiceImpl.printMember("mId1");
        System.out.println();
        memberServiceImpl.printAllMember();

    }

}
