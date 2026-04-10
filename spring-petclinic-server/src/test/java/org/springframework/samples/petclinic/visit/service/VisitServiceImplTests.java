package org.springframework.samples.petclinic.visit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.visit.model.Visit;
import org.springframework.samples.petclinic.visit.repository.VisitRepository;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VisitServiceImplTests {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitServiceImpl visitService;

    @Test
    public void shouldSaveVisit() {
        Visit visit = new Visit();
        visit.setDescription("checkup");

        visitService.saveVisit(visit);

        verify(visitRepository).save(visit);
    }
}
