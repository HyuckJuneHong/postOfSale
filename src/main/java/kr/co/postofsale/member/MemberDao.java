package kr.co.postofsale.member;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {

    private static long memberCode = 0;

    private Map<String, MemberEntity> map = new HashMap<>();

    public void insertMember(MemberEntity member){
        member.setCode(memberCode++);
        map.put(member.getIdentity(), member);
    }

    public void updateMember(MemberEntity member){
        map.put(member.getIdentity(), member);
    }

    public void deleteMember(MemberEntity member){
        map.remove(member.getIdentity());
    }

    public MemberEntity findByMember(String identity){
        return map.get(identity);
    }

    public Collection<MemberEntity> findAllMember(){
        return map.values();
    }

}
