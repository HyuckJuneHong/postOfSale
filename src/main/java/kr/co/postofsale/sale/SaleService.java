package kr.co.postofsale.sale;

import kr.co.postofsale.sale.saleDto.PaymentDto;
import kr.co.postofsale.sale.saleDto.SelectDto;

public interface SaleService {

    void addCart(SelectDto select);     //장바구니에 제품 담는 서비스

    void payment(PaymentDto payment);   //결제 서비스

}
