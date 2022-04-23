package kr.co.postofsale.sale.saleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SelectDto {
    private String buyCodeName;
    private Long buyAmount;
}
