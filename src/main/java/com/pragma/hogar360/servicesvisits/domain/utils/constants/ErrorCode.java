package com.pragma.hogar360.servicesvisits.domain.utils.constants;

import java.text.MessageFormat;

public enum ErrorCode {
    TIME_SLOT_NULL("ERR_TIME_SLOT_NULL", "The TimeSlot can not be null."),
    INVALID_HOME_ID("ERR_INVALID_HOME_ID", "The homeId must be positive."),
    INVALID_SELLER_ID("ERR_INVALID_SELLER_ID", "The sellerId must be positive."),
    INVALID_START_TIME("ERR_INVALID_START_TIME", "The startTime cannot be null."),
    INVALID_END_TIME("ERR_INVALID_END_TIME", "The endTime cannot be null."),
    TIME_RANGE_INCONSISTENCY("ERR_TIME_RANGE_INCONSISTENCY", "The startTime must be before or equal to endTime."),
    TIME_INCONSISTENCY("ERR_TIME_INCONSISTENCY", "The endTime must be after the startTime."),
    START_TIME_OUT_OF_RANGE("ERR_START_TIME_OUT_OF_RANGE", "The startTime must be within the next 3 weeks."),
    HOME_ID_TOO_LARGE("ERR_HOME_ID_TOO_LARGE", "The homeId must be a reasonable value less than {0}."),
    HOME_NOT_FOUND("ERR_HOME_NOT_FOUND", "No home was found in the response."),
    HOME_ID_MISMATCH("ERR_HOME_ID_MISMATCH", "Requested home ID {0} does not match actual home ID {1}."),
    TIME_SLOT_OVERLAP("ERR_TIME_SLOT_OVERLAP", "There is already a time slot for this seller and home that overlaps with the given time range."),
    INVALID_PAGE("ERR_INVALID_PAGE", "The page number must be non-negative."),
    INVALID_SIZE("ERR_INVALID_SIZE", "The size must be greater than zero."),
    INVALID_SORT_BY("ERR_INVALID_SORT_BY", "The sortBy value is invalid. Allowed values are: {0}."),
    INVALID_SORT_DIRECTION("ERR_INVALID_SORT_DIRECTION", "The sortDirection must be either 'ASC' or 'DESC'.");

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