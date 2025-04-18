package com.pragma.hogar360.servicesvisits.factory;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;

import java.time.LocalDateTime;

public class TimeSlotModelFactory {

    public static TimeSlotModel createTimeSlotModel(Long id, Long homeId, Long sellerId, LocalDateTime startTime, LocalDateTime endTime) {
        return new TimeSlotModel(startTime, endTime, homeId, sellerId, id);
    }

    public static TimeSlotModel createDefaultTimeSlotModel() {
        return createTimeSlotModel(1L, 1L, 1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3).plusHours(1));
    }

    public static TimeSlotModel createTimeSlotModelWithHomeId(Long homeId) {
        return createTimeSlotModel(1L, homeId, 1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3).plusHours(1));
    }

    public static TimeSlotModel createTimeSlotModelWithSellerId(Long sellerId) {
        return createTimeSlotModel(1L, 1L, sellerId, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3).plusHours(1));
    }

    public static TimeSlotModel createTimeSlotModelWithStartTime(LocalDateTime startTime) {
        return createTimeSlotModel(1L, 1L, 1L, startTime, startTime.plusHours(1));
    }

    public static TimeSlotModel createTimeSlotModelWithEndTime(LocalDateTime endTime) {
        return createTimeSlotModel(1L, 1L, 1L, LocalDateTime.now().plusDays(1), endTime);
    }

    public static TimeSlotModel createTimeSlotModelWithStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        return createTimeSlotModel(1L, 1L, 1L, startTime, endTime);
    }
}