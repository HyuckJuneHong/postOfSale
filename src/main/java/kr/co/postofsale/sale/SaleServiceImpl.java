package kr.co.postofsale.sale;

import org.springframework.beans.factory.annotation.Autowired;

public class SaleServiceImpl implements SaleService{

    private SaleDao saleDao;

    @Autowired
    public SaleServiceImpl(SaleDao saleDao) {
        this.saleDao = saleDao;
    }
}
