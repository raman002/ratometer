package io.cfapps.ratometer.support;

import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Response<String>> handleValidationException(ValidationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(Response.of(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<String>> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.of(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!"));
    }
}
