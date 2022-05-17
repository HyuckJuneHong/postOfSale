package kr.co.postofsale.product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductRepositoryImpl {

    private static long productCode = 0;

    private Map<String, ProductEntity> map = new HashMap<>();

    public void addNewProduct(ProductEntity product){
        product.setId(productCode++);
        map.put(product.getCodeName(), product);
    }

    public void updateProduct(ProductEntity product){
        map.put(product.getCodeName(), product);
    }

    public void deleteProduct(ProductEntity product){
        map.remove(product.getCodeName());
    }

    public ProductEntity findByProduct(String codeName){
        return map.get(codeName);
    }

    public Collection<ProductEntity> findAllProduct(){
        return map.values();
    }
}
