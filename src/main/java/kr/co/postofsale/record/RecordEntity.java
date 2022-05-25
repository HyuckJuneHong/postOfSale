package kr.co.postofsale.record;

import kr.co.postofsale.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordEntity extends BaseEntity {

    private String name;
    private Long daySale;
    private Long weekSale;
    private Long monthSale;
    private Long yearSale;
}
