package fast.campus.netflix.advice;

import fast.campus.netflix.controller.NetflixApiResponse;
import fast.campus.netflix.exception.ErrorCode;
import fast.campus.netflix.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GlobalExceptionAdvice {

    @ExceptionHandler(UserException.class)
    protected NetflixApiResponse<?> handleUserException(UserException e){
        log.error("error={}", e.getMessage(), e);
        return NetflixApiResponse.fail(e.getErrorCode(), e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    protected NetflixApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("error={}", e.getMessage(), e);
        return NetflixApiResponse.fail(ErrorCode.DEFAULT_ERROR, e.getMessage());
    }

}
