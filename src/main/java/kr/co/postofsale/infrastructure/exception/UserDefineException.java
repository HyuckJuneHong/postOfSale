package kr.co.postofsale.infrastructure.exception;

public class UserDefineException extends RuntimeException{

    private String errorMessage;

    public UserDefineException(String message) {
        super(message);
    }

    public UserDefineException(String message, String errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }
}
