package kr.co.postofsale.infrastructure.exception;

//논리 예외
public class BusinessLogicException extends RuntimeException{

    private int status;

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(String message, int status){
        super(message);
        this.status = status;
    }
}
