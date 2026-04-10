package org.springframework.samples.petclinic.visit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.service.PetService;
import org.springframework.samples.petclinic.visit.model.Visit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VisitServiceIntegrationTests {

    @Autowired
    private VisitService visitService;

    @Autowired
    private PetService petService;

    @Test
    @Transactional
    public void shouldAddNewVisitForPet() {
        Pet pet7 = this.petService.findPetById(7);
        int found = pet7.getVisits().size();
        Visit visit = new Visit();
        pet7.addVisit(visit);
        visit.setDescription("test");
        this.visitService.saveVisit(visit);
        this.petService.savePet(pet7);

        pet7 = this.petService.findPetById(7);
        assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
        assertThat(visit.getId()).isNotNull();
    }
}
