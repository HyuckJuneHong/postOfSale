package kr.co.postofsale.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    ProductEntity save(ProductEntity productEntity);

    void update(ProductEntity productEntity);
    void saleProduct(ProductEntity productEntity);

    void deleteByName(String name);
    void deleteAll();

    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findAll();

    Boolean existByName(String name);
}
