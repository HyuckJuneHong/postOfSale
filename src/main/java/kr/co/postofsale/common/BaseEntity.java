package kr.co.postofsale.common;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BaseEntity {

    protected Long id;
    protected LocalDate insertDate;
    protected LocalDate updateDate;
    protected LocalDate deleteDate;

}
