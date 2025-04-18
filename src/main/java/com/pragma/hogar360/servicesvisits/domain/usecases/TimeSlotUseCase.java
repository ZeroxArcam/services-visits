package com.pragma.hogar360.servicesvisits.domain.usecases;

import com.pragma.hogar360.servicesvisits.domain.exceptions.BadRequestException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.ConflictException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.NotFoundException;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.domain.ports.in.TimeSlotServicePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;
import com.pragma.hogar360.servicesvisits.domain.utils.constants.ErrorCode;
import com.pragma.hogar360.servicesvisits.domain.utils.validation.TimeSlotPaginationRequest;
import com.pragma.hogar360.servicesvisits.domain.utils.validation.Validator;
import java.util.List;

public class TimeSlotUseCase implements TimeSlotServicePort {
    private final TimeSlotPersistencePort timeSlotPersistencePort;

    public TimeSlotUseCase(TimeSlotPersistencePort timeSlotPersistencePort){
        this.timeSlotPersistencePort = timeSlotPersistencePort;
    }

    @Override
    public void save(TimeSlotModel timeSlot){
        validateTimeSlot(timeSlot);
        validateTimeSlotOverlap(timeSlot);
        timeSlotPersistencePort.save(timeSlot);
    }
    private void validateTimeSlot(TimeSlotModel timeSlot) {
        Validator.validateTimeSlotConsistency(timeSlot);
    }
    public void existHome(boolean homeNotFound) {
        if (homeNotFound) {
            throw new NotFoundException(
                    ErrorCode.HOME_NOT_FOUND.code(),
                    ErrorCode.HOME_NOT_FOUND.message()
            );
        }
    }

    private void validateTimeSlotOverlap(TimeSlotModel timeSlot) {
        List<TimeSlotModel> overlappingSlots = timeSlotPersistencePort.findOverlappingTimeSlots(
                timeSlot.getSellerId(),
                timeSlot.getHomeId(),
                timeSlot.getStartTime(),
                timeSlot.getEndTime()
        );
        if (!overlappingSlots.isEmpty()) {
            throw new ConflictException(
                    ErrorCode.TIME_SLOT_OVERLAP.code(),
                    ErrorCode.TIME_SLOT_OVERLAP.message()
            );
        }
    }


    @Override
    public void validateHome(Long requestedHomeId, Long actualHomeId) {
        if (actualHomeId == null) {
            throw new NotFoundException(
                    ErrorCode.HOME_NOT_FOUND.code(),
                    ErrorCode.HOME_NOT_FOUND.message()
            );
        }
        if (!actualHomeId.equals(requestedHomeId)) {
            throw new BadRequestException(
                    ErrorCode.HOME_ID_MISMATCH.code(),
                    ErrorCode.HOME_ID_MISMATCH.message(requestedHomeId, actualHomeId)
            );
        }
    }

    @Override
    public Pagination<TimeSlotModel> findTimeSlotByFilters(
            TimeSlotQueryModel timeSlotQueryModel,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection
    ){
        TimeSlotPaginationRequest request = new TimeSlotPaginationRequest(timeSlotQueryModel,page,size,sortBy,sortDirection);
        Validator.validateTimeSlotQueryConsistency(request);
        return timeSlotPersistencePort.findTimeSlotByFilters(timeSlotQueryModel,page,size,sortBy,sortDirection);
    }

}