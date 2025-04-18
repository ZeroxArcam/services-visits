package com.pragma.hogar360.servicesvisits.application.services.implementation;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveVisitRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveVisitResponse;
import com.pragma.hogar360.servicesvisits.application.services.VisitService;
import com.pragma.hogar360.servicesvisits.domain.ports.in.VisitServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitServiceImplementation implements VisitService {
    private final VisitServicePort visitServicePort;

    @Override
    public SaveVisitResponse save(SaveVisitRequest request, String email){
        visitServicePort.save(request.timeSlotId(), email);
        return new SaveVisitResponse("ok", LocalDateTime.now());
    }
}
