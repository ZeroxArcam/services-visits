package com.pragma.hogar360.servicesvisits.application.services.implementation;

import com.pragma.hogar360.servicesvisits.application.client.dto.PagedHomeResponse;
import com.pragma.hogar360.servicesvisits.application.client.dto.HomeResponse;
import com.pragma.hogar360.servicesvisits.application.client.services.HomeServiceClient;
import com.pragma.hogar360.servicesvisits.application.dto.request.SaveTimeSlotRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.PagedTimeSlotResponse;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveTimeSlotResponse;
import com.pragma.hogar360.servicesvisits.application.dto.response.TimeSlotResponse;
import com.pragma.hogar360.servicesvisits.application.mappers.TimeSlotDtoMapper;
import com.pragma.hogar360.servicesvisits.application.services.TimeSlotService;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.domain.ports.in.TimeSlotServicePort;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImplementation implements TimeSlotService {

    private static final Logger logger = LoggerFactory.getLogger(TimeSlotServiceImplementation.class);

    private final TimeSlotServicePort timeSlotServicePort;
    private final TimeSlotDtoMapper timeSlotDtoMapper;
    private final HomeServiceClient homeServiceClient;

    @Override
    public SaveTimeSlotResponse save(SaveTimeSlotRequest request, Long userId) {
        logger.info("Iniciando la creación de TimeSlot para homeId: {} y userId: {}", request.homeId(), userId);
        logger.info("Intentando buscar homeId: {} para userId: {}", request.homeId(), userId);
        PagedHomeResponse homeResponse = homeServiceClient.searchHomes(0, 1, "price","ASC",null,null,userId, request.homeId(), null,null,null,null,null,null, null);

        logger.info("Respuesta del microservicio de home para homeId {} y userId {}: {}", request.homeId(), userId, homeResponse);
        logger.info("Número de casas encontradas en la respuesta: {}", homeResponse.homes().size());

        List<HomeResponse> homes = homeResponse.homes();
        HomeResponse home = homes.get(0);

        logger.info("Tipo de home.id(): {}", home.id().getClass().getName());
        logger.info("Tipo de request.homeId(): {}", request.homeId().getClass().getName());

        timeSlotServicePort.validateHome(request.homeId(),home.id());

        TimeSlotModel timeSlot = timeSlotDtoMapper.requestToModel(request);
        timeSlot.setSellerId(userId);
        timeSlotServicePort.save(timeSlot);
        logger.info("TimeSlot guardado para homeId: {} y userId: {}", timeSlot.getHomeId(), userId);

        return new SaveTimeSlotResponse("ok", LocalDateTime.now());
    }

    @Override
    public PagedTimeSlotResponse findTimeSlotByFilters(TimeSlotQueryModel timeSlotQueryModel, Integer page, Integer size, String sortBy, String sortDirection){
        Pagination<TimeSlotModel> timeSlotPagination = timeSlotServicePort.findTimeSlotByFilters(timeSlotQueryModel,page,size,sortBy,sortDirection);
        List<TimeSlotResponse> timeSlotResponses = timeSlotPagination.getItems().stream().map(timeSlotDtoMapper::modelToResponse).toList();
        return new PagedTimeSlotResponse(
                timeSlotResponses,
                timeSlotPagination.getTotalElements(),
                timeSlotPagination.getTotalPages(),
                timeSlotPagination.getPageNumber(),
                timeSlotPagination.getPageSize()
        );
    }
}