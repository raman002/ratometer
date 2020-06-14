package io.cfapps.ratometer.util.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class Response<Type> {

    @JsonIgnore
    private HttpStatus httpStatus;
    private Integer code;
    private String message;
    private Type result;

    public Response() {
    }

    public Response(HttpStatus status, Type result) {
        httpStatus = status;
        this.code = status.value();
        this.result = result;
    }

    public Response(HttpStatus status, String message) {
        httpStatus = status;
        this.code = status.value();
        this.message = message;
    }

    public Response(HttpStatus status, String message, Type result) {
        httpStatus = status;
        this.code = status.value();
        this.message = message;
        this.result = result;
    }


    public static <Type> Response<Type> ok(Type result) {
        return of(HttpStatus.OK, result);
    }

    public static <Type> Response<Type> ok(String message) {
        return of(HttpStatus.OK, message);
    }

    public static <Type> Response<Type> ok(String message, Type result) {
        return of(HttpStatus.OK, message, result);
    }

    public static <Type> Response<Type> of(HttpStatus status, Type result) {
        return new Response(status, result);
    }

    public static <Type> Response<Type> of(HttpStatus status, String message) {
        return new Response(status, message);
    }

    public static <Type> Response<Type> of(HttpStatus status, String message, Type result) {
        return new Response(status, message, result);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
        httpStatus = HttpStatus.resolve(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getResult() {
        return result;
    }

    public void setResult(Type result) {
        this.result = result;
    }
}
