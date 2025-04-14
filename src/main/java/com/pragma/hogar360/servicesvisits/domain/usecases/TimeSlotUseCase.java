package com.pragma.hogar360.servicesvisits.domain.usecases;

import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.ports.in.TimeSlotServicePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimeSlotUseCase implements TimeSlotServicePort {
    private final TimeSlotPersistencePort timeSlotPersistencePort;

    public TimeSlotUseCase(TimeSlotPersistencePort timeSlotPersistencePort){
        this.timeSlotPersistencePort = timeSlotPersistencePort;
    }

    @Override
    public void save(TimeSlotModel timeSlot){
        validateTimeSlot(timeSlot);
        validateTimeSlotOverlap(timeSlot);
        timeSlotPersistencePort.save(timeSlot);
    }

    private void validateTimeSlot(TimeSlotModel timeSlot) {
        if (timeSlot == null) {
            throw new IllegalArgumentException("El TimeSlot no puede ser nulo.");
        }
        if (timeSlot.getHomeId() == null || timeSlot.getHomeId() <= 0) {
            throw new IllegalArgumentException("El homeId debe ser positivo.");
        }
        if (timeSlot.getSellerId() == null || timeSlot.getSellerId() <= 0) {
            throw new IllegalArgumentException("El sellerId debe ser positivo.");
        }
        if (timeSlot.getStartTime() == null) {
            throw new IllegalArgumentException("La startTime no puede ser nula.");
        }
        if (timeSlot.getEndTime() == null) {
            throw new IllegalArgumentException("La endTime no puede ser nula.");
        }
        if (timeSlot.getEndTime() != null && timeSlot.getStartTime() != null && !timeSlot.getEndTime().isAfter(timeSlot.getStartTime())) {
            throw new IllegalArgumentException("La endTime debe ser posterior a la startTime.");
        }
        validateBookingWindow(timeSlot.getStartTime());
    }

    private void validateBookingWindow(LocalDateTime startTime) {
        if (startTime != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime threeWeeksFromNow = now.plus(3, ChronoUnit.WEEKS);
            if (startTime.isBefore(now) || startTime.isAfter(threeWeeksFromNow)) {
                throw new IllegalArgumentException("La startTime debe estar dentro de las próximas 3 semanas a partir de ahora.");
            }
        }
    }

    private void validateTimeSlotOverlap(TimeSlotModel timeSlot) {
        List<TimeSlotModel> overlappingSlots = timeSlotPersistencePort.findOverlappingTimeSlots(
                timeSlot.getSellerId(),
                timeSlot.getHomeId(),
                timeSlot.getStartTime(),
                timeSlot.getEndTime()
        );
        if (!overlappingSlots.isEmpty()) {
            throw new IllegalArgumentException("Ya existe un horario para este vendedor y casa que se solapa con el periodo especificado.");
        }
    }

    @Override
    public void validateHome(Long requestedHomeId, Long actualHomeId) {
        if (actualHomeId == null) {
            throw new IllegalArgumentException("No se encontró la casa con ID: " + requestedHomeId);
        }
        if (!actualHomeId.equals(requestedHomeId)) {
            throw new IllegalArgumentException("La casa con ID " + requestedHomeId + " no coincide con la casa encontrada.");
        }
    }
}