package com.pragma.hogar360.servicesvisits.infrastructure.adapters;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;
import com.pragma.hogar360.servicesvisits.infrastructure.mappers.TimeSlotEntityMapper;
import com.pragma.hogar360.servicesvisits.infrastructure.repositories.mysql.TimeSlotRepository;
import com.pragma.hogar360.servicesvisits.infrastructure.utils.InfrastructureConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeSlotPersistenceAdapter implements TimeSlotPersistencePort {
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotEntityMapper timeSlotEntityMapper;

    @Override
    public void save(TimeSlotModel timeSlot){
        timeSlotRepository.save(timeSlotEntityMapper.toEntity(timeSlot));
    }
    @Override
    public List<TimeSlotModel> findOverlappingTimeSlots(Long sellerId, Long homeId, LocalDateTime startTime, LocalDateTime endTime) {
        return timeSlotRepository.findOverlappingTimeSlots(sellerId, homeId, startTime, endTime)
                .stream()
                .map(timeSlotEntityMapper::toModel)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<TimeSlotModel> getTimeSlotById(Long id){
        return timeSlotRepository.findById(id).map(timeSlotEntityMapper::toModel);
    }
    @Override
    public Pagination<TimeSlotModel> findTimeSlotByFilters(
            TimeSlotQueryModel timeSlotQueryModel,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection
    ){
        Sort sort;
        if (sortBy == null || sortBy.isEmpty()) {
            sort = Sort.by(InfrastructureConstants.SLOT_TIME_START);
        } else {
            sort = Sort.by(sortBy);
            if (sortDirection != null && sortDirection.equalsIgnoreCase(InfrastructureConstants.SORT_BY_DESC)) {
                sort = sort.descending();
            }
        }
        Pageable pagination = PageRequest.of(page,size,sort);
        return timeSlotEntityMapper.pageToPagination(
                timeSlotRepository.findAvailableTimeSlotsBySellerAndHomeId(
                        timeSlotQueryModel.getSellerId(),
                        timeSlotQueryModel.getHomeId(),
                        timeSlotQueryModel.getFilterStartTime(),
                        timeSlotQueryModel.getFilterEndTime(),
                        timeSlotQueryModel.getNow(),
                        pagination
                )
        );
    }
}

