package kr.co.postofsale.sale;

import kr.co.postofsale.sale.enumClass.SalePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SaleRepositoryJDBC implements SaleRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleRepositoryJDBC(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<SaleEntity> saleEntityRowMapper(){
        return (rs, rowNum) -> {
          SaleEntity saleEntity = SaleEntity.builder()
                  .buyIdentity(rs.getString("buy_identity"))
                  .salePayment(SalePayment.of(rs.getString("sale_payment")))
                  .buyProductName(rs.getString("buy_product_name"))
                  .buyAmount(rs.getLong("buy_amount"))
                  .totalPrice(rs.getLong("total_price"))
                  .build();

          saleEntity.setId(rs.getLong("id"));
          saleEntity.setInsertDate(rs.getTimestamp("insert_date"));
          saleEntity.setUpdateDate(rs.getTimestamp("update_date"));

          return saleEntity;
        };
    }

    @Override
    @Transactional
    public SaleEntity save(SaleEntity saleEntity){

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("sale").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("buy_identity", saleEntity.getBuyIdentity());
        parameters.put("sale_payment", saleEntity.getSalePayment());
        parameters.put("buy_product_name", saleEntity.getBuyProductName());
        parameters.put("buy_amount", saleEntity.getBuyAmount());
        parameters.put("total_price", saleEntity.getTotalPrice());
        parameters.put("insert_date", saleEntity.getInsertDate());
        parameters.put("update_date", saleEntity.getUpdateDate());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        saleEntity.setId(key.longValue());
        return saleEntity;
    }

    @Override
    public List<SaleEntity> findByProductName(String productName) {
        return jdbcTemplate.query("select * from sale where buy_product_name = ? "
                , saleEntityRowMapper(), productName);
    }

    @Override
    public List<SaleEntity> findByIdentity(String identity) {
        return jdbcTemplate.query("select * from sale where buy_identity = ? "
            , saleEntityRowMapper(), identity);
    }

    @Override
    public List<SaleEntity> findAll() {
        return jdbcTemplate.query("select * from sale ", saleEntityRowMapper());
    }

    @Override
    public void deleteByMySelf(String identity) {
        this.jdbcTemplate.update("DELETE FROM sale WHERE buy_identity=?", identity);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM sale");
    }
}
