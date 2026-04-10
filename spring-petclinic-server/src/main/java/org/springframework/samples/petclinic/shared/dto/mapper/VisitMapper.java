package org.springframework.samples.petclinic.shared.dto.mapper;

import org.springframework.samples.petclinic.shared.dto.VisitCreateRequest;
import org.springframework.samples.petclinic.shared.dto.VisitDto;
import org.springframework.samples.petclinic.visit.model.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {
    public VisitDto toDto(Visit visit) {
        return new VisitDto(visit.getId(), visit.getDate(), visit.getDescription());
    }

    public Visit toEntity(VisitCreateRequest request) {
        Visit visit = new Visit();
        visit.setDate(request.date());
        visit.setDescription(request.description());
        return visit;
    }
}
