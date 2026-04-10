package org.springframework.samples.petclinic.pet.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;

import java.util.Date;

public class PetDetails {

    final int id;
    final String name;
    final String owner;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    final Date birthDate;
    final PetType type;

    public PetDetails(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.owner = pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName();
        this.birthDate = pet.getBirthDate();
        this.type = pet.getType();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public PetType getType() {
        return type;
    }
}
