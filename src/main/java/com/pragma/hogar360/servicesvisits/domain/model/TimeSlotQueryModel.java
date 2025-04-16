package com.pragma.hogar360.servicesvisits.domain.model;
import java.time.LocalDateTime;

public class TimeSlotQueryModel {
    private Long homeId;
    private LocalDateTime filterStartTime;
    private LocalDateTime filterEndTime;
    private LocalDateTime now;
    private Long sellerId;

    public TimeSlotQueryModel(){}

    public TimeSlotQueryModel(Long homeId, LocalDateTime filterStartTime, LocalDateTime filterEndTime, LocalDateTime now, Long sellerId) {
        this.homeId = homeId;
        this.filterStartTime = filterStartTime;
        this.filterEndTime = filterEndTime;
        this.now = now;
        this.sellerId = sellerId;
    }

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public LocalDateTime getFilterStartTime() {
        return filterStartTime;
    }

    public void setFilterStartTime(LocalDateTime filterStartTime) {
        this.filterStartTime = filterStartTime;
    }

    public LocalDateTime getFilterEndTime() {
        return filterEndTime;
    }

    public void setFilterEndTime(LocalDateTime filterEndTime) {
        this.filterEndTime = filterEndTime;
    }

    public LocalDateTime getNow() {
        now=LocalDateTime.now();
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}