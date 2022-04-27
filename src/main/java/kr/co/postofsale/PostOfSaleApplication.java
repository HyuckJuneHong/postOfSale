package kr.co.postofsale;

import kr.co.postofsale.common.BadRequestException;
import kr.co.postofsale.member.MemberRole;
import kr.co.postofsale.member.MemberServiceImpl;
import kr.co.postofsale.member.memberDto.CreateDto;
import kr.co.postofsale.member.memberDto.DeleteDto;
import kr.co.postofsale.member.memberDto.SignInDto;
import kr.co.postofsale.member.memberDto.UpdatePasswordDto;
import kr.co.postofsale.product.ProductServiceImpl;
import kr.co.postofsale.product.productDto.InsertDto;
import kr.co.postofsale.record.RecordDao;
import kr.co.postofsale.sale.SalePayment;
import kr.co.postofsale.sale.SaleServiceImpl;
import kr.co.postofsale.sale.saleDto.PaymentDto;
import kr.co.postofsale.sale.saleDto.SelectDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.*;

public class PostOfSaleApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
        MemberServiceImpl memberServiceImpl = ctx.getBean("memberServiceImpl", MemberServiceImpl.class);
        ProductServiceImpl productServiceImpl = ctx.getBean("productServiceImpl", ProductServiceImpl.class);
        SaleServiceImpl saleServiceImpl = ctx.getBean("saleServiceImpl", SaleServiceImpl.class);
        RecordDao recordDao = ctx.getBean("recordDao", RecordDao.class);


        while (true){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
                String startMenu = "어서오세요. 아래에서 원하시는 서비스를 선택해주세요.\n" +
                        "1. 회원 서비스 메뉴\n" +
                        "2. 제품 서비스 메뉴\n" +
                        "3. 판매 서비스 메뉴\n" +
                        "4. 나가기 메뉴";
                bufferedWriter.write(startMenu);
                bufferedWriter.flush();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String clickMenu = bufferedReader.readLine();
                bufferedReader.close();

                if(clickMenu.equals("1")){
                    String memberMenu = "<회원 서비스>\n" +
                            "1. 회원 가입\n" +
                            "2. 회원 탈퇴\n" +
                            "3. 로그인\n" +
                            "4. 비밀번호 수정\n" +
                            "5. 권한 변경\n" +
                            "6. 회원 조회\n" +
                            "7. 총 회원 조회\n" +
                            "8. 메뉴에 없는 문자 혹은 숫자를 누르면. 다시 처음 메뉴로 갑니다.";
                    bufferedWriter.write(memberMenu);
                    bufferedWriter.flush();

                    BufferedReader bf1 = new BufferedReader(new InputStreamReader(System.in));
                    String click1 = bf1.readLine();
                    bf1.close();
                    if(click1.equals("1")){

                    }else if(click1.equals("2")){

                    }else if(click1.equals("3")){

                    }else if(click1.equals("4")){

                    }else if(click1.equals("5")){

                    }else if(click1.equals("6")){

                    }else if(click1.equals("7")){

                    }else{
                        String noMenu = "<처음으로 돌아갑니다.>";
                        bufferedWriter.write(noMenu);
                        bufferedWriter.flush();
                        continue;
                    }


                }else if(clickMenu.equals("2")){
                    String productMenu ="<제품 서비스>\n" +
                            "1. 제품 삽입\n" +
                            "2. 제품 삭제\n" +
                            "3. 제품 조회\n" +
                            "4. 총 제품 조회\n" +
                            "5. 메뉴에 없는 문자 혹은 숫자를 누르면. 다시 처음 메뉴로 갑니다.";
                    bufferedWriter.write(productMenu);
                    bufferedWriter.flush();

                    BufferedReader bf2 = new BufferedReader(new InputStreamReader(System.in));
                    String click2 = bf2.readLine();
                    bf2.close();
                    if(click2.equals("1")){

                    }else if(click2.equals("2")){

                    }else if(click2.equals("3")){

                    }else if(click2.equals("4")){

                    }else{
                        String noMenu = "<처음으로 돌아갑니다.>";
                        bufferedWriter.write(noMenu);
                        bufferedWriter.flush();
                        continue;
                    }

                }else if(clickMenu.equals("3")){
                    String saleMenu = "<판매 서비스>\n" +
                            "1. 장바구니 담기\n" +
                            "2. 장바구니에 담긴 제품 결제" +
                            "3. 메뉴에 없는 문자 혹은 숫자를 누르면. 다시 처음 메뉴로 갑니다.";
                    bufferedWriter.write(saleMenu);
                    bufferedWriter.flush();

                    BufferedReader bf3 = new BufferedReader(new InputStreamReader(System.in));
                    String click3 = bf3.readLine();
                    bf3.close();
                    if(click3.equals("1")){

                    }else if(click3.equals("2")){

                    }else{
                        String noMenu = "<처음으로 돌아갑니다.>";
                        bufferedWriter.write(noMenu);
                        bufferedWriter.flush();
                        continue;
                    }


                }else if(clickMenu.equals("4")){
                    String hello = "<이용해 주셔서 감사합니다. 안녕히 가세요.>";
                    bufferedWriter.write(hello);
                    bufferedWriter.flush();
                    break;
                }else{
                    String noMenu = "<없는 메뉴 입니다. 1, 2, 3, 4 중에 눌러 주세요.>";
                    bufferedWriter.write(noMenu);
                    bufferedWriter.flush();
                    continue;
                }

                bufferedWriter.close();

            }catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException(e.getMessage());
            }
            break;
        }
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

        //제품 삽입
        InsertDto insertDto1 = InsertDto.builder()
                .amount(20L)
                .box(5L)
                .price(1000L)
                .productName("콜라")
                .CodeName("P1")
                .build();
        productServiceImpl.insertProduct(insertDto1);

        //제품 삽입
        InsertDto insertDto2 = InsertDto.builder()
                .amount(20L)
                .box(5L)
                .price(1000L)
                .productName("콜라")
                .CodeName("P1")
                .build();
        productServiceImpl.insertProduct(insertDto2);

        //제품 삽입
        InsertDto insertDto3 = InsertDto.builder()
                .amount(20L)
                .box(5L)
                .price(1000L)
                .productName("츄러스")
                .CodeName("P2")
                .build();
        productServiceImpl.insertProduct(insertDto3);

        //제품 조회
        productServiceImpl.printAllProduct();

        //메뉴 선택
        SelectDto selectDto1 = SelectDto.builder()
                .buyAmount(10L)
                .buyCodeName("P1")
                .build();
        saleServiceImpl.addCart(selectDto1);
        saleServiceImpl.addCart(selectDto1);

        //메뉴 선택
        SelectDto selectDto2 = SelectDto.builder()
                .buyCodeName("P2")
                .buyAmount(30L)
                .build();
        saleServiceImpl.addCart(selectDto2);

        //결제 완료
        PaymentDto paymentDto = PaymentDto.builder()
                .salePayment(SalePayment.CARD)
                .build();
        saleServiceImpl.payment(paymentDto);

        //총 매출량 출력
        recordDao.maxProduct();
    }

}
