package org.springframework.samples.petclinic.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.customer.repository.OwnerRepository;
import org.springframework.samples.petclinic.shared.web.error.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) {
        return ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Owner> findAll(Pageable pageable) {
        return ownerRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void deleteOwner(int id) {
        ownerRepository.deleteById(id);
    }
}
