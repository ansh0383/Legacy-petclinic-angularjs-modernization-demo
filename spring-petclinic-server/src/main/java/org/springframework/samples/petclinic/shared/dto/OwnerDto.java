package org.springframework.samples.petclinic.shared.dto;

import java.util.List;

public record OwnerDto(
    int id,
    String firstName,
    String lastName,
    String address,
    String city,
    String telephone,
    List<PetSummaryDto> pets
) {}
