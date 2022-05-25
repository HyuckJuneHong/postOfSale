package kr.co.postofsale.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SaleRepositoryJDBC implements SaleRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleRepositoryJDBC(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
