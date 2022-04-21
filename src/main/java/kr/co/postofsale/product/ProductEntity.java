package kr.co.postofsale.product;

import kr.co.postofsale.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity {

    private String CodeName;
    private String productName;
    private LocalDateTime insertDate;
    private Long price;
    private Long amount;
    private Long box;
    private Long totalAmount;
}
