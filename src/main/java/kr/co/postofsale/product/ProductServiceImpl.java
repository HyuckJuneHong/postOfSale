package kr.co.postofsale.product;

import kr.co.postofsale.common.BadRequestException;
import kr.co.postofsale.product.productDto.InsertDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public void insertProduct(InsertDto insert) {
        ProductEntity product = productDao.findByProduct(insert.getCodeName());

        if(product != null){

        }else{

        }
    }

    @Override
    public void printProduct(String codeName) {
        ProductEntity product = productDao.findByProduct(codeName);

        System.out.println();
        if(product == null){
            throw new BadRequestException("해당 상품은 존재하지 않습니다.");
        }

        System.out.println();

    }

    @Override
    public void printAllProduct() {

        ArrayList<ProductEntity> list = new ArrayList<>(productDao.findAllProduct());

        System.out.println();
        System.out.println();
        for(ProductEntity product : list){
            System.out.println();
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }
}
