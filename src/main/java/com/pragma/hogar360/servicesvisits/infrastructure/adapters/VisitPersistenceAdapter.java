package com.pragma.hogar360.servicesvisits.infrastructure.adapters;

import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;
import com.pragma.hogar360.servicesvisits.domain.ports.out.VisitPersistencePort;
import com.pragma.hogar360.servicesvisits.infrastructure.mappers.VisitEntityMapper;
import com.pragma.hogar360.servicesvisits.infrastructure.repositories.mysql.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitPersistenceAdapter implements VisitPersistencePort {

    private final VisitRepository visitRepository;
    private final VisitEntityMapper visitEntityMapper;

    @Override
    public void save(VisitModel visitModel){
        visitRepository.save(visitEntityMapper.toEntity(visitModel));
    }

    @Override
    public Long countVisitsByTimeSlotId(Long timeSlotId) {
        return visitRepository.countByTimeSlot_Id(timeSlotId);
    }
    @Override
    public boolean existsByTimeSlot_IdAndCustomerEmail(Long timeSlotId, String email) {
        return visitRepository.existsByTimeSlot_IdAndCustomerEmail(timeSlotId, email);
    }

}
