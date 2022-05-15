package kr.co.postofsale.infrastructure.exception;


//유효하지 않은 토큰 예외
public class JwtTokenInvalidException extends UserDefineException{

    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
