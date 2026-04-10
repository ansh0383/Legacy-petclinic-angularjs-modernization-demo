package org.springframework.samples.petclinic.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

public record PetDto(
    int id,
    String name,
    @JsonFormat(pattern = "yyyy-MM-dd") Date birthDate,
    PetTypeDto type,
    String ownerName,
    List<VisitDto> visits
) {}
