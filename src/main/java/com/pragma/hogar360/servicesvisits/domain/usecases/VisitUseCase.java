package com.pragma.hogar360.servicesvisits.domain.usecases;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;
import com.pragma.hogar360.servicesvisits.domain.ports.in.VisitServicePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.VisitPersistencePort;
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
        Optional<TimeSlotModel> timeSlotOptional = timeSlotPersistencePort.getTimeSlotById(timeSlotId);
        if (timeSlotOptional.isEmpty()){
            throw new IllegalArgumentException("The time slot with ID " + timeSlotId + " does not exist");
        }
        TimeSlotModel timeSlot = timeSlotOptional.get();
        Long existingVisitsCount = visitPersistencePort.countVisitsByTimeSlotId(timeSlotId);


        if (existingVisitsCount >= 2){
            throw new IllegalStateException("Maximum 2 customers allowed for this time slot. There are already " + existingVisitsCount + " bookings.");
        }
        boolean alreadyBooked = visitPersistencePort.existsByTimeSlot_IdAndCustomerEmail(timeSlotId, email);

        if (alreadyBooked) {
            throw new IllegalStateException("You have already booked this time slot.");
        }

        VisitModel visitModel = new VisitModel(null, timeSlot, email);
        visitPersistencePort.save(visitModel);
    }
}