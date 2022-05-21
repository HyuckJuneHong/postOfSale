package kr.co.postofsale.common;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BaseEntity {

    protected Long id;
    protected Timestamp insertDate;
    protected Timestamp updateDate;
}
