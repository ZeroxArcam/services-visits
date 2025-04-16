package com.pragma.hogar360.servicesvisits.domain.ports.out;

import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;

import java.util.Optional;

public interface VisitPersistencePort {
    void save(VisitModel visitModel);
    Long countVisitsByTimeSlotId(Long timeSlotId);
    boolean existsByTimeSlot_IdAndCustomerEmail(Long timeSlotId, String email);
}
