package com.pragma.hogar360.servicesvisits.infrastructure.mappers;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;
import com.pragma.hogar360.servicesvisits.infrastructure.entities.TimeSlotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface TimeSlotEntityMapper {

    TimeSlotModel toModel(TimeSlotEntity timeSlotEntity);

    TimeSlotEntity toEntity(TimeSlotModel timeSlotModel);

    @Mapping(target = "items", expression = "java(timeSlotPage.getContent().stream().map(this::toModel).toList())")
    @Mapping(target = "totalElements",expression = "java(timeSlotPage.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(timeSlotPage.getTotalPages())")
    @Mapping(target = "pageNumber", expression = "java(timeSlotPage.getNumber())")
    @Mapping(target = "pageSize",expression = "java(timeSlotPage.getSize())")
    Pagination<TimeSlotModel> pageToPagination(Page<TimeSlotEntity> timeSlotPage);
}
