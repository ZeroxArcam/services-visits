package com.pragma.hogar360.servicesvisits.domain.ports.in;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;

public interface TimeSlotServicePort {
    void save(TimeSlotModel timeSlot);
    void validateHome(Long requestedHomeId, Long actualHomeId);

}
