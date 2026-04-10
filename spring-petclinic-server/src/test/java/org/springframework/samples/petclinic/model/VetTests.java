package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VetTests {

    @Test
    public void shouldAddSpecialty() {
        Vet vet = new Vet();
        Specialty specialty = new Specialty();
        specialty.setName("surgery");

        vet.addSpecialty(specialty);

        assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
        assertThat(vet.getSpecialties()).contains(specialty);
    }

    @Test
    public void shouldReturnSpecialtiesSortedByName() {
        Vet vet = new Vet();

        Specialty surgery = new Specialty();
        surgery.setName("surgery");
        vet.addSpecialty(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setName("dentistry");
        vet.addSpecialty(dentistry);

        Specialty radiology = new Specialty();
        radiology.setName("radiology");
        vet.addSpecialty(radiology);

        assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
        assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("radiology");
        assertThat(vet.getSpecialties().get(2).getName()).isEqualTo("surgery");
    }

    @Test
    public void shouldReturnZeroSpecialtiesForNewVet() {
        Vet vet = new Vet();

        assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
    }
}
