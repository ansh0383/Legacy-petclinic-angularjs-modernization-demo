package org.springframework.samples.petclinic.customer.service;

import org.springframework.samples.petclinic.customer.model.Owner;
import java.util.Collection;

public interface OwnerService {
    Owner findOwnerById(int id);
    Collection<Owner> findAll();
    void saveOwner(Owner owner);
}
