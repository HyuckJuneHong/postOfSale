package kr.co.postofsale.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);
    Optional<MemberEntity> findByIdentity(String identity);
    Optional<MemberEntity> findById(Long id);

    List<MemberEntity> findAll();

    boolean existsByIdentityAndNameAndBirth(String identity, String name, String birth);
    boolean existsByIdentity(String identity);
}
