package com.pragma.hogar360.servicesvisits.infrastructure.repositories.mysql;

import com.pragma.hogar360.servicesvisits.infrastructure.entities.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
    Optional<VisitEntity> findById(Long id);
    Long countByTimeSlot_Id(Long timeSlotId);
    boolean existsByTimeSlot_IdAndCustomerEmail(Long timeSlotId, String email);
}
