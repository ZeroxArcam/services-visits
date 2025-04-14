package com.pragma.hogar360.servicesvisits.application.services;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveTimeSlotRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveTimeSlotResponse;

public interface TimeSlotService {
    SaveTimeSlotResponse save(SaveTimeSlotRequest request, Long userId);
}
