package com.pragma.hogar360.servicesvisits.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record PagedTimeSlotResponse(
        @JsonProperty("timeSlots")
        @Schema(
                description = "Paged list of time slots",
                example = """
                [
                    {
                        "id": 10,
                        "startTime": "2025-04-20T10:00:00",
                        "endTime": "2025-04-20T11:00:00",
                        "homeId": 5,
                        "sellerId": 1
                    },
                    {
                        "id": 11,
                        "startTime": "2025-04-20T11:00:00",
                        "endTime": "2025-04-20T12:00:00",
                        "homeId": 5,
                        "sellerId": 1
                    }
                ]"""
        )
        List<TimeSlotResponse> timeSlots,

        @Schema(
                description = "Total elements in all pages",
                example = "15"
        )
        long totalElements,

        @Schema(
                description = "Total available pages",
                example = "3"
        )
        int totalPages,

        @Schema(
                description = "Current page (base 0)",
                example = "0"
        )
        int pageNumber,

        @Schema(
                description = "Total elements per page",
                example = "5"
        )
        int pageSize
) {}