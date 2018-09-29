package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Buchung;
import com.gmail.michzuerch.anouman.backend.jpa.repository.BuchungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuchungService {

    @Autowired
    BuchungRepository repository;

    public Buchung add(Buchung val) {
        return repository.save(val);
    }
}