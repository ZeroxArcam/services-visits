package com.pragma.hogar360.servicesvisits.infrastructure.mappers;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.infrastructure.entities.TimeSlotEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeSlotEntityMapper {

    TimeSlotModel toModel(TimeSlotEntity timeSlotEntity);

    TimeSlotEntity toEntity(TimeSlotModel timeSlotModel);
}
