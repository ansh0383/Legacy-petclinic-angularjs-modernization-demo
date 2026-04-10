package org.springframework.samples.petclinic.customer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.customer.repository.OwnerRepository;

import org.springframework.samples.petclinic.shared.web.error.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceImplTests {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    public void shouldFindOwnerById() {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("George");
        owner.setLastName("Franklin");

        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));

        Owner found = ownerService.findOwnerById(1);

        assertThat(found).isEqualTo(owner);
    }

    @Test
    public void shouldThrowWhenOwnerNotFound() {
        given(ownerRepository.findById(999)).willReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.findOwnerById(999))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void shouldFindAllOwners() {
        Owner owner1 = new Owner();
        owner1.setId(1);
        Owner owner2 = new Owner();
        owner2.setId(2);

        given(ownerRepository.findAll()).willReturn(Arrays.asList(owner1, owner2));

        Collection<Owner> owners = ownerService.findAll();

        assertThat(owners).hasSize(2);
    }

    @Test
    public void shouldSaveOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Sam");
        owner.setLastName("Schultz");

        ownerService.saveOwner(owner);

        verify(ownerRepository).save(owner);
    }
}
