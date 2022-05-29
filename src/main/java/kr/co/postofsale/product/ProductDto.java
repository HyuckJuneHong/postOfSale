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
        String price;

        @ApiModelProperty(example = "상품량")
        @NotBlank(message = "생성할 상품량을 입력해주세요.")
        String amount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ{

        @ApiModelProperty(example = "상품명")
        String name;

        @ApiModelProperty(example = "가격")
        String price;

        @ApiModelProperty(example = "상품량")
        String amount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE{

        @ApiModelProperty(example = "상품명")
        @NotBlank(message = "변경할 상품 이름을 입력해주세요.")
        String name;

        @ApiModelProperty(example = "새 상품명")
        String newName;

        @ApiModelProperty(example = "새 상품 가격")
        Long newPrice;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE_AMOUNT{

        @ApiModelProperty(example = "판매 상품명 or 추가 상품명")
        @NotBlank(message = "판매 또는 추가할 상품명을 입력해주세요.")
        String name;

        @ApiModelProperty(example = "판매 상품량 or 추가 상품량")
        Long updateAmount;
    }

}
