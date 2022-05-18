package kr.co.postofsale.product;

import kr.co.postofsale.member.MemberEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductRepositoryImpl {


    private static Map<Long, ProductEntity> mapProduct = new HashMap<>();
    private static Long sequenceP = 0L;

    public void addNewProduct(ProductEntity product){
        product.setId(sequenceP++);
        mapProduct.put(product.getId(), product);
    }

    public void updateProduct(ProductEntity product){
        mapProduct.put(product.getId(), product);
    }

    public void deleteProduct(ProductEntity product){
        mapProduct.remove(product.getId());
    }

//    public ProductEntity findByProduct(Long id){
//        return mapProduct.get(id);
//    }

//    public Collection<ProductEntity> findAllProduct(){
//        return mapProduct.values();
//    }
}
