package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Buchhaltung;
import com.gmail.michzuerch.anouman.backend.jpa.repository.BuchhaltungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuchhaltungService {

    @Autowired
    BuchhaltungRepository repository;

    public Buchhaltung add(Buchhaltung val) {
        return repository.save(val);
    }
}