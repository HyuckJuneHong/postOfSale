package kr.co.postofsale.sale;

import kr.co.postofsale.common.BadRequestException;
import kr.co.postofsale.product.ProductDao;
import kr.co.postofsale.product.ProductEntity;
import kr.co.postofsale.sale.saleDto.PaymentDto;
import kr.co.postofsale.sale.saleDto.SelectDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SaleServiceImpl implements SaleService{

    private SaleDao saleDao;
    private ProductDao productDao;

    @Autowired
    public SaleServiceImpl(SaleDao saleDao, ProductDao productDao) {
        this.saleDao = saleDao;
        this.productDao = productDao;
    }

    @Override
    public void addCart(SelectDto select) {
        ProductEntity product =  productDao.findByProduct(select.getBuyCodeName());

        System.out.println("\n<제품 선택 서비스>");
        if(product == null){
            throw new BadRequestException("현재 해당하는 제품은 존재하지 않습니다.");
        }

        if(select.getBuyAmount() > product.getTotalAmount()){
            throw new BadRequestException("현재 제품 재고가 부족합니다. 더 적게 사주세요.");
        }else{
            SaleEntity sale = SaleEntity.builder()
                    .buyCodeName(select.getBuyCodeName())
                    .buyProductName(product.getProductName())
                    .buyAmount(select.getBuyAmount())
                    .totalPrice(select.getBuyAmount() * product.getPrice())
                    .build();

            saleDao.cart(sale);
            System.out.println("[장바구니에 제품: " + sale.getBuyProductName() + " (을/를) "
                    + sale.getBuyAmount() + "개 담았습니다.]");

            long totalAmount = product.getTotalAmount() - select.getBuyAmount();
            if(totalAmount == 0){
                productDao.deleteProduct(product);
            }else{
                product.updateAmount(totalAmount);
                productDao.updateProduct(product);
            }
            System.out.println("[남은 재고량: " + totalAmount + "개]");
        }
    }

    @Override
    public void payment(PaymentDto payment) {

        saleDao.shoppingEnd();
        List<SaleEntity> list = saleDao.findByBuyList();
        Long totalPrice = 0L;

        System.out.println("\n<구매 목록>");
        for(SaleEntity sale : list){
            System.out.println("-" + sale.getBuyProductName() + " " + sale.getBuyAmount() + "개"
                    + " " + sale.getTotalPrice() + "원");
            totalPrice += sale.getTotalPrice();
        }


        System.out.println("총 금액: " + totalPrice + "->결제 방법: " + payment.getSalePayment());
        System.out.println("[이용해주셔서 감사합니다.]");
    }

}
