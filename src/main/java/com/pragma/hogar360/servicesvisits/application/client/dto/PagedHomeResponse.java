package com.pragma.hogar360.servicesvisits.application.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PagedHomeResponse(
        @JsonProperty("home")
        List<HomeResponse> homes,
        long totalElements,
        int totalPages,
        int pageNumber,
        int pageSize
) {}