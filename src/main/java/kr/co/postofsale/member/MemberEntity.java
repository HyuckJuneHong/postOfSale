package kr.co.postofsale.member;

import kr.co.postofsale.common.BaseEntity;
import kr.co.postofsale.member.enumClass.Gender;
import kr.co.postofsale.member.enumClass.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberEntity extends BaseEntity{

    private String identity;
    private String password;
    private String name;
    private String phone;
    private String birth;
    private Gender gender;
    private MemberRole memberRole;
    private String refreshToken;

    @Builder
    public MemberEntity(String identity, String password, String name
            , String phone, String birth, Gender gender, MemberRole memberRole) {
        this.identity = identity;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.gender = gender;
        this.memberRole = memberRole;
    }

    public void updateRole(MemberRole role){
        this.memberRole = role;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateMember(MemberDto.UPDATE update) {
        this.phone = update.getPhone();
        this.name = update.getName();
    }
}
