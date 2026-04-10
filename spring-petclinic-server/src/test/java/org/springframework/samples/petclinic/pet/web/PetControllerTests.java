package org.springframework.samples.petclinic.pet.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.customer.service.OwnerService;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;
import org.springframework.samples.petclinic.pet.service.PetService;
import org.springframework.samples.petclinic.shared.dto.mapper.PetMapper;
import org.springframework.samples.petclinic.shared.dto.mapper.VisitMapper;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
@Import({PetMapper.class, VisitMapper.class})
public class PetControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    PetService petService;

    @MockBean
    OwnerService ownerService;

    @Test
    public void shouldGetAPetInJSonFormat() throws Exception {

        Pet pet = setupPet();

        given(petService.findPetById(2)).willReturn(pet);


        mvc.perform(get("/api/v1/owners/2/pets/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Basil"))
                .andExpect(jsonPath("$.type.id").value(6));
    }

    @Test
    public void shouldGetPetTypes() throws Exception {
        PetType cat = new PetType();
        cat.setId(1);
        cat.setName("cat");
        PetType dog = new PetType();
        dog.setId(2);
        dog.setName("dog");

        given(petService.findPetTypes()).willReturn(Arrays.asList(cat, dog));

        mvc.perform(get("/api/v1/pet-types").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("cat"))
                .andExpect(jsonPath("$[1].name").value("dog"));
    }

    @Test
    public void shouldCreatePet() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("George");
        owner.setLastName("Bush");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");

        PetType petType = new PetType();
        petType.setId(6);
        petType.setName("hamster");

        given(ownerService.findOwnerById(1)).willReturn(owner);
        given(petService.findPetTypes()).willReturn(Arrays.asList(petType));

        doAnswer(invocation -> {
            Pet pet = invocation.getArgument(0);
            pet.setId(99);
            return null;
        }).when(petService).savePet(any(Pet.class));

        mvc.perform(post("/api/v1/owners/1/pets")
                        .content("{\"name\":\"Leo\",\"birthDate\":\"2020-01-01\",\"typeId\":6}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(petService).savePet(any(Pet.class));
    }

    @Test
    public void shouldUpdatePet() throws Exception {
        Pet existingPet = setupPet();

        PetType petType = new PetType();
        petType.setId(6);
        petType.setName("hamster");

        given(petService.findPetById(2)).willReturn(existingPet);
        given(petService.findPetTypes()).willReturn(Arrays.asList(petType));

        mvc.perform(put("/api/v1/owners/1/pets/2")
                        .content("{\"name\":\"LeoUpdated\",\"birthDate\":\"2020-01-01\",\"typeId\":6}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Pet setupPet() {
        Owner owner = new Owner();
        owner.setFirstName("George");
        owner.setLastName("Bush");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");

        Pet pet = new Pet();

        pet.setName("Basil");
        pet.setId(2);

        PetType petType = new PetType();
        petType.setId(6);
        pet.setType(petType);

        owner.addPet(pet);
        return pet;
    }
}
