package com.pragma.hogar360.servicesvisits.domain.exceptions;

public class NotFoundException extends DomainException {
    public NotFoundException(String errorCode,String message) {
        super(errorCode,message);
    }
}
