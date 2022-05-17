package kr.co.postofsale.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ResponseFormat<T> {

    @ApiModelProperty(example = "성공은 1 or 실패는 2 or 토큰 만료는 3")
    private int code;
    @ApiModelProperty(example = "true or false")
    private Boolean check;
    @ApiModelProperty(example = "성공 or 에러 메시지 or 토큰 만료")
    private String description;
    private T data;

    public static ResponseFormat ok(){
        return ResponseFormat.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .check(true)
                .data(null)
                .description("성공")
                .build();
    }
    public static <T> ResponseFormat ok(T data){
        return ResponseFormat.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .check(true)
                .data(data)
                .description("성공")
                .build();
    }
    public static ResponseFormat fail(String message){

        return ResponseFormat.builder()
                .code(ResponseCode.FAIL.getCode())
                .check(false)
                .data(null)
                .description(message)
                .build();
    }

    public static ResponseFormat expire(){

        return ResponseFormat.builder()
                .code(ResponseCode.TOKEN_EXPIRED.getCode())
                .check(false)
                .data(null)
                .description("토큰 만료")
                .build();
    }
}
