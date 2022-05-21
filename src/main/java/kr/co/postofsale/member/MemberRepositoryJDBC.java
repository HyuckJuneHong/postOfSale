package kr.co.postofsale.member;

import kr.co.postofsale.member.enumClass.Gender;
import kr.co.postofsale.member.enumClass.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class MemberRepositoryJDBC implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepositoryJDBC(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<MemberEntity> memberEntityRowMapper(){
        return (rs, rowNum) -> {
            MemberEntity memberEntity = MemberEntity.builder()
                    .identity(rs.getString("identity"))
                    .password(rs.getString("password"))
                    .name(rs.getString("name"))
                    .birth(rs.getString("birth"))
                    .phone(rs.getString("phone"))
                    .gender(Gender.of(rs.getString("gender")))
                    .memberRole(MemberRole.of(rs.getString("member_role")))
                    .build();

            memberEntity.setId(rs.getLong("id"));
            memberEntity.setInsertDate(rs.getTimestamp("insert_date"));
            memberEntity.setDeleteDate(rs.getTimestamp("delete_date"));
            memberEntity.setUpdateDate(rs.getTimestamp("update_date"));

            return memberEntity;
        };
    }

    @Override
    @Transactional
    public MemberEntity save(MemberEntity memberEntity) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("identity", memberEntity.getIdentity());
        parameters.put("password", memberEntity.getPassword());
        parameters.put("name", memberEntity.getName());
        parameters.put("birth", memberEntity.getBirth());
        parameters.put("phone", memberEntity.getPhone());
        parameters.put("gender", memberEntity.getGender());
        parameters.put("member_role", memberEntity.getMemberRole());
        parameters.put("insert_date", memberEntity.getInsertDate());
        parameters.put("update_date", memberEntity.getUpdateDate());
        parameters.put("delete_date", memberEntity.getDeleteDate());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        memberEntity.setId(key.longValue());
        return memberEntity;
    }

    @Override
    public void update(MemberEntity memberEntity) {
        this.jdbcTemplate.update("update member " +
                        "set password=?, name=?, phone=?, member_role=?, update_date=?" +
                        "where identity=?"
                , new Object[] { memberEntity.getPassword(), memberEntity.getName(), memberEntity.getPhone()
                        , memberEntity.getMemberRole(), memberEntity.getUpdateDate()
                        , memberEntity.getIdentity()});
    }

    @Override
    public void deleteByidentity(MemberEntity memberEntity) {
        this.jdbcTemplate.update("DELETE FROM member WHERE identity=?", memberEntity.getIdentity());
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE from member");
    }

    @Override
    public Optional<MemberEntity> findByIdentity(String identity) {
        List<MemberEntity> list = jdbcTemplate.query("select * from member where identity = ?"
                , memberEntityRowMapper(), identity);
        return list.isEmpty() ? null : list.stream().findAny();
    }

    @Override
    public List<MemberEntity> findAll() {
        return jdbcTemplate.query("select * from member", memberEntityRowMapper());
    }

    @Override
    public Boolean existByIdentity(String identity) {

        List<MemberEntity> list = jdbcTemplate.query("select * from member where identity = ?"
                , memberEntityRowMapper(), identity);

        return list.isEmpty() ? false : true;
    }

    @Override
    public Boolean existByPhone(String phone) {

        List<MemberEntity> list = jdbcTemplate.query("select * from member where phone = ?"
                , memberEntityRowMapper(), phone);

        return list.isEmpty() ? false : true;

    }

}
