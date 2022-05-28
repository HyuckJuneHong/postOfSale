package kr.co.postofsale.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepositoryImpl;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepositoryImpl) {
        this.productRepositoryImpl = productRepositoryImpl;
    }

    @Override
    public void productCreate(ProductDto.CREATE create) {

    }


}
