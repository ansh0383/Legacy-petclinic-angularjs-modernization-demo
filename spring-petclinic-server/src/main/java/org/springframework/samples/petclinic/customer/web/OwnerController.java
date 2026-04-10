package org.springframework.samples.petclinic.customer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.customer.service.OwnerService;
import org.springframework.samples.petclinic.shared.dto.OwnerCreateRequest;
import org.springframework.samples.petclinic.shared.dto.OwnerDto;
import org.springframework.samples.petclinic.shared.dto.mapper.OwnerMapper;
import org.springframework.samples.petclinic.shared.web.AbstractResourceController;
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

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Owner", description = "Owner management APIs")
public class OwnerController extends AbstractResourceController {

    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    public OwnerController(OwnerService ownerService, OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    @GetMapping("/owners")
    @Operation(summary = "List all owners (paginated)")
    @ApiResponse(responseCode = "200", description = "Paginated list of owners")
    public Page<OwnerDto> findAll(Pageable pageable) {
        return ownerService.findAll(pageable).map(ownerMapper::toDto);
    }

    @GetMapping("/owners/{ownerId}")
    @Operation(summary = "Find owner by ID")
    @ApiResponse(responseCode = "200", description = "Owner found")
    @ApiResponse(responseCode = "404", description = "Owner not found")
    public OwnerDto findOwner(@PathVariable int ownerId) {
        return ownerMapper.toDto(ownerService.findOwnerById(ownerId));
    }

    @PostMapping("/owners")
    @Operation(summary = "Create a new owner")
    @ApiResponse(responseCode = "201", description = "Owner created")
    public ResponseEntity<OwnerDto> createOwner(@Valid @RequestBody OwnerCreateRequest request) {
        Owner owner = ownerMapper.toEntity(request);
        ownerService.saveOwner(owner);
        OwnerDto dto = ownerMapper.toDto(owner);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(owner.getId()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/owners/{ownerId}")
    @Operation(summary = "Update an existing owner")
    @ApiResponse(responseCode = "200", description = "Owner updated")
    @ApiResponse(responseCode = "404", description = "Owner not found")
    public OwnerDto updateOwner(@PathVariable int ownerId, @Valid @RequestBody OwnerCreateRequest request) {
        Owner owner = ownerService.findOwnerById(ownerId);
        ownerMapper.updateEntity(owner, request);
        ownerService.saveOwner(owner);
        return ownerMapper.toDto(owner);
    }

    @DeleteMapping("/owners/{ownerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an owner")
    @ApiResponse(responseCode = "204", description = "Owner deleted")
    public void deleteOwner(@PathVariable int ownerId) {
        ownerService.findOwnerById(ownerId);
        ownerService.deleteOwner(ownerId);
    }
}
