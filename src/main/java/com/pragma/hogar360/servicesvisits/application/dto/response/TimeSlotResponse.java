package com.pragma.hogar360.servicesvisits.application.dto.response;

import java.time.LocalDateTime;

public record TimeSlotResponse(
        Long id,
        Long sellerId,
        Long homeId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
