package com.pragma.hogar360.servicesvisits.infrastructure.repositories.mysql;

import com.pragma.hogar360.servicesvisits.infrastructure.entities.TimeSlotEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {

    @Query("""
        SELECT ts FROM TimeSlotEntity ts
        WHERE ts.sellerId = :sellerId
        AND ts.homeId = :homeId
        AND (
            (:startTime BETWEEN ts.startTime AND ts.endTime) OR
            (:endTime BETWEEN ts.startTime AND ts.endTime) OR
            (ts.startTime BETWEEN :startTime AND :endTime) OR
            (ts.endTime BETWEEN :startTime AND :endTime)
        )
    """)
    List<TimeSlotEntity> findOverlappingTimeSlots(
            @Param("sellerId") Long sellerId,
            @Param("homeId") Long homeId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    Optional<TimeSlotEntity> findById(Long id);

    @Query("""
        SELECT ts FROM TimeSlotEntity ts
        LEFT JOIN VisitEntity v ON ts.id = v.timeSlot.id
        WHERE ts.startTime >= :now
        AND (:sellerId IS NULL OR ts.sellerId = :sellerId)
        AND (:homeId IS NULL OR ts.homeId = :homeId)
        AND (:filterStartTime IS NULL OR :filterEndTime IS NULL OR ts.startTime BETWEEN :filterStartTime AND :filterEndTime)
        GROUP BY ts
        HAVING COUNT(v.id) < 2
        ORDER BY :#{#pageable.sort}
        """)
    Page<TimeSlotEntity> findAvailableTimeSlotsBySellerAndHomeId(
            @Param("sellerId") Long sellerId,
            @Param("homeId") Long homeId,
            @Param("filterStartTime") LocalDateTime filterStartTime,
            @Param("filterEndTime") LocalDateTime filterEndTime,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );

}
