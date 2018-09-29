package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.jpa.repository.TemplateBuchhaltungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateBuchhaltungService {

    @Autowired
    TemplateBuchhaltungRepository repository;

    public TemplateBuchhaltung add(TemplateBuchhaltung val) {
        return repository.save(val);
    }
}