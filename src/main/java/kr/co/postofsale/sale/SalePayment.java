package kr.co.postofsale.sale;

import kr.co.postofsale.member.MemberRole;
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
                .findAny().orElseThrow(()-> new RuntimeException("카드 혹은 현금에서 골라주세요."));
    }
}
