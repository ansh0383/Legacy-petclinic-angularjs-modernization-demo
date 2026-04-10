package org.springframework.samples.petclinic.vet.service;

import org.springframework.samples.petclinic.vet.model.Vet;
import java.util.Collection;

public interface VetService {
    Collection<Vet> findVets();
    Vet findVetById(int id);
}
