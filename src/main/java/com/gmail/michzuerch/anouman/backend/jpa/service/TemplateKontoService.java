package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateKonto;
import com.gmail.michzuerch.anouman.backend.jpa.repository.TemplateKontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateKontoService {

    @Autowired
    TemplateKontoRepository repository;

    public TemplateKonto add(TemplateKonto val) {
        return repository.save(val);
    }
}