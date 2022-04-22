package kr.co.postofsale.sale;

import kr.co.postofsale.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaleEntity extends BaseEntity {

    private String buyCodeName;
    private String buyProductName;
    private Long buyAmount;
    private SalePayment salePayment;

    @Builder
    public SaleEntity(String buyCodeName, String buyProductName, Long buyAmount, SalePayment salePayment) {
        this.buyCodeName = buyCodeName;
        this.buyProductName = buyProductName;
        this.buyAmount = buyAmount;
        this.salePayment = salePayment;
    }
}
