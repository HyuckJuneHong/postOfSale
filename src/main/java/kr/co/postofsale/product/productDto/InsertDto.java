package kr.co.postofsale.product.productDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertDto {
    private String CodeName;
    private String productName;
    private Long price;
    private Long amount;
    private Long box;
}
