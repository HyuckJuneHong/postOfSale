package kr.co.postofsale.product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductDao {

    private static long productCode = 0;

    private Map<String, ProductEntity> map = new HashMap<>();

    public void insertInventory(ProductEntity product){
        product.setCode(productCode++);
        map.put(product.getCodeName(), product);
    }

    public ProductEntity findByProduct(String codeName){
        return map.get(codeName);
    }

    public Collection<ProductEntity> findAllProduct(){
        return map.values();
    }


}
