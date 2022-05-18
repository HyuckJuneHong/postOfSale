package kr.co.postofsale.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);
    MemberEntity updateSave(MemberEntity memberEntity);

    Optional<MemberEntity> findByIdentity(String identity);
    List<MemberEntity> findAll();

    boolean existsByIdentity(String identity);
}
