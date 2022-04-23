package kr.co.postofsale.product;

import kr.co.postofsale.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductEntity extends BaseEntity {

    private String codeName;
    private String productName;
    private LocalDate insertDate;
    private Long price;
    private Long totalAmount;

    @Builder
    public ProductEntity(String codeName, String productName, LocalDate insertDate, Long price, Long totalAmount) {
        this.codeName = codeName;
        this.productName = productName;
        this.insertDate = insertDate;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    public void updateAmount(Long totalAmount){
        this.totalAmount = totalAmount;
    }
}
