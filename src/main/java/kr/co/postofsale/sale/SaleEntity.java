package kr.co.postofsale.sale;

import kr.co.postofsale.common.BaseEntity;
import kr.co.postofsale.sale.enumClass.SalePayment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class SaleEntity extends BaseEntity {

    private String buyIdentity;         //구매자 아이디
    private String buyProductName;      //구매할 제품명
    private Long buyAmount;             //구매할 수량
    private Long totalPrice;            //총 가격
    private SalePayment salePayment;    //구매 방법

    @Builder
    public SaleEntity(String buyIdentity, SalePayment salePayment
            , String buyProductName, Long buyAmount, Long totalPrice) {
        this.buyIdentity = buyIdentity;
        this.buyProductName = buyProductName;
        this.buyAmount = buyAmount;
        this.totalPrice = totalPrice;
        this.salePayment = salePayment;
    }

    public void selectPayment(SalePayment salePayment){
        this.salePayment = salePayment;
    }
}
