package kr.co.postofsale.member.enumClass;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum MemberRole {

    ROLE_MEMBER("MEMBER"),
    ROLE_MANAGER("MANAGER"),
    ROLE_ADMIN("ADMIN");

    private String role;

    public static MemberRole of(String role){
        return Arrays.stream(MemberRole.values())
                .filter(r->r.toString().equalsIgnoreCase(role))
                .findAny().orElseThrow(()-> new RuntimeException("해당 권한을 찾을 수 없습니다."));
    }

}
