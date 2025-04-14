package com.pragma.hogar360.servicesvisits.infrastructure.endpoints.rest;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveTimeSlotRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveTimeSlotResponse;
import com.pragma.hogar360.servicesvisits.application.services.TimeSlotService;
import com.pragma.hogar360.servicesvisits.infrastructure.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}


