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
    public boolean existsByIdentity(String identity) {
        Optional<MemberEntity> member = map.values().stream()
                .filter(memberEntity -> memberEntity.getIdentity().equals(identity))
                .findAny();

        if(member == null){
            return false;
        }
        return true;
    }
}
