package com.pragma.hogar360.servicesvisits.domain.exceptions;

public class ServiceUnavailableException extends RuntimeException {
  public ServiceUnavailableException(String message) {
    super(message);
  }
}
