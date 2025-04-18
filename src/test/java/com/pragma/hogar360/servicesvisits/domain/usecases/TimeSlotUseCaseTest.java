package com.pragma.hogar360.servicesvisits.domain.usecases;

import com.pragma.hogar360.servicesvisits.domain.exceptions.BadRequestException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.ConflictException;
import com.pragma.hogar360.servicesvisits.domain.exceptions.NotFoundException;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotModel;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.utils.Pagination;
import com.pragma.hogar360.servicesvisits.domain.utils.constants.DomainConstants;
import com.pragma.hogar360.servicesvisits.factory.TimeSlotModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TimeSlotUseCaseTest {

    @Mock
    private TimeSlotPersistencePort timeSlotPersistencePort;

    @InjectMocks
    private TimeSlotUseCase timeSlotUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_Success() {
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        System.out.println("StartTime: " + timeSlot.getStartTime());
        System.out.println("EndTime: " + timeSlot.getEndTime());
        doNothing().when(timeSlotPersistencePort).save(timeSlot);
        when(timeSlotPersistencePort.findOverlappingTimeSlots(anyLong(), anyLong(), any(), any())).thenReturn(Collections.emptyList());
        assertDoesNotThrow(() -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, times(1)).save(timeSlot);
    }

    @Test
    void testSave_TimeSlotOverlap() {
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        when(timeSlotPersistencePort.findOverlappingTimeSlots(anyLong(), anyLong(), any(), any())).thenReturn(List.of(timeSlot));

        assertThrows(ConflictException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(timeSlot);
    }
    @Test
    void testSave_NullTimeSlot() {
        TimeSlotModel timeSlot = null;

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(null);
    }

    @Test
    void testSave_InvalidHomeId_Null() {
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlot.setHomeId(null);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_InvalidHomeId_NegativeOrZero() {
        TimeSlotModel timeSlotNegative = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlotNegative.setHomeId(0L);
        TimeSlotModel timeSlotZero = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlotZero.setHomeId(-1L);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlotNegative));
        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlotZero));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_InvalidSellerId_Null() {
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlot.setSellerId(null);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_InvalidSellerId_NegativeOrZero() {
        TimeSlotModel timeSlotNegative = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlotNegative.setSellerId(0L);
        TimeSlotModel timeSlotZero = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlotZero.setSellerId(-1L);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlotNegative));
        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlotZero));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_InvalidStartTime_Null() {
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlot.setStartTime(null);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_InvalidEndTime_Null() {
        // Arrange
        TimeSlotModel timeSlot = TimeSlotModelFactory.createDefaultTimeSlotModel();
        timeSlot.setEndTime(null);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_TimeInconsistency() {
        LocalDateTime startTime = LocalDateTime.now().plusHours(2);
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        TimeSlotModel timeSlot = TimeSlotModelFactory.createTimeSlotModelWithStartAndEndTime(startTime, endTime);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_StartTimeOutOfRange_Past() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        TimeSlotModel timeSlot = TimeSlotModelFactory.createTimeSlotModelWithStartTime(startTime);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testSave_StartTimeOutOfRange_Future() {

        LocalDateTime startTime = LocalDateTime.now().plusWeeks(DomainConstants.WEEKS_NUMBER + 1);
        TimeSlotModel timeSlot = TimeSlotModelFactory.createTimeSlotModelWithStartTime(startTime);

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.save(timeSlot));
        verify(timeSlotPersistencePort, never()).save(any());
    }

    @Test
    void testValidateHome_Success() {

        Long requestedHomeId = 1L;
        Long actualHomeId = 1L;

        assertDoesNotThrow(() -> timeSlotUseCase.validateHome(requestedHomeId, actualHomeId));
    }

    @Test
    void testValidateHome_NotFound() {
        Long requestedHomeId = 1L;
        Long actualHomeId = null;

        assertThrows(NotFoundException.class, () -> timeSlotUseCase.validateHome(requestedHomeId, actualHomeId));
    }

    @Test
    void testValidateHome_IdMismatch() {
        Long requestedHomeId = 1L;
        Long actualHomeId = 2L;

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.validateHome(requestedHomeId, actualHomeId));
    }

    @Test
    void testFindTimeSlotByFilters_Success() {
        TimeSlotQueryModel queryModel = new TimeSlotQueryModel();
        LocalDateTime startTimeFilter = LocalDateTime.now().minusDays(1);
        LocalDateTime endTimeFilter = LocalDateTime.now().plusDays(1);
        queryModel.setFilterStartTime(startTimeFilter);
        queryModel.setFilterEndTime(endTimeFilter);
        int page = 0;
        int size = 10;
        String sortBy = "startTime";
        String sortDirection = "asc";
        Pagination<TimeSlotModel> expectedPagination = new Pagination<>();

        when(timeSlotPersistencePort.findTimeSlotByFilters(queryModel, page, size, sortBy, sortDirection)).thenReturn(expectedPagination);
        Pagination<TimeSlotModel> result = timeSlotUseCase.findTimeSlotByFilters(queryModel, page, size, sortBy, sortDirection);

        assertNotNull(result);
        assertEquals(expectedPagination, result);
        verify(timeSlotPersistencePort, times(1)).findTimeSlotByFilters(queryModel, page, size, sortBy, sortDirection);
    }
    @Test
    void testFindTimeSlotByFilters_InvalidPage() {
        TimeSlotQueryModel queryModel = new TimeSlotQueryModel();
        int page = -1;
        int size = 10;
        String sortBy = "startTime";
        String sortDirection = "asc";

        assertThrows(BadRequestException.class, () -> timeSlotUseCase.findTimeSlotByFilters(queryModel, page, size, sortBy, sortDirection));
        verify(timeSlotPersistencePort, never()).findTimeSlotByFilters(any(), anyInt(), anyInt(), anyString(), anyString());
    }
    @ParameterizedTest
    @CsvSource({
            "0, 0, startTime, asc",
            "0, 10, invalidField, asc",
            "0, 10, startTime, invalidDir"
    })
    void testFindTimeSlotByFilters_InvalidParameters(int page, int size, String sortBy, String sortDirection) {

        TimeSlotQueryModel queryModel = new TimeSlotQueryModel();

        assertThrows(BadRequestException.class, () ->
                timeSlotUseCase.findTimeSlotByFilters(queryModel, page, size, sortBy, sortDirection));

        verify(timeSlotPersistencePort, never()).findTimeSlotByFilters(any(), anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void testExistHome_NotFound() {
        assertThrows(NotFoundException.class, () -> timeSlotUseCase.existHome(true));
    }

    @Test
    void testExistHome_Success() {
        assertDoesNotThrow(() -> timeSlotUseCase.existHome(false));
    }

}