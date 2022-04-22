package kr.co.postofsale.member;

import kr.co.postofsale.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberEntity extends BaseEntity {

    private String identity;
    private String password;
    private MemberRole memberRole;

    @Builder
    public MemberEntity(String identity, String password, MemberRole memberRole) {
        this.identity = identity;
        this.password = password;
        this.memberRole = memberRole;
    }

    public void updateRole(MemberRole role){
        this.memberRole = role;
    }
    public void updatePassword(String newPassword){
        this.password = newPassword;
    }
}
