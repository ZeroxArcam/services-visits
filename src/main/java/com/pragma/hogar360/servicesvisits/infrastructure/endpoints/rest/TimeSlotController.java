package com.pragma.hogar360.servicesvisits.infrastructure.endpoints.rest;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveTimeSlotRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.PagedTimeSlotResponse;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveTimeSlotResponse;
import com.pragma.hogar360.servicesvisits.application.services.TimeSlotService;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.infrastructure.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/time-slots")
@RequiredArgsConstructor
@Tag(name = "Time Slots", description = "Operations related to available time slots for visits")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;
    @PostMapping("/create")
    @Operation(summary = "Create a new available time slot", description = "Creates a new time slot that a seller can offer for property visits.")
    @ApiResponse(responseCode = "201", description = "Time slot created successfully", content = @Content(schema = @Schema(implementation = SaveTimeSlotResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<SaveTimeSlotResponse> createTimeSlot(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Time slot data to create", required = true,
                    content = @Content(schema = @Schema(implementation = SaveTimeSlotRequest.class)))
            @RequestBody SaveTimeSlotRequest saveTimeSlotRequest,
            HttpServletRequest request
    ){
        Long userId = (Long) request.getAttribute(JwtAuthenticationFilter.USER_ID_REQUEST_ATTRIBUTE);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SaveTimeSlotResponse response = timeSlotService.save(saveTimeSlotRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/search")
    @Operation(summary = "Search available time slots", description = "Searches available time slots with pagination, sorting, and filtering.")
    @ApiResponse(responseCode = "200", description = "Search results", content = @Content(schema = @Schema(implementation = PagedTimeSlotResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<PagedTimeSlotResponse> searchAvailableTimeSlots(
            @Parameter(description = "Seller ID to filter time slots") @RequestParam(required = false) Long sellerId, // ¡Añadido este parámetro!
            @Parameter(description = "Home ID to filter time slots") @RequestParam(required = false) Long homeId,
            @Parameter(description = "Start date and time for filtering (YYYY-MM-DDTHH:MM:SS)") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "End date and time for filtering (YYYY-MM-DDTHH:MM:SS)") @RequestParam(required = false) LocalDateTime endTime,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Number of time slots per page") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Field to sort by (startTime, endTime, id)") @RequestParam(defaultValue = "startTime") String sortBy,
            @Parameter(description = "Sorting direction (ASC or DESC)") @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        TimeSlotQueryModel queryModel = new TimeSlotQueryModel();
        queryModel.setSellerId(sellerId);
        queryModel.setHomeId(homeId);
        queryModel.setFilterStartTime(startTime);
        queryModel.setFilterEndTime(endTime);

        PagedTimeSlotResponse response = timeSlotService.findTimeSlotByFilters(queryModel, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }


}


