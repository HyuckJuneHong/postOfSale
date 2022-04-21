package kr.co.postofsale.product.productDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertDto {
    private String CodeName;
    private String productName;
    private LocalDateTime insertDate;
    private Long price;
    private Long amount;
    private Long box;
    private Long totalAmount;
}
