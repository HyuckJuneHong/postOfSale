package kr.co.postofsale.product;

import kr.co.postofsale.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

    public void updateAmount(Long amount){
        this.amount = amount;
    }

    public void updateProduct(ProductDto.UPDATE update){

        if(!this.price.equals(update.getNewPrice())){
            this.price = update.getNewPrice();
            this.updateDate = new Timestamp(System.currentTimeMillis());
        }

        if(!this.amount.equals(update.getNewAmount())){
            this.amount = update.getNewAmount();
            this.updateDate = new Timestamp(System.currentTimeMillis());
        }
    }
}
