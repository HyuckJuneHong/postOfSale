package kr.co.postofsale.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    void update(MemberEntity memberEntity);
    void deleteByidentity(MemberEntity memberEntity);
    void deleteAll();

    Optional<MemberEntity> findByIdentity(String identity);
    List<MemberEntity> findAll();

    Boolean existByIdentity(String identity);
    Boolean existByPhone(String phone);

}
