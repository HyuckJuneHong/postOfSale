//package kr.co.postofsale;
//
//import kr.co.postofsale.infrastructure.exception.BadRequestException;
//import kr.co.postofsale.member.enumClass.MemberRole;
//import kr.co.postofsale.member.MemberServiceImpl;
//import kr.co.postofsale.member.memberDto.CreateDto;
//import kr.co.postofsale.member.memberDto.DeleteDto;
//import kr.co.postofsale.member.memberDto.SignInDto;
//import kr.co.postofsale.member.memberDto.UpdatePasswordDto;
//import kr.co.postofsale.product.ProductServiceImpl;
//import kr.co.postofsale.product.productDto.InsertDto;
//import kr.co.postofsale.record.RecordDao;
//import kr.co.postofsale.sale.enumClass.SalePayment;
//import kr.co.postofsale.sale.SaleServiceImpl;
//import kr.co.postofsale.sale.saleDto.PaymentDto;
//import kr.co.postofsale.sale.saleDto.SelectDto;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//
//import java.io.*;
/////
//public class PostOfSaleApplication {
//
//    public static void main(String[] args) {
//
//        ApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
//        MemberServiceImpl memberServiceImpl = ctx.getBean("memberServiceImpl", MemberServiceImpl.class);
//        ProductServiceImpl productServiceImpl = ctx.getBean("productServiceImpl", ProductServiceImpl.class);
//        SaleServiceImpl saleServiceImpl = ctx.getBean("saleServiceImpl", SaleServiceImpl.class);
//        RecordDao recordDao = ctx.getBean("recordDao", RecordDao.class);
//
//
//            try {
//
//                File memberFile = new File("/Users/hongyeongjune/postofsale/member.txt");
//                File productFile = new File("/Users/hongyeongjune/postofsale/product.txt");
//                File recordFile = new File("/Users/hongyeongjune/postofsale/record.txt");
//
//                if (!memberFile.exists()) {
//                    memberFile.createNewFile();
//                }
//                if (!productFile.exists()) {
//                    productFile.createNewFile();
//                }
//                if (!recordFile.exists()) {
//                    recordFile.createNewFile();
//                }
//
//                FileWriter memberFw = new FileWriter(memberFile);
//                FileWriter productFw = new FileWriter(productFile);
//                FileWriter recordFw = new FileWriter(recordFile);
//
//                BufferedWriter memberBw = new BufferedWriter(memberFw);
//                BufferedWriter productBw = new BufferedWriter(productFw);
//                BufferedWriter recordBw = new BufferedWriter(recordFw);
//
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
//                String startMenu = "\n어서오세요. 아래에서 원하시는 서비스를 선택해주세요.\n" +
//                        "1. 회원 서비스 메뉴\n" +
//                        "2. 제품 서비스 메뉴\n" +
//                        "3. 판매 서비스 메뉴\n" +
//                        "4. 통계 서비스 메뉴\n" +
//                        "5. 나가기 메뉴";
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//
//                while (true) {
//
//                    bufferedWriter.write(startMenu);
//                    bufferedWriter.flush();
//
//                    String clickMenu = bufferedReader.readLine();
//
//                    if (clickMenu.equals("1")) {
//                        String memberMenu = "<회원 서비스>\n" +
//                                "1. 회원 가입\n" +
//                                "2. 회원 탈퇴\n" +
//                                "3. 로그인\n" +
//                                "4. 비밀번호 수정\n" +
//                                "5. 권한 변경\n" +
//                                "6. 회원 조회\n" +
//                                "7. 총 회원 조회\n" +
//                                "8. 메뉴에 없는 문자 혹은 숫자를 누르면. 다시 처음 메뉴로 갑니다.";
//                        bufferedWriter.write(memberMenu);
//                        bufferedWriter.flush();
//
//                        BufferedReader bf1 = new BufferedReader(new InputStreamReader(System.in));
//                        String click1 = bf1.readLine();
//
//                        if (click1.equals("1")) {
//                            String[] information = new String[4];
//                            String[] resultArr = new String[4];
//                            information[0] = "\n원하는 아이디를 입력해주세요";
//                            information[1] = "원하는 비밀번호를 입력해주세요.";
//                            information[2] = "비밀번호를 확인해주세요.";
//                            information[3] = "권한을 입력바랍니다. (선택: \"member\" or \"manager\")";
//
//                            for (int i = 0; i < information.length; i++) {
//                                bufferedWriter.write(information[i]);
//                                bufferedWriter.flush();
//                                BufferedReader result = new BufferedReader(new InputStreamReader(System.in));
//                                resultArr[i] = result.readLine();
//                            }
//
//                            CreateDto memberCreate;
//
//                            if (resultArr[3].equals("member")) {
//                                memberCreate = CreateDto.builder()
//                                        .identity(resultArr[0])
//                                        .password(resultArr[1])
//                                        .checkPassword(resultArr[2])
//                                        .memberRole(MemberRole.ROLE_MEMBER)
//                                        .build();
//                            } else if (resultArr[3].equals("manager")) {
//                                memberCreate = CreateDto.builder()
//                                        .identity(resultArr[0])
//                                        .password(resultArr[1])
//                                        .checkPassword(resultArr[2])
//                                        .memberRole(MemberRole.ROLE_MANAGER)
//                                        .build();
//                            } else {
//                                String error = "\n회원가입 실패 (정보를 잘못 입력하셨습니다.) 처음 화면으로 돌아갑니다.";
//                                bufferedWriter.write(error);
//                                bufferedWriter.flush();
//                                continue;
//                            }
//
//                            memberServiceImpl.signUp(memberCreate);
//                            memberBw.write("<회원가입>\nID: " + memberCreate.getIdentity() + "\nPW: "
//                                    + memberCreate.getPassword() + "\nROLE: "
//                                    + memberCreate.getMemberRole() + "\n\n");
//                            memberBw.flush();
//                            continue;
//
//                        } else if (click1.equals("2")) {
//                            String[] information = new String[2];
//                            String[] resultArr = new String[2];
//                            information[0] = "\n삭제할 아이디를 입력해주세요";
//                            information[1] = "삭제할 아이디의 비밀번호를 입력해주세요.";
//
//                            for (int i = 0; i < information.length; i++) {
//                                bufferedWriter.write(information[i]);
//                                bufferedWriter.flush();
//                                BufferedReader result = new BufferedReader(new InputStreamReader(System.in));
//                                resultArr[i] = result.readLine();
//                            }
//
//                            DeleteDto memberDelete = DeleteDto.builder()
//                                    .identity(resultArr[0])
//                                    .password(resultArr[1])
//                                    .build();
//                            memberServiceImpl.deleteMember(memberDelete);
//                            memberBw.write("<회원 탈퇴>\nID: " + memberDelete.getIdentity() + "\n\n");
//                            memberBw.flush();
//                            continue;
//
//                        } else if (click1.equals("3")) {
//
//                            String[] information = new String[2];
//                            String[] resultArr = new String[2];
//                            information[0] = "\n아이디를 입력해주세요";
//                            information[1] = "비밀번호를 입력해주세요.";
//
//                            for (int i = 0; i < information.length; i++) {
//                                bufferedWriter.write(information[i]);
//                                bufferedWriter.flush();
//                                BufferedReader result = new BufferedReader(new InputStreamReader(System.in));
//                                resultArr[i] = result.readLine();
//                            }
//
//                            SignInDto login = SignInDto.builder()
//                                    .identity(resultArr[0])
//                                    .password(resultArr[1])
//                                    .build();
//                            memberServiceImpl.signIn(login);
//                            memberBw.write("<로그인>\nID: " + login.getIdentity() + "\n\n");
//                            memberBw.flush();
//
//                            continue;
//
//                        } else if (click1.equals("4")) {
//                            String[] information = new String[4];
//                            String[] resultArr = new String[4];
//                            information[0] = "\n원하는 아이디를 입력해주세요.";
//                            information[1] = "현재 비밀번호를 입력해주세요.";
//                            information[2] = "변경할 비밀번호를 입력해주세요.";
//                            information[3] = "변경 비밀번호를 확인해주세요.";
//
//                            for (int i = 0; i < information.length; i++) {
//                                bufferedWriter.write(information[i]);
//                                bufferedWriter.flush();
//                                BufferedReader result = new BufferedReader(new InputStreamReader(System.in));
//                                resultArr[i] = result.readLine();
//                            }
//
//                            UpdatePasswordDto updatePassword = UpdatePasswordDto.builder()
//                                    .identity(resultArr[0])
//                                    .oldPassword(resultArr[1])
//                                    .newPassword(resultArr[2])
//                                    .checkPassword(resultArr[3])
//                                    .build();
//                            memberServiceImpl.updatePassword(updatePassword);
//                            memberBw.write("<비번 변경>\nID: " + updatePassword.getIdentity() + "\nOld PW: "
//                                    + updatePassword.getOldPassword() + "\nNew PW: "
//                                    + updatePassword.getNewPassword() + "\n\n");
//                            memberBw.flush();
//                            continue;
//
//                        } else if (click1.equals("5")) {
//                            String information = "\n권한을 바꿀 아이디를 입력해주세요.";
//                            String result;
//                            bufferedWriter.write(information);
//                            bufferedWriter.flush();
//                            BufferedReader resultBf = new BufferedReader(new InputStreamReader(System.in));
//                            result = resultBf.readLine();
//
//                            memberServiceImpl.updateRole(result);
//                            memberBw.write("<권한 변경>\nID: " + result + "\n\n");
//                            memberBw.flush();
//
//                            continue;
//
//                        } else if (click1.equals("6")) {
//                            String information = "\n조회할 아이디를 입력해주세요.";
//                            String result;
//                            bufferedWriter.write(information);
//                            bufferedWriter.flush();
//                            BufferedReader resultBf = new BufferedReader(new InputStreamReader(System.in));
//                            result = resultBf.readLine();
//
//                            memberServiceImpl.printMember(result);
//                            continue;
//
//                        } else if (click1.equals("7")) {
//                            memberServiceImpl.printAllMember();
//                            continue;
//
//                        } else {
//                            String noMenu = "\n<처음으로 돌아갑니다.>";
//                            bufferedWriter.write(noMenu);
//                            bufferedWriter.flush();
//                            continue;
//
//                        }
//                    } else if (clickMenu.equals("2")) {
//                        String productMenu = "<제품 서비스>\n" +
//                                "1. 제품 삽입\n" +
//                                "2. 제품 삭제\n" +
//                                "3. 제품 조회\n" +
//                                "4. 총 제품 조회\n" +
//                                "5. 메뉴에 없는 문자 혹은 숫자를 누르면. 다시 처음 메뉴로 갑니다.";
//                        bufferedWriter.write(productMenu);
//                        bufferedWriter.flush();
//
//                        BufferedReader bf2 = new BufferedReader(new InputStreamReader(System.in));
//                        String click2 = bf2.readLine();
//
//                        if (click2.equals("1")) {
//
//                            String[] information = new String[5];
//                            String[] resultArr = new String[5];
//                            information[0] = "\n삽입할 제품양을 적어주세요. (단, 숫자로만)";
//                            information[1] = "삽입할 제품 박스의 개수를 적어주세요. (단, 숫자로만)";
//                            information[2] = "제품의 가격을 적어주세요. (단, 숫자로만)";
//                            information[3] = "제품 이름을 적어주세요.";
//                            information[4] = "제품 코드명을 적어주세요.";
//
//                            for (int i = 0; i < information.length; i++) {
//                                bufferedWriter.write(information[i]);
//                                bufferedWriter.flush();
//                                BufferedReader result = new BufferedReader(new InputStreamReader(System.in));
//                                resultArr[i] = result.readLine();
//
//                            }
//
//                            InsertDto insertDto = InsertDto.builder()
//                                    .amount(Long.parseLong(resultArr[0]))
//                                    .box(Long.parseLong(resultArr[1]))
//                                    .price(Long.parseLong(resultArr[2]))
//                                    .productName(resultArr[3])
//                                    .CodeName(resultArr[4])
//                                    .build();
//                            productServiceImpl.insertProduct(insertDto);
//                            productBw.write("<제품 삽입>\nCODE: " + insertDto.getCodeName() + "\nNAME: "
//                                    + insertDto.getProductName() + "\nTotal AMOUNT: "
//                                    + Long.toString(insertDto.getAmount()*insertDto.getBox()) + "\nPRICE: "
//                                    + Long.toString(insertDto.getPrice()) + "\n\n");
//                            productBw.flush();
//
//                            continue;
//
//                        } else if (click2.equals("2")) {
//                            String information = "\n삭제할 제품 코드명을 입력해주세요.";
//                            String result;
//                            bufferedWriter.write(information);
//                            bufferedWriter.flush();
//                            BufferedReader resultBf = new BufferedReader(new InputStreamReader(System.in));
//                            result = resultBf.readLine();
//
//                            productServiceImpl.deleteProduct(result);
//                            productBw.write("<제품 삭제>\nCODE: " + result + "\n\n");
//                            productBw.flush();
//
//                            continue;
//
//                        } else if (click2.equals("3")) {
//                            String information = "\n조회할 제품 코드명을 입력해주세요.";
//                            String result;
//                            bufferedWriter.write(information);
//                            bufferedWriter.flush();
//                            BufferedReader resultBf = new BufferedReader(new InputStreamReader(System.in));
//                            result = resultBf.readLine();
//
//                            productServiceImpl.printProduct(result);
//                            continue;
//
//                        } else if (click2.equals("4")) {
//                            productServiceImpl.printAllProduct();
//                            continue;
//
//                        } else {
//                            String noMenu = "\n<처음으로 돌아갑니다.>";
//                            bufferedWriter.write(noMenu);
//                            bufferedWriter.flush();
//                            continue;
//                        }
//
//                    } else if (clickMenu.equals("3")) {
//                        String saleMenu = "<판매 서비스>\n" +
//                                "1. 장바구니 담기\n" +
//                                "2. 장바구니에 담긴 제품 결제\n" +
//                                "3. 메뉴에 없는 문자 혹은 숫자를 누르면. 다시 처음 메뉴로 갑니다.";
//                        bufferedWriter.write(saleMenu);
//                        bufferedWriter.flush();
//
//                        BufferedReader bf3 = new BufferedReader(new InputStreamReader(System.in));
//                        String click3 = bf3.readLine();
//
//                        if (click3.equals("1")) {
//
//                            String[] information = new String[2];
//                            String[] resultArr = new String[2];
//                            information[0] = "\n장바구니에 담을 제품 코드명을 적어주세요.";
//                            information[1] = "몇개 담을 건지 적어주세요. (단, 숫자로만)";
//
//                            for (int i = 0; i < information.length; i++) {
//                                bufferedWriter.write(information[i]);
//                                bufferedWriter.flush();
//                                BufferedReader result = new BufferedReader(new InputStreamReader(System.in));
//                                resultArr[i] = result.readLine();
//                            }
//
//                            SelectDto selectDto1 = SelectDto.builder()
//                                    .buyAmount(Long.parseLong(resultArr[1]))
//                                    .buyCodeName(resultArr[0])
//                                    .build();
//                            saleServiceImpl.addCart(selectDto1);
//                            continue;
//
//                        } else if (click3.equals("2")) {
//                            String information = "\n결제 방법을 적어주세요. (단, \"cash\" or \"card\")\n[cash=현금 / card=카드)";
//                            String result;
//                            bufferedWriter.write(information);
//                            bufferedWriter.flush();
//                            BufferedReader resultBf = new BufferedReader(new InputStreamReader(System.in));
//                            result = resultBf.readLine();
//
//                            if (result.equals("card")) {
//                                PaymentDto paymentDto = PaymentDto.builder()
//                                        .salePayment(SalePayment.CARD)
//                                        .build();
//                                saleServiceImpl.payment(paymentDto);
//                            } else if (result.equals("cash")) {
//                                PaymentDto paymentDto = PaymentDto.builder()
//                                        .salePayment(SalePayment.CASH)
//                                        .build();
//                                saleServiceImpl.payment(paymentDto);
//                            } else {
//                                String error = "\n결제 실패 (결제 방법을 잘못 입력하셨습니다.) 처음 화면으로 돌아갑니다.";
//                                bufferedWriter.write(error);
//                                bufferedWriter.flush();
//                                continue;
//                            }
//                            continue;
//
//                        } else {
//                            String noMenu = "\n<처음으로 돌아갑니다.>";
//                            bufferedWriter.write(noMenu);
//                            bufferedWriter.flush();
//                            continue;
//                        }
//
//                    }else if (clickMenu.equals("4")){
//                        recordDao.maxProduct();
//                        recordBw.write(Long.toString(RecordDao.totalSales));
//                        continue;
//
//                    } else if (clickMenu.equals("5")) {
//                        String hello = "\n<이용해 주셔서 감사합니다. 안녕히 가세요.>";
//                        bufferedWriter.write(hello);
//                        bufferedWriter.flush();
//                        break;
//
//                    } else {
//                        String noMenu = "\n<없는 메뉴 입니다. 1, 2, 3, 4, 5 중에 눌러 주세요.>";
//                        bufferedWriter.write(noMenu);
//                        bufferedWriter.flush();
//                        continue;
//
//                    }
//                }
//                bufferedWriter.close();
//                bufferedReader.close();
//                memberBw.close();
//                productBw.close();
//                recordBw.close();
//            }catch (IOException e) {
//                e.printStackTrace();
//                throw new BadRequestException(e.getMessage());
//            }
//    }
//
//}
