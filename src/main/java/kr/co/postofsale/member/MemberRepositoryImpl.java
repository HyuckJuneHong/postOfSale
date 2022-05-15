package kr.co.postofsale.member;

import java.util.*;

public class MemberRepositoryImpl implements MemberRepository{

     private static Map<Long, MemberEntity> map = new HashMap<>();
     private static Long sequence = 0L;

     @Override
    public MemberEntity save(MemberEntity memberEntity) {
         memberEntity.setId(sequence++);
         map.put(memberEntity.getId(), memberEntity);

         return memberEntity;
    }

    @Override
    public Optional<MemberEntity> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<MemberEntity> findByIdentity(String identity) {

        return map.values().stream()
                 .filter(memberEntity -> memberEntity.getIdentity().equals(identity))
                 .findAny();
    }

    @Override
    public List<MemberEntity> findAll() {
        return new ArrayList<>(map.values());
     }

    @Override
    public boolean existsByIdentityAndNameAndBirth(String identity, String name, String birth) {
        return false;
    }

    @Override
    public boolean existsByIdentity(String identity) {
        return false;
    }
}
