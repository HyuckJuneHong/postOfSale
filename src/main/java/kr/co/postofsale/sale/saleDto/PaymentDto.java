package kr.co.postofsale.sale.saleDto;

import kr.co.postofsale.sale.enumClass.SalePayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentDto {

    private SalePayment salePayment;
}
