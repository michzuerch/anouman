package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateKontoklasse;
import com.gmail.michzuerch.anouman.backend.jpa.repository.TemplateKontoklasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateKontoklasseService {

    @Autowired
    TemplateKontoklasseRepository repository;

    public TemplateKontoklasse add(TemplateKontoklasse val) {
        return repository.save(val);
    }
}