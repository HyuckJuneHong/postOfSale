package kr.co.postofsale.product;

import kr.co.postofsale.product.productDto.InsertDto;

public interface ProductService {

    void insertProduct(InsertDto insert); //제품 삽입 서비스

    void printProduct(String codeName);
    void printAllProduct();
}
