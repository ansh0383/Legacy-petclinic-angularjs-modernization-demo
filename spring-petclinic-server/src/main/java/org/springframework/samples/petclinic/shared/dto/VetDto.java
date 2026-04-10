package org.springframework.samples.petclinic.shared.dto;

import java.util.List;

public record VetDto(
    int id,
    String firstName,
    String lastName,
    List<SpecialtyDto> specialties
) {}
