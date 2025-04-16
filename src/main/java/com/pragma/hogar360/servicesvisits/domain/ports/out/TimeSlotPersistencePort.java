package com.pragma.hogar360.servicesvisits.domain.ports.out;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotPersistencePort {
    void save(TimeSlotModel timeSlot);
    List<TimeSlotModel> findOverlappingTimeSlots(Long sellerId, Long homeId, LocalDateTime startTime, LocalDateTime endTime);
    Optional<TimeSlotModel> getTimeSlotById(Long id);

    Pagination<TimeSlotModel> findTimeSlotByFilters(
            TimeSlotQueryModel timeSlotQueryModel,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection
    );

}
