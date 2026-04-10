package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClinicServiceImplTests {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private VetRepository vetRepository;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private ClinicServiceImpl clinicService;

    @Test
    public void shouldFindOwnerById() {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("George");
        owner.setLastName("Franklin");

        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));

        Owner found = clinicService.findOwnerById(1);

        assertThat(found).isEqualTo(owner);
    }

    @Test
    public void shouldThrowWhenOwnerNotFound() {
        given(ownerRepository.findById(999)).willReturn(Optional.empty());

        assertThatThrownBy(() -> clinicService.findOwnerById(999))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void shouldFindAllOwners() {
        Owner owner1 = new Owner();
        owner1.setId(1);
        Owner owner2 = new Owner();
        owner2.setId(2);

        given(ownerRepository.findAll()).willReturn(Arrays.asList(owner1, owner2));

        Collection<Owner> owners = clinicService.findAll();

        assertThat(owners).hasSize(2);
    }

    @Test
    public void shouldSaveOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Sam");
        owner.setLastName("Schultz");

        clinicService.saveOwner(owner);

        verify(ownerRepository).save(owner);
    }

    @Test
    public void shouldFindPetById() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Leo");

        given(petRepository.findById(1)).willReturn(pet);

        Pet found = clinicService.findPetById(1);

        assertThat(found).isEqualTo(pet);
    }

    @Test
    public void shouldSavePet() {
        Pet pet = new Pet();
        pet.setName("Leo");

        clinicService.savePet(pet);

        verify(petRepository).save(pet);
    }

    @Test
    public void shouldFindPetTypes() {
        PetType cat = new PetType();
        cat.setId(1);
        cat.setName("cat");
        PetType dog = new PetType();
        dog.setId(2);
        dog.setName("dog");

        given(petRepository.findPetTypes()).willReturn(Arrays.asList(cat, dog));

        Collection<PetType> petTypes = clinicService.findPetTypes();

        assertThat(petTypes).hasSize(2);
    }

    @Test
    public void shouldFindVets() {
        Vet vet1 = new Vet();
        vet1.setId(1);
        Vet vet2 = new Vet();
        vet2.setId(2);

        given(vetRepository.findAll()).willReturn(Arrays.asList(vet1, vet2));

        Collection<Vet> vets = clinicService.findVets();

        assertThat(vets).hasSize(2);
    }

    @Test
    public void shouldSaveVisit() {
        Visit visit = new Visit();
        visit.setDescription("checkup");

        clinicService.saveVisit(visit);

        verify(visitRepository).save(visit);
    }
}
