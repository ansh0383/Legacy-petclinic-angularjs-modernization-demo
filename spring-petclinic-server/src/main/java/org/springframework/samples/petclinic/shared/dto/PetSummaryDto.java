package org.springframework.samples.petclinic.shared.dto;

import java.util.Date;

public record PetSummaryDto(
    int id,
    String name,
    Date birthDate,
    String typeName
) {}
