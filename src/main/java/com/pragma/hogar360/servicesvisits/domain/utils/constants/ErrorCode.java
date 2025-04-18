package com.pragma.hogar360.servicesvisits.domain.constants;

import java.text.MessageFormat;

public enum ErrorCode {
    INVALID_HOME_ID("ERR_INVALID_HOME_ID", "The homeId must be positive."),
    INVALID_SELLER_ID("ERR_INVALID_SELLER_ID", "The sellerId must be positive."),
    INVALID_START_TIME("ERR_INVALID_START_TIME", "The startTime cannot be null."),
    INVALID_END_TIME("ERR_INVALID_END_TIME", "The endTime cannot be null."),
    TIME_INCONSISTENCY("ERR_TIME_INCONSISTENCY", "The endTime must be after the startTime."),
    START_TIME_OUT_OF_RANGE("ERR_START_TIME_OUT_OF_RANGE", "The startTime must be within the next 3 weeks."),
    HOME_ID_TOO_LARGE("ERR_HOME_ID_TOO_LARGE", "The homeId must be a reasonable value less than {0}.")
    ;

    private final String code;
    private final String messageTemplate;

    ErrorCode(String code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }

    public String code() {
        return code;
    }

    public String message(Object... args) {
        return MessageFormat.format(messageTemplate, args);
    }
}