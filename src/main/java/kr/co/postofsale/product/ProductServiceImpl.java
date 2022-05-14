package kr.co.postofsale.product;

import kr.co.postofsale.infrastructure.exception.BadRequestException;
import kr.co.postofsale.product.productDto.InsertDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
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

        System.out.println("\n<제품 입고 서비스>");
        if(product != null){
            ProductEntity addProduct = ProductEntity.builder()
                    .codeName(product.getCodeName())
                    .productName(product.getProductName())
                    .insertDate(LocalDate.now())
                    .price(product.getPrice())
                    .totalAmount(product.getTotalAmount() + (insert.getAmount() * insert.getBox()))
                    .build();
            productDao.updateProduct(addProduct);
        }else{
            ProductEntity newProduct = ProductEntity.builder()
                    .codeName(insert.getCodeName())
                    .productName(insert.getProductName())
                    .insertDate(LocalDate.now())
                    .price(insert.getPrice())
                    .totalAmount(insert.getAmount()*insert.getBox())
                    .build();
            productDao.addNewProduct(newProduct);
        }
        System.out.println("[" + insert.getProductName() + " 입고 완료]");
    }

    @Override
    public void deleteProduct(String codeName) {
        ProductEntity product = productDao.findByProduct(codeName);

        System.out.println("\n<제품 삭제 서비스>");
        if(product == null){
            throw new BadRequestException("해당 상품은 이미 존재하지 않아 삭제에 실패하였습니다.");
        }
        System.out.println("[제품명: " + product.getProductName() + " 삭제 완료]");
        productDao.deleteProduct(product);
    }

    @Override
    public void printProduct(String codeName) {
        ProductEntity product = productDao.findByProduct(codeName);

        System.out.println("\n<제품 조회 서비스>");
        if(product == null){
            throw new BadRequestException("해당 상품은 존재하지 않습니다.");
        }
        System.out.println("-제품 코드: " + product.getCodeName() + "\n-제품명: " + product.getProductName() + "\n-입고 날짜: "
                + product.getInsertDate() + "\n-가격: " + product.getPrice() + "\n-총량: " + product.getTotalAmount());

    }

    @Override
    public void printAllProduct() {
        ArrayList<ProductEntity> list = new ArrayList<>(productDao.findAllProduct());

        System.out.println("\n<총 제품 조회 서비스>");
        System.out.println("---------------------------");
        for(ProductEntity product : list){
            System.out.println("-제품 코드: " + product.getCodeName() + "\n-제품명: " + product.getProductName() + "\n-입고 날짜: "
                    + product.getInsertDate() + "\n-가격: " + product.getPrice() + "\n-총량: " + product.getTotalAmount());
            System.out.println("---------------------------");
        }
        System.out.println("[총 제품 종류: " + list.size() + "]");
        System.out.println("---------------------------");
    }
}
