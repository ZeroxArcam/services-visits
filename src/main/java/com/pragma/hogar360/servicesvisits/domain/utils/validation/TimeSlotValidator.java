package com.pragma.hogar360.servicesvisits.domain.utils.validation;

import com.pragma.hogar360.servicesvisits.domain.utils.constants.ErrorCode;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;

import java.util.function.Predicate;

public class TimeSlotValidator {

    private final Predicate<TimeSlotModel> predicate;
    private final ErrorCode errorCode;

    public TimeSlotValidator(Predicate<TimeSlotModel> predicate, ErrorCode errorCode) {
        this.predicate = predicate;
        this.errorCode = errorCode;
    }

    public boolean test(TimeSlotModel timeSlot) {
        return predicate.test(timeSlot);
    }

    public String getErrorMessage() {
        return errorCode.message();
    }

    public String getErrorCode() {
        return errorCode.code();
    }
}
