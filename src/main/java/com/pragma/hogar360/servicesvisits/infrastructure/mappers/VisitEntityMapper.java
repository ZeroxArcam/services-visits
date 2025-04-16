package com.pragma.hogar360.servicesvisits.infrastructure.mappers;

import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;
import com.pragma.hogar360.servicesvisits.infrastructure.entities.VisitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitEntityMapper {

    VisitModel toModel(VisitEntity visitEntity);
    VisitEntity toEntity(VisitModel visitModel);
}
