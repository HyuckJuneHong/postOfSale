package kr.co.postofsale.member;

import io.swagger.annotations.ApiModelProperty;
import kr.co.postofsale.member.enumClass.Gender;
import kr.co.postofsale.member.enumClass.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {

        @ApiModelProperty(example = "사용할 아이디") //example - 지정된 임의 테스트 값을 입력 함
        @NotBlank(message = "아이디를 입력해주세요.")
        private String identity;
        @ApiModelProperty(example = "사용할 비밀번호")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
        @ApiModelProperty(example = "사용할 비밀번호 체크")
        @NotBlank(message = "확인 비밀번호를 입력해주세요.")
        private String checkPassword;

        @ApiModelProperty(example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
        @ApiModelProperty(example = "010-xxxx-xxxx")
        @NotBlank(message = "전화번호를 입력해주세요.")
        private String phone;
        @ApiModelProperty(example = "19980122")
        @NotBlank(message = "생년월일을 입력해주세요.")
        private String birth;
        @ApiModelProperty(example = "MALE or FEMALE")
        @NotBlank(message = "성별을 입력하세요.")
        private Gender gender;
        @ApiModelProperty(example = "ROLE_MEMBER or ROLE_MANAGER or ROLE_ADMIN")
        @NotBlank(message = "유저 권한을 입력해주세요.")
        private MemberRole memberRole;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LOGIN {

        @ApiModelProperty(example = "로그인 아이디")
        @NotBlank(message = "아이디를 입력해주세요.")
        private String identity;
        @ApiModelProperty(example = "로그인할 비밀번호")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TOKEN{

        @ApiModelProperty(example = "사용자 인증을 위한 accessToken")
        private String accessToken;
        @ApiModelProperty(example = "자동 로그인을 위한 refreshToken")
        private String refreshToken;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class READ{

        @ApiModelProperty(example = "아이디")
        private String identity;

        @ApiModelProperty(example = "홍길동")
        private String name;
        @ApiModelProperty(example = "MALE or FEMALE")
        private Gender gender;
        @ApiModelProperty(example = "19980122")
        private String birth;
        @ApiModelProperty(example = "010-xxxx-xxxx")
        private String phone;
        @ApiModelProperty(example = "해당 권한")
        private MemberRole memberRole;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UPDATE{

        @ApiModelProperty(example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
        @ApiModelProperty(example = "010-xxxx-xxxx")
        @NotBlank(message = "전화번호를 입력해주세요.")
        private String phone;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE_PASSWORD {

        @ApiModelProperty(example = "기존 비밀번호")
        @NotBlank(message = "기존 비밀번호를 입력해주세요.")
        private String password;

        @ApiModelProperty(example = "새 비밀번호")
        @NotBlank(message = "새 비밀번호를 입력해주세요.")
        private String newPassword;
        @ApiModelProperty(example = "새 비밀번호 확인")
        @NotBlank(message = "확인 비밀번호를 입력해주세요.")
        private String reNewPassword;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UPDATE_ROLE{

        @ApiModelProperty(example = "변경할 회원 아이디")
        @NotBlank(message = "권한을 변경할 아이디를 입력해주세요.")
        private String identity;

        @ApiModelProperty(example = "변경할 권한: ROLE_MEMBER or ROLE_MANAGER")
        @NotBlank(message = "새로 부여할 권한을 입력하세요. ROLE_MEMBER or ROLE_MANAGER")
        private MemberRole memberRole;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class RESET_CHECK {

        @ApiModelProperty(example = "아이디")
        private String identity;

        @ApiModelProperty(example = "홍길동")
        private String name;
        @ApiModelProperty(example = "19980122")
        private String birth;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class RESET_PASSWORD {

        @ApiModelProperty(example = "아이디")
        private String identity;

        @ApiModelProperty(example = "새 비밀번호")
        @NotBlank(message = "새 비밀번호를 입력해주세요.")
        private String newPassword;
        @ApiModelProperty(example = "새 비밀번호 확인")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String reNewPassword;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DELETE {

        @ApiModelProperty(example = "삭제할 아이디")
        private String identity;
        @ApiModelProperty(example = "삭제할 비밀번호")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }

}
