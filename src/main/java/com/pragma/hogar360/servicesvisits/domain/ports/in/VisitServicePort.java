package com.pragma.hogar360.servicesvisits.domain.ports.in;

import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;

public interface VisitServicePort {
    void save(Long timeSlotId, String email);
}
