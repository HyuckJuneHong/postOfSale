package kr.co.postofsale.sale;

import java.util.List;

public interface SaleRepository {

    SaleEntity save(SaleEntity saleEntity);

    List<SaleEntity> findByProductName(String productName);
    List<SaleEntity> findByIdentity(String identity);
    List<SaleEntity> findAll();
}
