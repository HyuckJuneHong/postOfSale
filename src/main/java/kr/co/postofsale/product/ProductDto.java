package kr.co.postofsale.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class ProductDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE{

        @ApiModelProperty(example = "상품명")
        @NotBlank(message = "생성할 상품 이름을 입력해주세요.")
        String name;

        @ApiModelProperty(example = "가격")
        @NotBlank(message = "생성할 상품 가격을 입력해주세요.")
        Long price;

        @ApiModelProperty(example = "상품량")
        @NotBlank(message = "생성할 상품량을 입력해주세요.")
        Long amount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ{

        @ApiModelProperty(example = "상품명")
        String name;

        @ApiModelProperty(example = "가격")
        Long price;

        @ApiModelProperty(example = "상품량")
        Long amount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE{

        @ApiModelProperty(example = "상품명")
        @NotBlank(message = "변경할 상품 이름을 입력해주세요.")
        String name;

        @ApiModelProperty(example = "새 상품 가격")
        Long newPrice;

        @ApiModelProperty(example = "수량 변경량")
        Long newAmount;

    }

}
