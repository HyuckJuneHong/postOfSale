package kr.co.postofsale.sale;

import kr.co.postofsale.product.ProductEntity;
import kr.co.postofsale.sale.enumClass.SalePayment;

import java.util.List;
import java.util.Optional;

public interface SaleRepository {

    SaleEntity save(SaleEntity saleEntity);

    List<SaleEntity> findByProductName(String productName);
    List<SaleEntity> findByIdentity(String identity);
    List<SaleEntity> findAll();
}
