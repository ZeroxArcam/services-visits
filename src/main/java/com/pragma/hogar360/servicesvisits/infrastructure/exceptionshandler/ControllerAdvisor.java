package com.pragma.hogar360.servicesvisits.infrastructure.exceptionshandler;

import com.pragma.hogar360.servicesvisits.domain.exceptions.BadRequestException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.ConflictException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.NotFoundException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.ServiceUnavailableException;
import com.pragma.hogar360.servicesvisits.infrastructure.exceptions.UnauthorizedException;
import feign.RetryableException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(BadRequestException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ExceptionResponse> handleServiceUnavailable(ServiceUnavailableException ex) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.SERVICE_UNAVAILABLE.name(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> handleConflict(ConflictException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ExceptionResponse> handleRetryable(RetryableException ex) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.REQUEST_TIMEOUT.name(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }
}
