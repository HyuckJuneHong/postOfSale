package kr.co.postofsale.record;

import kr.co.postofsale.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordEntity extends BaseEntity {

    Long totalSales;
    Long maxProduct;

    @Builder
    public RecordEntity(Long totalSales, Long maxProduct) {
        this.totalSales = totalSales;
        this.maxProduct = maxProduct;
    }
}
