package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Kontoklasse;
import com.gmail.michzuerch.anouman.backend.jpa.repository.KontoklasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KontoklasseService {

    @Autowired
    KontoklasseRepository repository;

    public Kontoklasse add(Kontoklasse val) {
        return repository.save(val);
    }
}