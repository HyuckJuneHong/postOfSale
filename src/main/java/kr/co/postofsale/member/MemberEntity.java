package kr.co.postofsale.member;

import kr.co.postofsale.member.enumClass.Gender;
import kr.co.postofsale.member.enumClass.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Getter
@NoArgsConstructor
public class MemberEntity{


    private String identity;
    private String password;
    private String name;
    private String phone;
    private String birth;
    private Gender gender;
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
