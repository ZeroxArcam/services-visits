package com.pragma.hogar360.servicesvisits.application.client.dto;
import java.time.LocalDate;

public record HomeResponse(Long id,
                           String neighborhood,
                           String address,
                           String description,
                           String category,
                           Integer numberOfRooms,
                           Integer numberOfBathrooms,
                           Double price,
                           String cityName,
                           String departmentName,
                           LocalDate activePublicationDate,
                           Long userId
) {}