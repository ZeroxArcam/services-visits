package com.pragma.hogar360.servicesvisits.application.mappers;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveTimeSlotRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.TimeSlotResponse;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TimeSlotDtoMapper {

    TimeSlotModel requestToModel(SaveTimeSlotRequest request);

    TimeSlotResponse modelToResponse(TimeSlotModel model);

}
