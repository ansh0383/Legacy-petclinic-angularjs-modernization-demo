package org.springframework.samples.petclinic.visit.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.service.PetService;
import org.springframework.samples.petclinic.visit.model.Visit;
import org.springframework.samples.petclinic.visit.service.VisitService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
public class VisitControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    VisitService visitService;

    @MockBean
    PetService petService;

    @Test
    public void shouldCreateVisit() throws Exception {
        Pet pet = setupPetWithOwner();

        given(petService.findPetById(1)).willReturn(pet);

        mvc.perform(post("/owners/1/pets/1/visits")
                        .content("{\"date\":\"2023-01-01\",\"description\":\"checkup\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(visitService).saveVisit(any(Visit.class));
    }

    @Test
    public void shouldGetVisitsForPet() throws Exception {
        Pet pet = setupPetWithOwner();

        Visit visit1 = new Visit();
        visit1.setId(1);
        visit1.setDescription("checkup");
        pet.addVisit(visit1);

        Visit visit2 = new Visit();
        visit2.setId(2);
        visit2.setDescription("vaccination");
        pet.addVisit(visit2);

        given(petService.findPetById(1)).willReturn(pet);

        mvc.perform(get("/owners/1/pets/1/visits").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void shouldCreateVisitWithEmptyDescription() throws Exception {
        Pet pet = setupPetWithOwner();

        given(petService.findPetById(1)).willReturn(pet);

        mvc.perform(post("/owners/1/pets/1/visits")
                        .content("{\"date\":\"2023-01-01\",\"description\":\"\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Pet setupPetWithOwner() {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("George");
        owner.setLastName("Franklin");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Leo");

        owner.addPet(pet);
        return pet;
    }
}
