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
import com.pragma.hogar360.servicesvisits.application.utils.ExceptionConstants;
import com.pragma.hogar360.servicesvisits.application.utils.AppConstants;
import com.pragma.hogar360.servicesvisits.domain.exceptions.ServiceUnavailableException;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.domain.ports.in.TimeSlotServicePort;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;
import feign.FeignException;
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
        try {
            PagedHomeResponse homeResponse = homeServiceClient.searchHomes(
                    AppConstants.DEFAULT_PAGE,
                    AppConstants.DEFAULT_SIZE,
                    AppConstants.DEFAULT_SORT_BY,
                    AppConstants.DEFAULT_SORT_DIRECTION,
                    null, null, userId, request.homeId(), null, null, null, null, null, null, null
            );

            boolean homeNotFound = homeResponse.totalElements() == AppConstants.HOME_NOT_FOUND_TOTAL_ELEMENTS_VALUE || homeResponse.homes().isEmpty();
            timeSlotServicePort.existHome(homeNotFound);
            HomeResponse home = homeResponse.homes().get(AppConstants.FIRST_ELEMENT_INDEX);
            timeSlotServicePort.validateHome(request.homeId(), home.id());

        } catch (FeignException e) {
            throw new ServiceUnavailableException(ExceptionConstants.SERVICE_UNAVAILABLE_CODE, ExceptionConstants.SERVICE_UNAVAILABLE_MESSAGE);
        }

        TimeSlotModel timeSlot = timeSlotDtoMapper.requestToModel(request);
        timeSlot.setSellerId(userId);
        timeSlotServicePort.save(timeSlot);
        return new SaveTimeSlotResponse(AppConstants.TIME_SLOT_CREATED_RESPONSE, LocalDateTime.now());
    }

    @Override
    public PagedTimeSlotResponse findTimeSlotByFilters(TimeSlotQueryModel timeSlotQueryModel, Integer page, Integer size, String sortBy, String sortDirection) {
        Pagination<TimeSlotModel> timeSlotPagination = timeSlotServicePort.findTimeSlotByFilters(
                timeSlotQueryModel, page, size, sortBy, sortDirection
        );

        List<TimeSlotResponse> timeSlotResponses = timeSlotPagination.getItems()
                .stream()
                .map(timeSlotDtoMapper::modelToResponse)
                .toList();

        return new PagedTimeSlotResponse(
                timeSlotResponses,
                timeSlotPagination.getTotalElements(),
                timeSlotPagination.getTotalPages(),
                timeSlotPagination.getPageNumber(),
                timeSlotPagination.getPageSize()
        );
    }
}
