package kr.co.postofsale.infrastructure.exception;

import kr.co.postofsale.common.ResponseFormat;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** @RestControllerAdvice
 * @ExceptionHandler가 하나의 클래스에 대한 것이라면, @ControllerAdvice는 모든 @Controller
 *  즉, 전역에서 발생할 수 있는 예외를 잡아 처리해주는 annotation.
 */
@RestControllerAdvice
@Log4j
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**@ExceptionHandler
     * GlovalExceptionHandler로 넘어온 예외가 NotFoundException 일 때, 해당 메소드가 실행.
     * @param notFoundException
     * @return
     */
    // @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseFormat<Void> notFoundExceptionHandler(NotFoundException notFoundException){
        return ResponseFormat.fail(notFoundException.getMessage());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseFormat<Void> badRequestExcetpionHandler(BadRequestException badRequestException){
        return ResponseFormat.fail(badRequestException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseFormat<Void> exceptionHandler(Exception exception) {
        logger.info("메세지 !!!!");
        return ResponseFormat.fail(exception.getMessage());
    }
}
