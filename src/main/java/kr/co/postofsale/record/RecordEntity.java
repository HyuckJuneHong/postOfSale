package kr.co.postofsale.record;

import kr.co.postofsale.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordEntity extends BaseEntity {

    private String bestSaleName;
    private String bestSalePrice;
    private Long daySalePrice;
    private Long totalSalePrice;

    @Builder
    public RecordEntity(String bestSaleName, String bestSalePrice, Long daySalePrice, Long totalSalePrice) {
        this.bestSaleName = bestSaleName;
        this.bestSalePrice = bestSalePrice;
        this.daySalePrice = daySalePrice;
        this.totalSalePrice = totalSalePrice;
    }
}
