package org.springframework.samples.petclinic.shared.dto.mapper;

import org.springframework.samples.petclinic.shared.dto.PetDto;
import org.springframework.samples.petclinic.shared.dto.PetTypeDto;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PetMapper {
    private final VisitMapper visitMapper;

    public PetMapper(VisitMapper visitMapper) {
        this.visitMapper = visitMapper;
    }

    public PetDto toDto(Pet pet) {
        return new PetDto(
            pet.getId(),
            pet.getName(),
            pet.getBirthDate(),
            pet.getType() != null ? new PetTypeDto(pet.getType().getId(), pet.getType().getName()) : null,
            pet.getOwner() != null ? pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName() : null,
            pet.getVisits().stream().map(visitMapper::toDto).collect(Collectors.toList())
        );
    }

    public PetTypeDto toTypeDto(PetType type) {
        return new PetTypeDto(type.getId(), type.getName());
    }
}
