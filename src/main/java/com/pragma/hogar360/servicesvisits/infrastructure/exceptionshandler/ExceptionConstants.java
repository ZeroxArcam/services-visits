package com.pragma.hogar360.servicesvisits.infrastructure.exceptionshandler;

public class ExceptionConstants {

    public static final String JWT_EXPIRED_ERROR_CODE = "JWT_EXPIRED";
    public static final String INVALID_TOKEN_ERROR_CODE = "INVALID_TOKEN";
    public static final String JWT_EXPIRED_MESSAGE_EN = "The JWT token has expired. Please log in again to obtain a new token.";
    public static final String INVALID_TOKEN_MESSAGE_EN = "The provided token is invalid.";
    public static final String MISSING_TOKEN_DATA_ERROR_CODE = "H360-401-001";
    public static final String MISSING_TOKEN_DATA_MESSAGE_EN = "The JWT token is missing required user ID or email.";

}
