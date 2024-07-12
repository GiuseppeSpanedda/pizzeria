package com.pizzeria.service;

import com.pizzeria.entity.Ordine;
import com.pizzeria.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrdineServiceImpl implements OrdineService {
    private final OrdineRepository ordineRepository;

    @Autowired
    public OrdineServiceImpl(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    @Override
    public List<Ordine> getAll() {
        return ordineRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Ordine> getAllWithPizze() {
        return ordineRepository.findAllWithPizze();
    }

    @Override
    public Optional<Ordine> findById(Long id) {
        return ordineRepository.findById(id);
    }

    @Override
    public Ordine create(Ordine ordine) {
        return ordineRepository.save(ordine);
    }

    @Override
    public Ordine update(Long id, Ordine updatedOrdine) {
        if (ordineRepository.existsById(id)) {
            updatedOrdine.setId(id);
            return ordineRepository.save(updatedOrdine);
        }
        return null;
    }

    @Override
    public Optional<Ordine> delete(Long id) {
        Optional<Ordine> ordineOpt = ordineRepository.findById(id);
        if (ordineOpt.isPresent()) {
            Ordine ordine = ordineOpt.get();

            ordineRepository.delete(ordine);
        }
        return ordineOpt;
    }
}
