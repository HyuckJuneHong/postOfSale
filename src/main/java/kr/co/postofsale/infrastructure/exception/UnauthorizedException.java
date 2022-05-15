package kr.co.postofsale.infrastructure.exception;


public class UnauthorizedException extends BusinessLogicException{
    public UnauthorizedException(String message, int status) {
        super(message, status);
    }
}
