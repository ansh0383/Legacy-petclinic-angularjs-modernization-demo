package org.springframework.samples.petclinic.shared.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

public record OwnerCreateRequest(
    @NotEmpty String firstName,
    @NotEmpty String lastName,
    @NotEmpty String address,
    @NotEmpty String city,
    @NotEmpty @Digits(fraction = 0, integer = 10) String telephone
) {}
