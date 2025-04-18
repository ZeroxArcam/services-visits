package com.pragma.hogar360.servicesvisits.infrastructure.exceptions;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
