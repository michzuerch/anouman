package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateKontohauptgruppe;
import com.gmail.michzuerch.anouman.backend.jpa.repository.TemplateKontohauptgruppeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateKontohauptgruppeService {

    @Autowired
    TemplateKontohauptgruppeRepository repository;

    public TemplateKontohauptgruppe add(TemplateKontohauptgruppe val) {
        return repository.save(val);
    }
}