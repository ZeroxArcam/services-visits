package com.pragma.hogar360.servicesvisits.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    private final String errorCode;

    public UnauthorizedException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return errorCode;
    }
}
