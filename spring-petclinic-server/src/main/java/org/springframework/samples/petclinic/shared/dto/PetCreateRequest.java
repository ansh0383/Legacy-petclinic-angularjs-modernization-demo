package org.springframework.samples.petclinic.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record PetCreateRequest(
    @NotEmpty String name,
    @JsonFormat(pattern = "yyyy-MM-dd") @NotNull Date birthDate,
    int typeId
) {}
