package com.pragma.hogar360.servicesvisits.infrastructure.utils;

public class JwtErrorResponse {
    private final String message;
    private final String errorCode;

    public JwtErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
