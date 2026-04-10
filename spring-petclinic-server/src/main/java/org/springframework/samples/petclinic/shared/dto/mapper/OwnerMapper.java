package org.springframework.samples.petclinic.shared.dto.mapper;

import org.springframework.samples.petclinic.shared.dto.OwnerCreateRequest;
import org.springframework.samples.petclinic.shared.dto.OwnerDto;
import org.springframework.samples.petclinic.shared.dto.PetSummaryDto;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OwnerMapper {
    public OwnerDto toDto(Owner owner) {
        return new OwnerDto(
            owner.getId(),
            owner.getFirstName(),
            owner.getLastName(),
            owner.getAddress(),
            owner.getCity(),
            owner.getTelephone(),
            owner.getPets().stream()
                .map(p -> new PetSummaryDto(p.getId(), p.getName(), p.getBirthDate(),
                    p.getType() != null ? p.getType().getName() : null))
                .collect(Collectors.toList())
        );
    }

    public Owner toEntity(OwnerCreateRequest request) {
        Owner owner = new Owner();
        owner.setFirstName(request.firstName());
        owner.setLastName(request.lastName());
        owner.setAddress(request.address());
        owner.setCity(request.city());
        owner.setTelephone(request.telephone());
        return owner;
    }

    public void updateEntity(Owner owner, OwnerCreateRequest request) {
        owner.setFirstName(request.firstName());
        owner.setLastName(request.lastName());
        owner.setAddress(request.address());
        owner.setCity(request.city());
        owner.setTelephone(request.telephone());
    }
}
