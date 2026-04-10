package org.springframework.samples.petclinic.pet.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;
import org.springframework.samples.petclinic.pet.repository.PetRepository;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplTests {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @Test
    public void shouldFindPetById() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Leo");

        given(petRepository.findById(1)).willReturn(pet);

        Pet found = petService.findPetById(1);

        assertThat(found).isEqualTo(pet);
    }

    @Test
    public void shouldSavePet() {
        Pet pet = new Pet();
        pet.setName("Leo");

        petService.savePet(pet);

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

        Collection<PetType> petTypes = petService.findPetTypes();

        assertThat(petTypes).hasSize(2);
    }
}
