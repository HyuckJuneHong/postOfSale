package kr.co.postofsale.sale;

import kr.co.postofsale.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaleEntity extends BaseEntity {

    private String buyCodeName;     //구입할 제품 코드명
    private String buyProductName;  //구입할 제품명
    private Long buyAmount;         //구입할 수량
    private Long totalPrice;        //총 가격

    @Builder
    public SaleEntity(String buyCodeName, String buyProductName, Long buyAmount, Long totalPrice) {
        this.buyCodeName = buyCodeName;
        this.buyProductName = buyProductName;
        this.buyAmount = buyAmount;
        this.totalPrice = totalPrice;
    }
}
