package com.pragma.hogar360.servicesvisits.domain.usecases;

import com.pragma.hogar360.servicesvisits.domain.exceptions.BadRequestException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.ConflictException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.NotFoundException;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;
import com.pragma.hogar360.servicesvisits.domain.ports.in.VisitServicePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.VisitPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.utils.constants.DomainConstants;
import com.pragma.hogar360.servicesvisits.domain.utils.constants.ErrorCode;

import java.util.Optional;

public class VisitUseCase implements VisitServicePort {

    private final VisitPersistencePort visitPersistencePort;
    private final TimeSlotPersistencePort timeSlotPersistencePort;

    public VisitUseCase(VisitPersistencePort visitPersistencePort, TimeSlotPersistencePort timeSlotPersistencePort){
        this.visitPersistencePort = visitPersistencePort;
        this.timeSlotPersistencePort = timeSlotPersistencePort;
    }

    @Override
    public void save(Long timeSlotId, String email) {
        if (timeSlotId == null) {
            throw new BadRequestException(
                    ErrorCode.INVALID_TIME_SLOT_ID.code(),
                    ErrorCode.INVALID_TIME_SLOT_ID.message()
            );
        }
        if (email == null || email.trim().isEmpty()) {
            throw new BadRequestException(
                    ErrorCode.INVALID_CUSTOMER_EMAIL.code(),
                    ErrorCode.INVALID_CUSTOMER_EMAIL.message());}
        Optional<TimeSlotModel> timeSlotOptional = timeSlotPersistencePort.getTimeSlotById(timeSlotId);
        if (timeSlotOptional.isEmpty()) {
            throw new NotFoundException(
                    ErrorCode.TIME_SLOT_NOT_FOUND.code(),
                    ErrorCode.TIME_SLOT_NOT_FOUND.message(timeSlotId));}

        Long existingVisitsCount = visitPersistencePort.countVisitsByTimeSlotId(timeSlotId);
        if (existingVisitsCount >= DomainConstants.MAX_VISITS_PER_SLOT) {
            throw new ConflictException(
                    ErrorCode.VISIT_LIMIT_REACHED.code(),
                    ErrorCode.VISIT_LIMIT_REACHED.message(existingVisitsCount)
            );
        }

        boolean alreadyBooked = visitPersistencePort.existsByTimeSlot_IdAndCustomerEmail(timeSlotId, email);
        if (alreadyBooked) {
            throw new ConflictException(
                    ErrorCode.ALREADY_BOOKED.code(),
                    ErrorCode.ALREADY_BOOKED.message(email, timeSlotId)
            );
        }

        VisitModel visitModel = new VisitModel(null, timeSlotOptional.get(), email);
        visitPersistencePort.save(visitModel);
    }
}
