package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerTests {

    @Test
    public void shouldAddPetAndSetOwnerBackReference() {
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("Buddy");

        owner.addPet(pet);

        assertThat(pet.getOwner()).isEqualTo(owner);
        assertThat(owner.getPets()).contains(pet);
    }

    @Test
    public void shouldReturnPetsSortedByName() {
        Owner owner = new Owner();

        Pet zorro = new Pet();
        zorro.setName("Zorro");
        owner.addPet(zorro);

        Pet alpha = new Pet();
        alpha.setName("Alpha");
        owner.addPet(alpha);

        Pet middle = new Pet();
        middle.setName("Middle");
        owner.addPet(middle);

        assertThat(owner.getPets().get(0).getName()).isEqualTo("Alpha");
        assertThat(owner.getPets().get(1).getName()).isEqualTo("Middle");
        assertThat(owner.getPets().get(2).getName()).isEqualTo("Zorro");
    }

    @Test
    public void shouldFindPetByName() {
        Owner owner = new Owner();
        Pet basil = new Pet();
        basil.setName("Basil");
        owner.addPet(basil);

        Pet found = owner.getPet("Basil");

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Basil");
    }

    @Test
    public void shouldFindPetByNameCaseInsensitive() {
        Owner owner = new Owner();
        Pet basil = new Pet();
        basil.setName("Basil");
        owner.addPet(basil);

        Pet found = owner.getPet("basil");

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Basil");
    }

    @Test
    public void shouldReturnNullForUnknownPetName() {
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("Buddy");
        owner.addPet(pet);

        Pet found = owner.getPet("Unknown");

        assertThat(found).isNull();
    }
}
