package org.springframework.samples.petclinic.shared.dto.mapper;

import org.springframework.samples.petclinic.shared.dto.SpecialtyDto;
import org.springframework.samples.petclinic.shared.dto.VetDto;
import org.springframework.samples.petclinic.vet.model.Vet;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VetMapper {
    public VetDto toDto(Vet vet) {
        return new VetDto(
            vet.getId(),
            vet.getFirstName(),
            vet.getLastName(),
            vet.getSpecialties().stream()
                .map(s -> new SpecialtyDto(s.getId(), s.getName()))
                .collect(Collectors.toList())
        );
    }
}
