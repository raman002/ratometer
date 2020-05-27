package io.cfapps.ratometer.controller.support;

import io.cfapps.ratometer.util.web.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public interface ValidationErrorProcessor {

    default <Type> ResponseEntity<Type> processValidationErrors(BindingResult result) {

        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ObjectError> globalError = result.getGlobalErrors();
        List<String> messages = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            messages.add(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }

        for (ObjectError objectError : globalError) {
            messages.add(String.format("%s: %s", objectError.getObjectName(), objectError.getDefaultMessage()));
        }

        ResponseEntity responseEntity = ResponseEntity.badRequest().body(Response.of(HttpStatus.BAD_REQUEST, messages));

        return responseEntity;
    }
}
