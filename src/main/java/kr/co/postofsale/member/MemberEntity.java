package kr.co.postofsale.member;

import kr.co.postofsale.common.BadRequestException;
import kr.co.postofsale.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity extends BaseEntity {

    private String identity;
    private String password;
    private MemberRole memberRole;


    public void updateRole(MemberRole role){
        this.memberRole = role;
    }
    public void updatePassword(String newPassword){
        this.password = newPassword;
    }
}
