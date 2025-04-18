package com.pragma.hogar360.servicesvisits.domain.exceptions;

public class ServiceUnavailableException extends DomainException {
    public ServiceUnavailableException(String errorCode,String message) {
        super(errorCode,message);
    }
}
