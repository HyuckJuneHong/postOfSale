package kr.co.postofsale.record;

import kr.co.postofsale.product.ProductDao;
import kr.co.postofsale.sale.SaleDao;
import kr.co.postofsale.sale.SaleEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class RecordServiceImpl implements RecordService{

    private RecordDao recordDao;
    private ProductDao productDao;
    private SaleDao saleDao;

    @Autowired
    public RecordServiceImpl(RecordDao recordDao, ProductDao productDao, SaleDao saleDao) {
        this.recordDao = recordDao;
        this.productDao = productDao;
        this.saleDao = saleDao;
    }

    @Override
    public void findByTotalSales() {

        Map<Long, List<SaleEntity>> map = saleDao.getMap();

    }

    @Override
    public void findByMaxProduct() {

    }
}
