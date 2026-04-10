package org.springframework.samples.petclinic.visit.service;

import org.springframework.samples.petclinic.visit.model.Visit;

public interface VisitService {
    void saveVisit(Visit visit);
}
