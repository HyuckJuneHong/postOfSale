package kr.co.postofsale.member;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import kr.co.postofsale.common.ResponseFormat;
import kr.co.postofsale.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pos/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepositoryImpl memberRepository;
    private final MemberServiceImpl memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiModelProperty("로그인")
    @PostMapping("/login")
    public ResponseFormat<MemberDto.TOKEN> login(@RequestBody MemberDto.LOGIN login){
        return ResponseFormat.ok(memberService.login(login));
    }

    @ApiOperation("비밀번호 재확인")
    @PostMapping("/password/check")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat<Boolean> reCheckPassword(@RequestBody String password) {
        return ResponseFormat.ok(memberService.reCheckPassword(password));
    }

    @ApiOperation("아이디 중복 확인")
    @PostMapping("/idneity/check")
    public ResponseFormat<Boolean> checkIdentity(@RequestBody String identity) {
        return ResponseFormat.ok(memberService.checkIdentity(identity));
    }

    @ApiOperation("회원가입")
    @PostMapping
    public ResponseFormat signUp(@RequestBody MemberDto.CREATE create) {
        memberService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 회원 정보 조회")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat<MemberDto.READ> getMemberSelf() {
        return ResponseFormat.ok(memberService.getMemberSelf());
    }

    @ApiOperation("해당 아이디 회원 정보 조회")
    @GetMapping("/read")
    public ResponseFormat<MemberDto.READ> getMemberIdentity(@RequestBody String identity){
        return ResponseFormat.ok(memberService.getMemberIdentity(identity));
    }

    @ApiOperation("모든 회원 정보 조회")
    @GetMapping("/readAll")
    public ResponseFormat<List<MemberDto.READ>> getMemberAll(){
        return ResponseFormat.ok(memberService.getMemberAll());
    }

    @ApiOperation("회원 정보 수정")
    @PutMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat updateUser(@RequestBody MemberDto.UPDATE update) {
        memberService.updateMember(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("비밀번호 변경")
    @PutMapping("/password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "사용자 인증을 위한 accessToken", paramType = "header", required = true)
    })
    public ResponseFormat updatePassword(@RequestBody MemberDto.UPDATE_PASSWORD update) {
        memberService.updatePassword(update);
        return ResponseFormat.ok();
    }

    //권한 변경
    @ApiModelProperty("권한 변경")
    @PutMapping("/role")
    public ResponseFormat updateRole(@RequestBody MemberDto.UPDATE_ROLE update){
        memberService.updateMemberRoLe(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("토큰 재발급")
    @GetMapping("/refresh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "accessToken 재발급을 위한 refreshToken", paramType = "header", required = true)
    })
    public ResponseFormat<String> refreshToken(@RequestHeader("Authorization") String refreshToken){
        return ResponseFormat.ok(jwtTokenProvider.createAccessToken(refreshToken));
    }

}
