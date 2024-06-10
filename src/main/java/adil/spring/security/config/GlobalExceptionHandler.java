package adil.spring.security.config;

import adil.spring.security.DTO.common.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleIllegalArgumentExceptions(Exception exception, WebRequest webRequest) {
        HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        return this.handleExceptionInternal(exception, new ErrorDTO(errorCode.value(), exception.getMessage()), new HttpHeaders(), errorCode, webRequest);
    }

}
