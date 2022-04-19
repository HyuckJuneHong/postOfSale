package kr.co.postofsale.member;

import kr.co.postofsale.common.BadRequestException;
import kr.co.postofsale.common.BaseEntity;

public class MemberEntity extends BaseEntity {

    private String identity;
    private String password;
    private MemberRole memberRole;

    public MemberEntity(String identity, String password, MemberRole memberRole) {
        this.identity = identity;
        this.password = password;
        this.memberRole = memberRole;
    }

    public long getCode(){
        return code;
    }

    public void setCode(long code){
        this.code = code;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPassword() {
        return password;
    }

    public MemberRole getMemberRole() {
        return memberRole;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }
}
