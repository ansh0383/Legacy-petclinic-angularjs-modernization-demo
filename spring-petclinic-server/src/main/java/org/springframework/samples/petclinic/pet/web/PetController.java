/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.customer.service.OwnerService;
import org.springframework.samples.petclinic.pet.model.Pet;
import org.springframework.samples.petclinic.pet.model.PetType;
import org.springframework.samples.petclinic.pet.service.PetService;
import org.springframework.samples.petclinic.shared.web.AbstractResourceController;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@RestController
public class PetController extends AbstractResourceController {

    private final PetService petService;
    private final OwnerService ownerService;

    @Autowired
    public PetController(PetService petService, OwnerService ownerService) {
        this.petService = petService;
        this.ownerService = ownerService;
    }

    @GetMapping("/petTypes")
    Collection<PetType> getPetTypes() {
        return petService.findPetTypes();
    }

    @PostMapping("/owners/{ownerId}/pets")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processCreationForm(
            @RequestBody PetRequest petRequest,
            @PathVariable("ownerId") int ownerId) {

        Pet pet = new Pet();
        Owner owner = this.ownerService.findOwnerById(ownerId);
        owner.addPet(pet);

        save(pet, petRequest);
    }

    @PutMapping("/owners/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processUpdateForm(@RequestBody PetRequest petRequest) {
        save(petService.findPetById(petRequest.getId()), petRequest);
    }

    private void save(Pet pet, PetRequest petRequest) {

        pet.setName(petRequest.getName());
        pet.setBirthDate(petRequest.getBirthDate());

        for (PetType petType : petService.findPetTypes()) {
            if (petType.getId() == petRequest.getTypeId()) {
                pet.setType(petType);
            }
        }

        petService.savePet(pet);
    }

    @GetMapping("/owners/*/pets/{petId}")
    public PetDetails findPet(@PathVariable("petId") int petId) {
        Pet pet = this.petService.findPetById(petId);
        return new PetDetails(pet);
    }

}
