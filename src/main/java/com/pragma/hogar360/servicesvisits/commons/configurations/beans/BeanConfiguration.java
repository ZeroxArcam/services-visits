package com.pragma.hogar360.servicesvisits.commons.configurations.beans;

import com.pragma.hogar360.servicesvisits.domain.ports.in.TimeSlotServicePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.usecases.TimeSlotUseCase;
import com.pragma.hogar360.servicesvisits.infrastructure.adapters.TimeSlotPersistenceAdapter;
import com.pragma.hogar360.servicesvisits.infrastructure.mappers.TimeSlotEntityMapper;
import com.pragma.hogar360.servicesvisits.infrastructure.repositories.mysql.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotEntityMapper timeSlotEntityMapper;

    @Bean
    public TimeSlotServicePort timeSlotServicePort() {
        return new TimeSlotUseCase(timeSlotPersistencePort());
    }

    @Bean
    public TimeSlotPersistencePort timeSlotPersistencePort() {
        return new TimeSlotPersistenceAdapter(timeSlotRepository, timeSlotEntityMapper);
    }

    @Bean
    public TimeSlotUseCase timeSlotUseCase(TimeSlotPersistencePort timeSlotPersistencePort) {
        return new TimeSlotUseCase(timeSlotPersistencePort);
    }
}