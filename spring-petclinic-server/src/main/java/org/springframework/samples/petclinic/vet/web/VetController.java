package org.springframework.samples.petclinic.vet.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.samples.petclinic.shared.dto.VetDto;
import org.springframework.samples.petclinic.shared.dto.mapper.VetMapper;
import org.springframework.samples.petclinic.shared.web.AbstractResourceController;
import org.springframework.samples.petclinic.vet.service.VetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Vet", description = "Veterinarian APIs")
public class VetController extends AbstractResourceController {

    private final VetService vetService;
    private final VetMapper vetMapper;

    public VetController(VetService vetService, VetMapper vetMapper) {
        this.vetService = vetService;
        this.vetMapper = vetMapper;
    }

    @GetMapping("/vets")
    @Operation(summary = "List all veterinarians")
    @ApiResponse(responseCode = "200", description = "List of veterinarians")
    public List<VetDto> listVets() {
        return vetService.findVets().stream()
            .map(vetMapper::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/vets/{vetId}")
    @Operation(summary = "Find a veterinarian by ID")
    @ApiResponse(responseCode = "200", description = "Vet found")
    @ApiResponse(responseCode = "404", description = "Vet not found")
    public VetDto findVet(@PathVariable int vetId) {
        return vetMapper.toDto(vetService.findVetById(vetId));
    }
}
