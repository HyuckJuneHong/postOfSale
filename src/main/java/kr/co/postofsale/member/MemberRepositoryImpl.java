package kr.co.postofsale.member;

import java.util.*;

public class MemberRepositoryImpl implements MemberRepository{

     private static Map<Long, MemberEntity> mapMember = new HashMap<>();
     private static Long sequenceM = 0L;

     @Override
     public MemberEntity save(MemberEntity memberEntity) {
         memberEntity.setId(sequenceM++);
         mapMember.put(memberEntity.getId(), memberEntity);

         return memberEntity;
     }

     @Override
     public MemberEntity updateSave(MemberEntity memberEntity){
         mapMember.put(memberEntity.getId(), memberEntity);
         return memberEntity;
     }

     @Override
     public Optional<MemberEntity> findByIdentity(String identity) {

         return mapMember.values().stream()
                  .filter(memberEntity -> memberEntity.getIdentity().equals(identity))
                  .findAny();
     }

     @Override
     public List<MemberEntity> findAll() {
         return new ArrayList<>(mapMember.values());
     }

     @Override
     public boolean existsByIdentity(String identity) {
         Optional<MemberEntity> member = mapMember.values().stream()
                 .filter(memberEntity -> memberEntity.getIdentity().equals(identity))
                 .findAny();

         if(member == null){
             return false;
         }
         return true;
     }
}
