package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Adresse;
import com.gmail.michzuerch.anouman.backend.jpa.repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdresseService {

    @Autowired
    AdresseRepository repository;

    public Adresse add(Adresse val) {
        return repository.save(val);
    }
}