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
        memberServiceImpl.printAllMember();

        MemberDto.UPDATE update1 = new MemberDto.UPDATE();
        update1.setIdentity("mId2");
        update1.setOldPassword("1234");
        update1.setNewPassword("2345");
        update1.setCheckPassword("2345");
        memberServiceImpl.updatePassword(update1);

        memberServiceImpl.printMember("mId2");

        MemberDto.DELETE delete1 = new MemberDto.DELETE();
        delete1.setIdentity("mId2");
        delete1.setPassword("2345");

        memberServiceImpl.deleteMember(delete1);
        memberServiceImpl.printMember("mId2");
    }

}
