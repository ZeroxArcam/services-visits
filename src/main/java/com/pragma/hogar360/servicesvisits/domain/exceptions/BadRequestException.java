package com.pragma.hogar360.servicesvisits.domain.exceptions;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
