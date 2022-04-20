package kr.co.postofsale.member.memberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {
    private String identity;
    private String oldPassword;
    private String newPassword;
    private String checkPassword;
}
