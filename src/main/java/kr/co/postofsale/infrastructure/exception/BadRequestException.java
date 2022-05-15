package kr.co.postofsale.infrastructure.exception;

//좋지 않은 요청
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
