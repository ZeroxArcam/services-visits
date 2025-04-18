package com.pragma.hogar360.servicesvisits.domain.utils.validation;

import com.pragma.hogar360.servicesvisits.domain.exceptions.BadRequestException;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.utils.constants.DomainConstants;
import com.pragma.hogar360.servicesvisits.domain.utils.constants.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

public class Validator {

    public static void validateTimeSlotConsistency(TimeSlotModel timeSlot) {
        List<TimeSlotValidator> validators = List.of(
                new TimeSlotValidator(ts -> ts != null, ErrorCode.TIME_SLOT_NULL),
                new TimeSlotValidator(ts -> ts.getHomeId() != null && ts.getHomeId() > DomainConstants.MIN_ID_NUMBER, ErrorCode.INVALID_HOME_ID),
                new TimeSlotValidator(ts -> ts.getHomeId() != null && ts.getHomeId() > DomainConstants.MIN_ID_NUMBER && ts.getHomeId() < DomainConstants.MAX_ID_NUMBER, ErrorCode.HOME_ID_TOO_LARGE),
                new TimeSlotValidator(ts -> ts.getSellerId() != null && ts.getSellerId() > DomainConstants.MIN_ID_NUMBER, ErrorCode.INVALID_SELLER_ID),
                new TimeSlotValidator(ts -> ts.getStartTime() != null, ErrorCode.INVALID_START_TIME),
                new TimeSlotValidator(ts -> ts.getEndTime() != null, ErrorCode.INVALID_END_TIME),
                new TimeSlotValidator(ts -> ts.getStartTime() != null && ts.getEndTime() != null &&
                        ts.getEndTime().isAfter(ts.getStartTime()), ErrorCode.TIME_INCONSISTENCY),
                new TimeSlotValidator(ts -> {
                    if (ts.getStartTime() == null) return false;
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime threeWeeksFromNow = now.plusWeeks(DomainConstants.WEEKS_NUMBER);
                    return !ts.getStartTime().isBefore(now) && !ts.getStartTime().isAfter(threeWeeksFromNow);
                }, ErrorCode.START_TIME_OUT_OF_RANGE)
        );

        validate(timeSlot, validators);
    }
    public static void validateTimeSlotQueryConsistency(TimeSlotPaginationRequest paginationRequest) {
        List<PaginationValidator> validators = List.of(
                //new PaginationValidator(p -> p.getQuery().getSellerId() > DomainConstants.MIN_ID_NUMBER, ErrorCode.INVALID_SELLER_ID),
                //new PaginationValidator(p -> p.getQuery().getHomeId() > DomainConstants.MIN_ID_NUMBER, ErrorCode.INVALID_HOME_ID),
                new PaginationValidator(p -> validateDateRange(p.getQuery().getFilterStartTime(), p.getQuery().getFilterEndTime()), ErrorCode.TIME_RANGE_INCONSISTENCY),
                new PaginationValidator(p -> p.getPage() != null && p.getPage() >= DomainConstants.MIN_PAGE_NUMBER, ErrorCode.INVALID_PAGE),
                new PaginationValidator(p -> p.getSize() != null && p.getSize() > DomainConstants.MIN_PAGE_SIZE, ErrorCode.INVALID_SIZE),
                new PaginationValidator(p -> DomainConstants.VALID_SORT_BY_VALUES.contains(p.getSortBy()), ErrorCode.INVALID_SORT_BY),
                new PaginationValidator(p -> p.getSortDirection().equalsIgnoreCase(DomainConstants.SORT_ASC) || p.getSortDirection().equalsIgnoreCase(DomainConstants.SORT_DESC), ErrorCode.INVALID_SORT_DIRECTION)
        );

        validatePagination(paginationRequest, validators);
    }



    public static void validatePagination(TimeSlotPaginationRequest paginationRequest, List<PaginationValidator> validators) {
        validators.forEach(validator -> {
            if (!validator.test(paginationRequest)) {
                throw new BadRequestException(validator.getErrorCode(), validator.getErrorMessage());
            }
        });
    }



    public static void validate(TimeSlotModel timeSlot, List<TimeSlotValidator> validators) {
        validators.forEach(validator -> {
            if (!validator.test(timeSlot)) {
                throw new BadRequestException(validator.getErrorCode(), validator.getErrorMessage());
            }
        });
    }
    public static boolean validateDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime != null && endTime != null && !startTime.isAfter(endTime);
    }


}
