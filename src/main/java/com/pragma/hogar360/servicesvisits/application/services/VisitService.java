package com.pragma.hogar360.servicesvisits.application.services;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveVisitRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveVisitResponse;

public interface VisitService {
    SaveVisitResponse save(SaveVisitRequest request, String email);
}
