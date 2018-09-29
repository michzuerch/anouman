package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Kontohauptgruppe;
import com.gmail.michzuerch.anouman.backend.jpa.repository.KontohauptgruppeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KontohauptgruppeService {

    @Autowired
    KontohauptgruppeRepository repository;

    public Kontohauptgruppe add(Kontohauptgruppe val) {
        return repository.save(val);
    }
}