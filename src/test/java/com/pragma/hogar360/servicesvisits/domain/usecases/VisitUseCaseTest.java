package com.pragma.hogar360.servicesvisits.domain.usecases;

import com.pragma.hogar360.servicesvisits.domain.exceptions.BadRequestException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.ConflictException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.NotFoundException;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.VisitModel;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.VisitPersistencePort;
import com.pragma.hogar360.servicesvisits.factory.TimeSlotModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VisitUseCaseTest {

    @Mock
    private VisitPersistencePort visitPersistencePort;

    @Mock
    private TimeSlotPersistencePort timeSlotPersistencePort;

    @InjectMocks
    private VisitUseCase visitUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_Success() {
        Long timeSlotId = 1L;
        String customerEmail = "test@example.com";
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        when(timeSlotPersistencePort.getTimeSlotById(timeSlotId)).thenReturn(Optional.of(timeSlot));
        when(visitPersistencePort.countVisitsByTimeSlotId(timeSlotId)).thenReturn(0L);
        when(visitPersistencePort.existsByTimeSlot_IdAndCustomerEmail(timeSlotId, customerEmail)).thenReturn(false);
        doNothing().when(visitPersistencePort).save(any(VisitModel.class));

        assertDoesNotThrow(() -> visitUseCase.save(timeSlotId, customerEmail));
        verify(timeSlotPersistencePort, times(1)).getTimeSlotById(timeSlotId);
        verify(visitPersistencePort, times(1)).countVisitsByTimeSlotId(timeSlotId);
        verify(visitPersistencePort, times(1)).existsByTimeSlot_IdAndCustomerEmail(timeSlotId, customerEmail);
        verify(visitPersistencePort, times(1)).save(any(VisitModel.class));
    }

    @Test
    void testSave_TimeSlotNotFound() {
        Long timeSlotId = 1L;
        String customerEmail = "test@example.com";
        when(timeSlotPersistencePort.getTimeSlotById(timeSlotId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> visitUseCase.save(timeSlotId, customerEmail));
        verify(timeSlotPersistencePort, times(1)).getTimeSlotById(timeSlotId);
        verify(visitPersistencePort, never()).countVisitsByTimeSlotId(anyLong());
        verify(visitPersistencePort, never()).existsByTimeSlot_IdAndCustomerEmail(anyLong(), anyString());
        verify(visitPersistencePort, never()).save(any(VisitModel.class));
    }

    @Test
    void testSave_VisitLimitReached() {
        // Arrange
        Long timeSlotId = 1L;
        String customerEmail = "zeroxCustomer@example.com";
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        when(timeSlotPersistencePort.getTimeSlotById(timeSlotId)).thenReturn(Optional.of(timeSlot));
        when(visitPersistencePort.countVisitsByTimeSlotId(timeSlotId)).thenReturn(2L);

        // Act & Assert
        assertThrows(ConflictException.class, () -> visitUseCase.save(timeSlotId, customerEmail));
        verify(timeSlotPersistencePort, times(1)).getTimeSlotById(timeSlotId);
        verify(visitPersistencePort, times(1)).countVisitsByTimeSlotId(timeSlotId);
        verify(visitPersistencePort, never()).existsByTimeSlot_IdAndCustomerEmail(anyLong(), anyString());
        verify(visitPersistencePort, never()).save(any(VisitModel.class));
    }

    @Test
    void testSave_AlreadyBooked() {
        // Arrange
        Long timeSlotId = 1L;
        String customerEmail = "test@example.com";
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        when(timeSlotPersistencePort.getTimeSlotById(timeSlotId)).thenReturn(Optional.of(timeSlot));
        when(visitPersistencePort.countVisitsByTimeSlotId(timeSlotId)).thenReturn(0L);
        when(visitPersistencePort.existsByTimeSlot_IdAndCustomerEmail(timeSlotId, customerEmail)).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> visitUseCase.save(timeSlotId, customerEmail));
        verify(timeSlotPersistencePort, times(1)).getTimeSlotById(timeSlotId);
        verify(visitPersistencePort, times(1)).countVisitsByTimeSlotId(timeSlotId);
        verify(visitPersistencePort, times(1)).existsByTimeSlot_IdAndCustomerEmail(timeSlotId, customerEmail);
        verify(visitPersistencePort, never()).save(any(VisitModel.class));
    }

    @Test
    void testSave_NullTimeSlotId() {
        // Arrange
        String customerEmail = "test@example.com";

        // Act & Assert
        assertThrows(BadRequestException.class, () -> visitUseCase.save(null, customerEmail));
        verify(timeSlotPersistencePort, never()).getTimeSlotById(any());
        verify(visitPersistencePort, never()).countVisitsByTimeSlotId(anyLong());
        verify(visitPersistencePort, never()).existsByTimeSlot_IdAndCustomerEmail(anyLong(), anyString());
        verify(visitPersistencePort, never()).save(any());
    }

    @Test
    void testSave_NullCustomerEmail() {
        // Arrange
        Long timeSlotId = 1L;

        // Act & Assert
        assertThrows(BadRequestException.class, () -> visitUseCase.save(timeSlotId, null));
        verify(timeSlotPersistencePort, never()).getTimeSlotById(anyLong());
        verify(visitPersistencePort, never()).countVisitsByTimeSlotId(anyLong());
        verify(visitPersistencePort, never()).existsByTimeSlot_IdAndCustomerEmail(anyLong(), any());
        verify(visitPersistencePort, never()).save(any());
    }

    @Test
    void testSave_EmptyCustomerEmail() {
        Long timeSlotId = 1L;
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        when(timeSlotPersistencePort.getTimeSlotById(timeSlotId)).thenReturn(Optional.of(timeSlot));

        assertThrows(BadRequestException.class, () -> visitUseCase.save(timeSlotId, ""));
        verify(visitPersistencePort, never()).countVisitsByTimeSlotId(anyLong());
        verify(visitPersistencePort, never()).existsByTimeSlot_IdAndCustomerEmail(anyLong(), anyString());
        verify(visitPersistencePort, never()).save(any());
    }
}