package com.pragma.hogar360.servicesvisits.domain.exceptions;

public class ConflictException extends DomainException {
    public ConflictException(String errorCode,String message) {
        super(errorCode,message);
    }
}
