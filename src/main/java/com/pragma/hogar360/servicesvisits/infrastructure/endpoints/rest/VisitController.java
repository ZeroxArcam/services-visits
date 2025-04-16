package com.pragma.hogar360.servicesvisits.infrastructure.endpoints.rest;

import com.pragma.hogar360.servicesvisits.application.dto.request.SaveVisitRequest;
import com.pragma.hogar360.servicesvisits.application.dto.response.SaveVisitResponse;
import com.pragma.hogar360.servicesvisits.application.services.VisitService;
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
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
@Tag(name = "Visits", description = "Operations related to scheduled visits")
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/create")
    @Operation(summary = "Schedule a new visit", description = "Allows a buyer to schedule a visit for a property.")
    @ApiResponse(responseCode = "201", description = "Visit scheduled successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "409", description = "Maximum number of buyers reached for this time slot", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<SaveVisitResponse> scheduleVisit(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Time slot ID to schedule", required = true,
                    content = @Content(schema = @Schema(implementation = SaveVisitRequest.class)))
            @RequestBody SaveVisitRequest saveVisitRequest,
            HttpServletRequest request
    ) {
        String email = (String) request.getAttribute(JwtAuthenticationFilter.USER_EMAIL_REQUEST_ATTRIBUTE);

        if(email.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        SaveVisitResponse saveVisitResponse = visitService.save(saveVisitRequest,email);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveVisitResponse);
    }
}