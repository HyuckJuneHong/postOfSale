package kr.co.postofsale.sale;

import io.swagger.annotations.ApiModelProperty;
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

        @ApiModelProperty(example = "장바구니에 넣을 상품명")
        @NotBlank(message = "장바구니에 담을 상품명을 입력해주세요.")
        private String buyProductName;

        @ApiModelProperty(example = "장바구니에 담을 상품량")
        @NotBlank(message = "장바구니에 담을 상품량을 입력해주세요.")
        private Long buyAmount;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ{

        @ApiModelProperty
        @NotBlank
        private String buyIdentity;

        @ApiModelProperty
        @NotBlank
        private String buyProductName;

        @ApiModelProperty
        @NotBlank
        private Long buyAmount;

        @ApiModelProperty
        @NotBlank
        private Long totalPrice;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE{

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DELETE {
    }
}
