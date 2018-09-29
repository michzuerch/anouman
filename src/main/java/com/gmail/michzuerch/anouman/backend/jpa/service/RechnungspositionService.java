package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Rechnungsposition;
import com.gmail.michzuerch.anouman.backend.jpa.repository.RechnungspositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechnungspositionService {

    @Autowired
    RechnungspositionRepository repository;

    public Rechnungsposition add(Rechnungsposition val) {
        return repository.save(val);
    }
}