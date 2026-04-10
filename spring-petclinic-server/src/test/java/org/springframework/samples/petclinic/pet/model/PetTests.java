package org.springframework.samples.petclinic.pet.model;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.visit.model.Visit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PetTests {

    @Test
    public void shouldAddVisitAndSetPetBackReference() {
        Pet pet = new Pet();
        Visit visit = new Visit();
        visit.setDescription("checkup");

        pet.addVisit(visit);

        assertThat(visit.getPet()).isEqualTo(pet);
        assertThat(pet.getVisits()).contains(visit);
    }

    @Test
    public void shouldReturnUnmodifiableVisitsList() {
        Pet pet = new Pet();
        Visit visit = new Visit();
        visit.setDescription("checkup");
        pet.addVisit(visit);

        assertThatThrownBy(() -> pet.getVisits().add(new Visit()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void shouldHandleNullVisitsSet() {
        Pet pet = new Pet();

        assertThat(pet.getVisits()).isNotNull();
        assertThat(pet.getVisits()).isEmpty();
    }
}
