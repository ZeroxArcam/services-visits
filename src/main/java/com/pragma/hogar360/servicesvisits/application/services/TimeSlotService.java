package com.pragma.hogar360.servicesvisits.application.services;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveTimeSlotRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.PagedTimeSlotResponse;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveTimeSlotResponse;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;

public interface TimeSlotService {
    SaveTimeSlotResponse save(SaveTimeSlotRequest request, Long userId);
    PagedTimeSlotResponse findTimeSlotByFilters(
            TimeSlotQueryModel timeSlotQueryModel,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection
    );
}
