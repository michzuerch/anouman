package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Aufwand;
import com.gmail.michzuerch.anouman.backend.jpa.repository.AufwandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AufwandService {

    @Autowired
    AufwandRepository repository;

    public Aufwand add(Aufwand val) {
        return repository.save(val);
    }
}