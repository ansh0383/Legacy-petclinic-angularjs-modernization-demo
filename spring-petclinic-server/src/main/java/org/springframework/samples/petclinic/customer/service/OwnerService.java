package org.springframework.samples.petclinic.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.customer.model.Owner;
import java.util.Collection;

public interface OwnerService {
    Owner findOwnerById(int id);
    Collection<Owner> findAll();
    Page<Owner> findAll(Pageable pageable);
    void saveOwner(Owner owner);
    void deleteOwner(int id);
}
