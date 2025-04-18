package com.pragma.hogar360.servicesvisits.domain.model;

import java.time.LocalDateTime;

public class TimeSlotModel {
    private Long id;
    private Long sellerId;
    private Long homeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlotModel(){}

    public TimeSlotModel(LocalDateTime startTime, LocalDateTime endTime, Long homeId, Long sellerId, Long id) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.homeId = homeId;
        this.sellerId = sellerId;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
