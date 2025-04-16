package com.pragma.hogar360.servicesvisits.domain.model;

public class VisitModel {
    private Long id;
    private TimeSlotModel timeSlot;
    private String customerEmail;

    VisitModel(){}

    public VisitModel(Long id, TimeSlotModel timeSlot, String customerEmail) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.customerEmail = customerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSlotModel getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotModel timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
