package com.pragma.hogar360.servicesvisits.factory;


import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;

public class VisitModelFactory {

    public static VisitModel createVisitModel(Long id, TimeSlotModel timeSlot, String customerEmail) {
        return new VisitModel(id, timeSlot, customerEmail);
    }

    public static VisitModel createDefaultVisitModel() {
        return createVisitModel(1L, TimeSlotModelFactory.createDefaultTimeSlotModel(), "test@example.com");
    }

    public static VisitModel createVisitModelWithTimeSlot(TimeSlotModel timeSlot) {
        return createVisitModel(1L, timeSlot, "test@example.com");
    }

    public static VisitModel createVisitModelWithCustomerEmail(String customerEmail) {
        return createVisitModel(1L, TimeSlotModelFactory.createDefaultTimeSlotModel(), customerEmail);
    }

    public static VisitModel createVisitModelWithoutId(TimeSlotModel timeSlot, String customerEmail) {
        return new VisitModel(null, timeSlot, customerEmail);
    }
}