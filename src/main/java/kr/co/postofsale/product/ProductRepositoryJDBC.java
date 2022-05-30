package kr.co.postofsale.product;

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

@Repository
public class ProductRepositoryJDBC implements ProductRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryJDBC(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<ProductEntity> productEntityRowMapper(){
        return (rs, rowNum) -> {
            ProductEntity productEntity = ProductEntity.builder()
                    .name(rs.getString("name"))
                    .price(rs.getLong("price"))
                    .amount(rs.getLong("amount"))
                    .build();

            productEntity.setId(rs.getLong("id"));
            productEntity.setInsertDate(rs.getTimestamp("insert_date"));
            productEntity.setUpdateDate(rs.getTimestamp("update_date"));

            return productEntity;
        };
    }

    @Override
    @Transactional
    public ProductEntity save(ProductEntity productEntity) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("product").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", productEntity.getName());
        parameters.put("price", productEntity.getPrice());
        parameters.put("amount", productEntity.getAmount());
        parameters.put("insert_date", productEntity.getInsertDate());
        parameters.put("update_date", productEntity.getUpdateDate());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        productEntity.setId(key.longValue());
        return productEntity;
    }

    @Override
    @Transactional
    public void update(ProductEntity productEntity) {
        this.jdbcTemplate.update("update product " +
                "set price=?, amount=?, update_date=? " +
                "where name=?"
                , new Object[]{productEntity.getPrice(), productEntity.getAmount()
                        ,productEntity.getUpdateDate(), productEntity.getName()});
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        this.jdbcTemplate.update("DELETE FROM product WHERE name=?", name);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE from product");
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        List<ProductEntity> list = jdbcTemplate.query("select * from product where name = ?"
                , productEntityRowMapper(), name);
        return list.isEmpty() ? null : list.stream().findAny();
    }

    @Override
    public List<ProductEntity> findAll() {
        return jdbcTemplate.query("select * from product", productEntityRowMapper());
    }

    @Override
    public Boolean existByName(String name) {
        List<ProductEntity> list = jdbcTemplate.query("select * from member where name = ?"
                , productEntityRowMapper(), name);

        return list.isEmpty() ? false : true;
    }
}
