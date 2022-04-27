package kr.co.postofsale.record;

import kr.co.postofsale.product.ProductEntity;
import kr.co.postofsale.sale.SaleEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordDao {

    private static long productCode = 0;
    public static long totalSales = 0;

    private Map<String, Long> map = new HashMap<>();

    public void updateRecord(ProductEntity product, SaleEntity sale){
        if(map.containsKey(product.getCodeName())){
            map.put(product.getCodeName(), map.get(product.getCodeName()) + sale.getBuyAmount());
        }else{
            map.put(product.getCodeName(), sale.getBuyAmount());
        }
    }

    public void maxProduct(){
        Long maxAmount = 0L;
        String maxProductName = "";

        for(String key : map.keySet()){
            if(map.get(key) > maxAmount){
                maxAmount = map.get(key);
                maxProductName = key;
            }
        }

        System.out.println("-가장 많이 팔린 제품 코드명: " + maxProductName + "\n-팔린 양: " + maxAmount);
        System.out.println("-총 매출량: " + totalSales);

    }

    public void clearTotalSales(){
        map.clear();
        totalSales = 0;
    }

}
