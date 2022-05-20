package kr.co.postofsale.member;

import kr.co.postofsale.member.enumClass.Gender;
import kr.co.postofsale.member.enumClass.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemberRepositoryJDBC implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepositoryJDBC(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<MemberEntity> memberEntityRowMapper(){
        return (rs, rowNum) -> {
            MemberEntity memberEntity = new MemberEntity();

//                    MemberEntity.builder()
//                    .identity(rs.getString("identity"))
//                    .password(rs.getString("password"))
//                    .name(rs.getString("name"))
//                    .birth(rs.getString("birth"))
//                    .phone(rs.getString("phone"))
//                    .gender(Gender.values()[rs.getInt("gender")])
//                    .memberRole(MemberRole.values()[rs.getInt("memberRole")])
//                    .build();

            memberEntity.setId(rs.getLong("id"));
            memberEntity.setInsertDate(rs.getTimestamp("insertDate"));
            memberEntity.setDeleteDate(rs.getTimestamp("deleteDate"));
            memberEntity.setUpdateDate(rs.getTimestamp("updateDate"));
            memberEntity.setIdentity(rs.getString("identity"));
            memberEntity.setPassword(rs.getString("password"));
            memberEntity.setName(rs.getString("name"));
            memberEntity.setBirth(rs.getString("birth"));
            memberEntity.setPhone(rs.getString("phone"));
            memberEntity.setGender(Gender.values()[rs.getInt("gender")]);
            memberEntity.setMemberRole(MemberRole.values()[rs.getInt("memberRole")]);

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
        parameters.put("insertDate", memberEntity.getInsertDate());
        parameters.put("updateDate", memberEntity.getUpdateDate());
        parameters.put("deleteDate", memberEntity.getDeleteDate());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        memberEntity.setId(key.longValue());
        return memberEntity;
    }

    @Override
    @Transactional
    public MemberEntity updateSave(MemberEntity memberEntity) {
        return null;
    }

    @Override
    public Optional<MemberEntity> findByIdentity(String identity) {
        List<MemberEntity> list = jdbcTemplate.query("select * from member where identity = ?"
                , memberEntityRowMapper(), identity);
        return list.stream().findAny();
    }

    @Override
    public List<MemberEntity> findAll() {
        return jdbcTemplate.query("select * from member", memberEntityRowMapper());
    }

}
