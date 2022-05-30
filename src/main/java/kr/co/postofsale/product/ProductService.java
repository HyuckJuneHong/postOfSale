package kr.co.postofsale.product;

import java.util.List;

public interface ProductService {

    //common service
    Boolean checkName(String name);                             //이름 중복 체크

    //create service
    void newInsert(ProductDto.CREATE create);                   //새 제품 생성

    //read service
    ProductDto.READ getProductName(String name);                //이름으로 조회
    List<ProductDto.READ> getProductAll();                      //모든 상품 조회

    //update service
    void updateProduct(ProductDto.UPDATE update);               //상품 정보 변경

    //delete service
    void deleteProduct(String name);                            //상품 삭제
}
