package com.pragma.hogar360.servicesvisits.domain.ports.in;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;

public interface TimeSlotServicePort {
    void save(TimeSlotModel timeSlot);
    void validateHome(Long requestedHomeId, Long actualHomeId);
    Pagination<TimeSlotModel> findTimeSlotByFilters(
            TimeSlotQueryModel timeSlotQueryModel,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection
    );

}
