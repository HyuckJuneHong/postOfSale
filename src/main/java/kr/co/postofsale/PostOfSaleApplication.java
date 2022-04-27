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
                        String[] information = new String[4];
                        String[] resultArr = new String[4];
                        information[0] = "원하는 아이디를 입력해주세요";
                        information[1] = "원하는 비밀번호를 입력해주세요.";
                        information[2] = "비밀번호를 확인해주세요.";
                        information[3] = "권한을 입력바랍니다. (선택: \"직원\" or \"매니저\")";

                        bufferedWriter.write(information[0]);
                        bufferedWriter.flush();
                        BufferedReader result1 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[0] = result1.readLine();
                        result1.close();

                        bufferedWriter.write(information[1]);
                        bufferedWriter.flush();
                        BufferedReader result2 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[1] = result2.readLine();
                        result2.close();

                        bufferedWriter.write(information[2]);
                        bufferedWriter.flush();
                        BufferedReader result3 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[2] = result3.readLine();
                        result3.close();

                        bufferedWriter.write(information[3]);
                        bufferedWriter.flush();
                        BufferedReader result4 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[3] = result4.readLine();
                        result4.close();

                        CreateDto memberCreate;

                        if(resultArr[3].equals("직원")){
                            memberCreate = CreateDto.builder()
                                    .identity(resultArr[0])
                                    .password(resultArr[1])
                                    .checkPassword(resultArr[2])
                                    .memberRole(MemberRole.ROLE_MEMBER)
                                    .build();
                        }else if(resultArr[3].equals("매니저")){
                            memberCreate = CreateDto.builder()
                                    .identity(resultArr[0])
                                    .password(resultArr[1])
                                    .checkPassword(resultArr[2])
                                    .memberRole(MemberRole.ROLE_MANAGER)
                                    .build();
                        }else{
                            String error = "회원가입 실패 (정보를 잘못 입력하셨습니다.) 처음 화면으로 돌아갑니다.";
                            bufferedWriter.write(error);
                            bufferedWriter.flush();
                            continue;
                        }
                        memberServiceImpl.signUp(memberCreate);
                    }else if(click1.equals("2")){
                        String[] information = new String[2];
                        String[] resultArr = new String[2];
                        information[0] = "삭제할 아이디를 입력해주세요";
                        information[1] = "삭제할 아이디의 비밀번호를 입력해주세요.";

                        bufferedWriter.write(information[0]);
                        bufferedWriter.flush();
                        BufferedReader result1 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[0] = result1.readLine();
                        result1.close();

                        bufferedWriter.write(information[1]);
                        bufferedWriter.flush();
                        BufferedReader result2 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[1] = result2.readLine();
                        result2.close();

                        DeleteDto memberDelete = DeleteDto.builder()
                                .identity(resultArr[0])
                                .password(resultArr[1])
                                .build();
                        memberServiceImpl.deleteMember(memberDelete);

                    }else if(click1.equals("3")){

                        String[] information = new String[2];
                        String[] resultArr = new String[2];
                        information[0] = "아이디를 입력해주세요";
                        information[1] = "비밀번호를 입력해주세요.";

                        bufferedWriter.write(information[0]);
                        bufferedWriter.flush();
                        BufferedReader result1 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[0] = result1.readLine();
                        result1.close();

                        bufferedWriter.write(information[1]);
                        bufferedWriter.flush();
                        BufferedReader result2 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[1] = result2.readLine();
                        result2.close();

                        SignInDto login = SignInDto.builder()
                                .identity(resultArr[0])
                                .password(resultArr[1])
                                .build();
                        memberServiceImpl.signIn(login);

                    }else if(click1.equals("4")){
                        String[] information = new String[4];
                        String[] resultArr = new String[4];
                        information[0] = "원하는 아이디를 입력해주세요.";
                        information[1] = "현재 비밀번호를 입력해주세요.";
                        information[2] = "변경할 비밀번호를 입력해주세요.";
                        information[3] = "변경 비밀번호를 확인해주세요.";

                        bufferedWriter.write(information[0]);
                        bufferedWriter.flush();
                        BufferedReader result1 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[0] = result1.readLine();
                        result1.close();

                        bufferedWriter.write(information[1]);
                        bufferedWriter.flush();
                        BufferedReader result2 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[1] = result2.readLine();
                        result2.close();

                        bufferedWriter.write(information[2]);
                        bufferedWriter.flush();
                        BufferedReader result3 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[2] = result3.readLine();
                        result3.close();

                        bufferedWriter.write(information[3]);
                        bufferedWriter.flush();
                        BufferedReader result4 = new BufferedReader(new InputStreamReader(System.in));
                        resultArr[3] = result4.readLine();
                        result4.close();

                        UpdatePasswordDto updatePassword = UpdatePasswordDto.builder()
                                .identity(resultArr[0])
                                .oldPassword(resultArr[1])
                                .newPassword(resultArr[2])
                                .checkPassword(resultArr[3])
                                .build();
                        memberServiceImpl.updatePassword(updatePassword);

                    }else if(click1.equals("5")){
                        String information = "권한을 바꿀 아이디를 입력해주세요.";
                        String result;
                        bufferedWriter.write(information);
                        bufferedWriter.flush();
                        BufferedReader resultBf = new BufferedReader(new InputStreamReader(System.in));
                        result = resultBf.readLine();
                        resultBf.close();

                        memberServiceImpl.updateRole(result);

                    }else if(click1.equals("6")){
                        String information = "조회할 아이디를 입력해주세요.";
                        String result;
                        bufferedWriter.write(information);
                        bufferedWriter.flush();
                        BufferedReader resultBf = new BufferedReader(new InputStreamReader(System.in));
                        result = resultBf.readLine();
                        resultBf.close();

                        memberServiceImpl.printMember(result);

                    }else if(click1.equals("7")){

                        memberServiceImpl.printAllMember();             //모두 조회
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
