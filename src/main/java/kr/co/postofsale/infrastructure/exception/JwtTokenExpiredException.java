package kr.co.postofsale.infrastructure.exception;

//토큰 만기 예외
public class JwtTokenExpiredException extends BusinessLogicException{

    public JwtTokenExpiredException() {
        super("jwt token expired");
    }
}
