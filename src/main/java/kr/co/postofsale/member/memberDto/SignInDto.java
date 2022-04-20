package kr.co.postofsale.member.memberDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInDto {

    private String identity;
    private String password;
}
