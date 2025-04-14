package com.pragma.hogar360.servicesvisits.application.client.services;

import com.pragma.hogar360.servicesvisits.application.client.dto.PagedHomeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@FeignClient(name = "services-home")
public interface HomeServiceClient {

    @GetMapping("/api/v1/home/search")
    PagedHomeResponse searchHomes(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "price") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "locationId", required = false) Long locationId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "homeId", required = false) Long homeId,
            @RequestParam(value = "minRooms", required = false) Integer minRooms,
            @RequestParam(value = "maxRooms", required = false) Integer maxRooms,
            @RequestParam(value = "minBathrooms", required = false) Integer minBathrooms,
            @RequestParam(value = "maxBathrooms", required = false) Integer maxBathrooms,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "currentDate", defaultValue = "2025-01-01") LocalDate currentDate
    );
}