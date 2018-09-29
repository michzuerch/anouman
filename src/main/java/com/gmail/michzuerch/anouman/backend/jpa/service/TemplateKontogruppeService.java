package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateKontogruppe;
import com.gmail.michzuerch.anouman.backend.jpa.repository.TemplateKontogruppeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateKontogruppeService {

    @Autowired
    TemplateKontogruppeRepository repository;

    public TemplateKontogruppe add(TemplateKontogruppe val) {
        return repository.save(val);
    }
}