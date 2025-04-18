package com.pragma.hogar360.servicesvisits.commons.configurations.beans;

import com.pragma.hogar360.servicesvisits.domain.ports.in.TimeSlotServicePort;
import com.pragma.hogar360.servicesvisits.domain.ports.in.VisitServicePort; // Importa VisitServicePort
import com.pragma.hogar360.servicesvisits.domain.ports.out.TimeSlotPersistencePort;
import com.pragma.hogar360.servicesvisits.domain.ports.out.VisitPersistencePort; // Importa VisitPersistencePort
import com.pragma.hogar360.servicesvisits.domain.usecases.TimeSlotUseCase;
import com.pragma.hogar360.servicesvisits.domain.usecases.VisitUseCase; // Importa VisitUseCase
import com.pragma.hogar360.servicesvisits.infrastructure.adapters.TimeSlotPersistenceAdapter;
import com.pragma.hogar360.servicesvisits.infrastructure.adapters.VisitPersistenceAdapter; // Importa VisitPersistenceAdapter
import com.pragma.hogar360.servicesvisits.infrastructure.mappers.TimeSlotEntityMapper;
import com.pragma.hogar360.servicesvisits.infrastructure.mappers.VisitEntityMapper; // Importa VisitEntityMapper
import com.pragma.hogar360.servicesvisits.infrastructure.repositories.mysql.TimeSlotRepository;
import com.pragma.hogar360.servicesvisits.infrastructure.repositories.mysql.VisitRepository; // Importa VisitRepository
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotEntityMapper timeSlotEntityMapper;
    private final VisitRepository visitRepository; // Necesitamos el repositorio de visitas
    private final VisitEntityMapper visitEntityMapper; // Necesitamos el mapper de visitas

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

    @Bean
    public VisitServicePort visitServicePort() {
        return new VisitUseCase(visitPersistencePort(),timeSlotPersistencePort()); // Inyectamos las dependencias necesarias
    }

    @Bean
    public VisitPersistencePort visitPersistencePort() {
        return new VisitPersistenceAdapter(visitRepository,visitEntityMapper);
    }

    @Bean
    public VisitUseCase visitUseCase(VisitPersistencePort visitPersistencePort, TimeSlotPersistencePort timeSlotPersistencePort) {
        return new VisitUseCase(visitPersistencePort,timeSlotPersistencePort);
    }
}