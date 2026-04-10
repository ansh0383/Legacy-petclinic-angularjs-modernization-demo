package org.springframework.samples.petclinic.visit.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.pet.service.PetService;
import org.springframework.samples.petclinic.shared.dto.VisitCreateRequest;
import org.springframework.samples.petclinic.shared.dto.VisitDto;
import org.springframework.samples.petclinic.shared.dto.mapper.VisitMapper;
import org.springframework.samples.petclinic.shared.web.AbstractResourceController;
import org.springframework.samples.petclinic.visit.model.Visit;
import org.springframework.samples.petclinic.visit.service.VisitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Visit", description = "Visit management APIs")
public class VisitController extends AbstractResourceController {

    private final VisitService visitService;
    private final PetService petService;
    private final VisitMapper visitMapper;

    public VisitController(VisitService visitService, PetService petService, VisitMapper visitMapper) {
        this.visitService = visitService;
        this.petService = petService;
        this.visitMapper = visitMapper;
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits")
    @Operation(summary = "List all visits for a pet")
    @ApiResponse(responseCode = "200", description = "List of visits")
    public List<VisitDto> listVisits(@PathVariable int petId) {
        return petService.findPetById(petId).getVisits().stream()
            .map(visitMapper::toDto)
            .collect(Collectors.toList());
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits")
    @Operation(summary = "Create a new visit for a pet")
    @ApiResponse(responseCode = "201", description = "Visit created")
    public ResponseEntity<VisitDto> createVisit(
            @PathVariable int petId,
            @Valid @RequestBody VisitCreateRequest request) {
        Visit visit = visitMapper.toEntity(request);
        petService.findPetById(petId).addVisit(visit);
        visitService.saveVisit(visit);
        VisitDto dto = visitMapper.toDto(visit);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(visit.getId()).toUri();
        return ResponseEntity.created(location).body(dto);
    }
}
