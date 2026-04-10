package org.springframework.samples.petclinic.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

public record VisitCreateRequest(
    @JsonFormat(pattern = "yyyy-MM-dd") @NotNull Date date,
    @Size(max = 8192) String description
) {}
