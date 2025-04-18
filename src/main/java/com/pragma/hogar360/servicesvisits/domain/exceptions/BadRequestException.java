package com.pragma.hogar360.servicesvisits.domain.exceptions;

public class BadRequestException extends DomainException {
    public BadRequestException(String errorCode,String message) {
        super(errorCode,message);
    }
}
