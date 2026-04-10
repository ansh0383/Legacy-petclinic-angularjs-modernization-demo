package org.springframework.samples.petclinic.pet.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.customer.service.OwnerService;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;
import org.springframework.samples.petclinic.pet.service.PetService;
import org.springframework.samples.petclinic.shared.dto.PetCreateRequest;
import org.springframework.samples.petclinic.shared.dto.PetDto;
import org.springframework.samples.petclinic.shared.dto.PetTypeDto;
import org.springframework.samples.petclinic.shared.dto.mapper.PetMapper;
import org.springframework.samples.petclinic.shared.web.AbstractResourceController;
import org.springframework.samples.petclinic.shared.web.error.ResourceNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Pet", description = "Pet management APIs")
public class PetController extends AbstractResourceController {

    private final PetService petService;
    private final OwnerService ownerService;
    private final PetMapper petMapper;

    public PetController(PetService petService, OwnerService ownerService, PetMapper petMapper) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petMapper = petMapper;
    }

    @GetMapping("/pet-types")
    @Operation(summary = "List all pet types")
    @ApiResponse(responseCode = "200", description = "List of pet types")
    public List<PetTypeDto> getPetTypes() {
        return petService.findPetTypes().stream()
            .map(petMapper::toTypeDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/owners/{ownerId}/pets")
    @Operation(summary = "List all pets for an owner")
    @ApiResponse(responseCode = "200", description = "List of pets")
    public List<PetDto> listPets(@PathVariable int ownerId) {
        Owner owner = ownerService.findOwnerById(ownerId);
        return owner.getPets().stream()
            .map(petMapper::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}")
    @Operation(summary = "Find a pet by ID")
    @ApiResponse(responseCode = "200", description = "Pet found")
    @ApiResponse(responseCode = "404", description = "Pet not found")
    public PetDto findPet(@PathVariable int ownerId, @PathVariable int petId) {
        Pet pet = petService.findPetById(petId);
        validatePetOwner(pet, ownerId);
        return petMapper.toDto(pet);
    }

    @PostMapping("/owners/{ownerId}/pets")
    @Operation(summary = "Create a new pet for an owner")
    @ApiResponse(responseCode = "201", description = "Pet created")
    public ResponseEntity<PetDto> createPet(@PathVariable int ownerId, @Valid @RequestBody PetCreateRequest request) {
        Pet pet = new Pet();
        Owner owner = ownerService.findOwnerById(ownerId);
        owner.addPet(pet);

        pet.setName(request.name());
        pet.setBirthDate(request.birthDate());
        pet.setType(findPetType(request.typeId()));

        petService.savePet(pet);
        PetDto dto = petMapper.toDto(pet);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(pet.getId()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/owners/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update a pet")
    @ApiResponse(responseCode = "204", description = "Pet updated")
    public void updatePet(@PathVariable int ownerId, @PathVariable int petId, @Valid @RequestBody PetCreateRequest request) {
        Pet pet = petService.findPetById(petId);
        validatePetOwner(pet, ownerId);
        pet.setName(request.name());
        pet.setBirthDate(request.birthDate());
        pet.setType(findPetType(request.typeId()));
        petService.savePet(pet);
    }

    @DeleteMapping("/owners/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a pet")
    @ApiResponse(responseCode = "204", description = "Pet deleted")
    public void deletePet(@PathVariable int ownerId, @PathVariable int petId) {
        Pet pet = petService.findPetById(petId);
        validatePetOwner(pet, ownerId);
        petService.deletePet(petId);
    }

    private PetType findPetType(int typeId) {
        return petService.findPetTypes().stream()
            .filter(pt -> pt.getId() == typeId)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("PetType", typeId));
    }

    private void validatePetOwner(Pet pet, int ownerId) {
        if (pet.getOwner() == null || pet.getOwner().getId() != ownerId) {
            throw new ResourceNotFoundException("Pet", pet.getId());
        }
    }
}
