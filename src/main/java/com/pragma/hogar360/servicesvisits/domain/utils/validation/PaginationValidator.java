package com.pragma.hogar360.servicesvisits.domain.utils.validation;

import com.pragma.hogar360.servicesvisits.domain.utils.constants.ErrorCode;

import java.util.function.Predicate;

public class PaginationValidator {
    private final Predicate<TimeSlotPaginationRequest> predicate;
    private final ErrorCode errorCode;

    public PaginationValidator(Predicate<TimeSlotPaginationRequest> predicate, ErrorCode errorCode) {
        this.predicate = predicate;
        this.errorCode = errorCode;
    }
    public boolean test(TimeSlotPaginationRequest paginationRequest){
        return predicate.test(paginationRequest);
    }
    public String getErrorMessage() {
        return errorCode.message();
    }

    public String getErrorCode() {
        return errorCode.code();
    }

}
