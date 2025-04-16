package com.pragma.hogar360.servicesvisits.application.mappers;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveVisitRequest;
import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitDtoMapper {
    @Mapping(source = "timeSlotId", target = "timeSlot.id")
    VisitModel requestToModel(SaveVisitRequest request);
}
