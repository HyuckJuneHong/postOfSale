package kr.co.postofsale;

import kr.co.postofsale.member.MemberRole;
import kr.co.postofsale.member.MemberServiceImpl;
import kr.co.postofsale.member.memberDto.CreateDto;
import kr.co.postofsale.member.memberDto.DeleteDto;
import kr.co.postofsale.member.memberDto.SignInDto;
import kr.co.postofsale.member.memberDto.UpdatePasswordDto;
import kr.co.postofsale.product.ProductServiceImpl;
import kr.co.postofsale.product.productDto.InsertDto;
import kr.co.postofsale.sale.SalePayment;
import kr.co.postofsale.sale.SaleServiceImpl;
import kr.co.postofsale.sale.saleDto.PaymentDto;
import kr.co.postofsale.sale.saleDto.SelectDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class PostOfSaleApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
        MemberServiceImpl memberServiceImpl = ctx.getBean("memberServiceImpl", MemberServiceImpl.class);
        ProductServiceImpl productServiceImpl = ctx.getBean("productServiceImpl", ProductServiceImpl.class);
        SaleServiceImpl saleServiceImpl = ctx.getBean("saleServiceImpl", SaleServiceImpl.class);

        //회원가입1
        CreateDto memberCreate1 = CreateDto.builder()
                .identity("mId1")
                .password("1234")
                .checkPassword("1234")
                .memberRole(MemberRole.ROLE_MANAGER)
                .build();
        memberServiceImpl.signUp(memberCreate1);

        //회원가입2
        CreateDto memberCreate2 = CreateDto.builder()
                .identity("mId2")
                .password("1234")
                .checkPassword("1234")
                .memberRole(MemberRole.ROLE_MANAGER)
                .build();
        memberServiceImpl.signUp(memberCreate2);

        memberServiceImpl.printMember("mId1");  //조회
        memberServiceImpl.printAllMember();             //모두 조회

        //비밀번호 변경
        UpdatePasswordDto updatePassword1 = UpdatePasswordDto.builder()
                .identity("mId2")
                .oldPassword("1234")
                .newPassword("2345")
                .checkPassword("2345")
                .build();
        memberServiceImpl.updatePassword(updatePassword1);

        memberServiceImpl.printMember("mId2");  //조회
        memberServiceImpl.updateRole("mId1");   //권한 변경
        memberServiceImpl.printMember("mId1");  //조회

        //로그인
        SignInDto login = SignInDto.builder()
                .identity("mId1")
                .password("1234")
                .build();
        memberServiceImpl.signIn(login);

        //아이디 삭제
        DeleteDto memberDelete1 = DeleteDto.builder()
                .identity("mId2")
                .password("2345")
                .build();
        memberServiceImpl.deleteMember(memberDelete1);

        //        memberServiceImpl.printMember("mId2");
        
        InsertDto insertDto1 = InsertDto.builder()
                .amount(20L)
                .box(5L)
                .price(1000L)
                .productName("콜라")
                .CodeName("P1")
                .build();
        productServiceImpl.insertProduct(insertDto1);

        InsertDto insertDto2 = InsertDto.builder()
                .amount(20L)
                .box(5L)
                .price(1000L)
                .productName("콜라")
                .CodeName("P1")
                .build();
        productServiceImpl.insertProduct(insertDto2);

        InsertDto insertDto3 = InsertDto.builder()
                .amount(20L)
                .box(5L)
                .price(1000L)
                .productName("츄러스")
                .CodeName("P2")
                .build();
        productServiceImpl.insertProduct(insertDto3);

        productServiceImpl.printAllProduct();

        SelectDto selectDto1 = SelectDto.builder()
                .buyAmount(10L)
                .buyCodeName("P1")
                .build();
        saleServiceImpl.addCart(selectDto1);
        saleServiceImpl.addCart(selectDto1);

        SelectDto selectDto2 = SelectDto.builder()
                .buyCodeName("P2")
                .buyAmount(20L)
                .build();
        saleServiceImpl.addCart(selectDto2);

        PaymentDto paymentDto = PaymentDto.builder()
                .salePayment(SalePayment.CARD)
                .build();
        saleServiceImpl.payment(paymentDto);
    }

}
