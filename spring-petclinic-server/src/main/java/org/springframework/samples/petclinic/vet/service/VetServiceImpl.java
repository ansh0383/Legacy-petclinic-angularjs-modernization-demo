package org.springframework.samples.petclinic.vet.service;

import org.springframework.samples.petclinic.shared.web.error.ResourceNotFoundException;
import org.springframework.samples.petclinic.vet.model.Vet;
import org.springframework.samples.petclinic.vet.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.cache.annotation.CacheResult;
import java.util.Collection;

@Service
public class VetServiceImpl implements VetService {
    private final VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @CacheResult(cacheName = "vets")
    public Collection<Vet> findVets() {
        return vetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vet findVetById(int id) {
        return vetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vet", id));
    }
}
