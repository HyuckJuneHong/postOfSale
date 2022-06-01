package kr.co.postofsale.sale;

import java.util.List;

public interface SaleService {

    //create service
    void addBuy(SaleDto.ADD add);                               //구매 정보 생성

    //read service
    List<SaleDto.READ> getSaleMySelf();                         //자신이 구매한 정보 조회
    List<SaleDto.READ> getSaleIdentity(String identity);        //해당 아이디가 구매한 정보 조회
    List<SaleDto.READ> getSaleProductName(String productName);  //해당 제품 구매한 정보 조회
    List<SaleDto.READ> getSaleAll();                            //모든 구매 정보 조회

    //delete service
//    void deleteSale(SaleDto.DELETE delete);                     //자신 구매 정보 삭제

}
