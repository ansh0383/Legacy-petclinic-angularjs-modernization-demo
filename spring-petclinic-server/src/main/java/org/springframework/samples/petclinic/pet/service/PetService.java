package org.springframework.samples.petclinic.pet.service;

import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;
import java.util.Collection;

public interface PetService {
    Pet findPetById(int id);
    void savePet(Pet pet);
    void deletePet(int id);
    Collection<PetType> findPetTypes();
}
