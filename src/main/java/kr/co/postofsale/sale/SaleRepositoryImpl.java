package kr.co.postofsale.sale;

import kr.co.postofsale.product.ProductEntity;

import java.util.*;

public class SaleRepositoryImpl implements SaleRepository{

    private static long saleCode = 0;

    private List<SaleEntity> list = new ArrayList<>();
    private Map<Long, List<SaleEntity>> map = new HashMap<>();

    public void cart(SaleEntity sale){
        list.add(sale);
    }

    public void shoppingEnd(){
        map.put(saleCode++, list);
    }

    public List<SaleEntity> findByBuyList(){
        return map.get(saleCode - 1);
    }

}
