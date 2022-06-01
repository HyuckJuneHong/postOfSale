package kr.co.postofsale.sale.enumClass;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum SalePayment {

    CARD("카드"),
    CASH("현금");

    private String payment;

    public static SalePayment of(String payment){
        return Arrays.stream(SalePayment.values())
                .filter(r->r.toString().equalsIgnoreCase(payment))
                .findAny().orElseThrow(()-> new RuntimeException("card(카드) 혹은 cash(현금)에서 골라주세요."));
    }
}
