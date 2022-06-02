package kr.co.postofsale.sale;

import io.swagger.annotations.ApiModelProperty;
import kr.co.postofsale.sale.enumClass.SalePayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class SaleDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ADD {

        @ApiModelProperty(example = "구매할 상품명")
        @NotBlank(message = "장바구니에 담을 상품명을 입력해주세요.")
        private String buyProductName;

        @ApiModelProperty(example = "구매할 상품량")
        @NotBlank(message = "장바구니에 담을 상품량을 입력해주세요.")
        private Long buyAmount;

        @ApiModelProperty(example = "CARD or CASH")
        @NotBlank(message = "결제 방법을 적어주세요.(CARD=카드, CASH=현금")
        private String salePayment;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ{

        @ApiModelProperty(example = "구매자 아이디")
        private String buyIdentity;

        @ApiModelProperty(example = "구매 제품명")
        private String buyProductName;

        @ApiModelProperty(example = "구매량")
        private Long buyAmount;

        @ApiModelProperty(example = "총 가격")
        private Long totalPrice;

        @ApiModelProperty(example = "결제 방법")
        private SalePayment salePayment;

    }
}
