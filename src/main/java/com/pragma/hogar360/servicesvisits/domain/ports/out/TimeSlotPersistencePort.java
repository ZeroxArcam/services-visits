package com.pragma.hogar360.servicesvisits.domain.ports.out;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotPersistencePort {
    void save(TimeSlotModel timeSlot);
    List<TimeSlotModel> findOverlappingTimeSlots(Long sellerId, Long homeId, LocalDateTime startTime, LocalDateTime endTime);
}
