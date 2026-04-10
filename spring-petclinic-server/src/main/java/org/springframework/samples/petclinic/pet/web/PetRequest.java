package org.springframework.samples.petclinic.pet.web;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Size;

import java.util.Date;

public class PetRequest {
    int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date birthDate;
    @Size(min = 1)
    String name;
    int typeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
