package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Kontogruppe;
import com.gmail.michzuerch.anouman.backend.jpa.repository.KontogruppeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KontogruppeService {

    @Autowired
    KontogruppeRepository repository;

    public Kontogruppe add(Kontogruppe val) {
        return repository.save(val);
    }
}