package org.springframework.samples.petclinic.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public record VisitDto(
    int id,
    @JsonFormat(pattern = "yyyy-MM-dd") Date date,
    String description
) {}
