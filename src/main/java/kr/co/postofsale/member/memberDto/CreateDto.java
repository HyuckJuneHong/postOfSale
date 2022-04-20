package kr.co.postofsale.member.memberDto;

import kr.co.postofsale.member.MemberRole;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDto {
    private String identity;
    private String password;
    private String checkPassword;
    private MemberRole memberRole;

}
