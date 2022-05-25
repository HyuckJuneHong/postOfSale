package kr.co.postofsale.product;

import kr.co.postofsale.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductEntity extends BaseEntity {

    private String name;
    private Long price;
    private Long amount;

    @Builder
    public ProductEntity(String name, Long price, Long amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public void updateName(String name, Long price){
        this.name = name;
        this.price = price;
    }

    public void updateAmount(Long amount){
        this.amount = amount;
    }
}
