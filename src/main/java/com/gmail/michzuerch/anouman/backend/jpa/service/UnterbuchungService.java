package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Unterbuchung;
import com.gmail.michzuerch.anouman.backend.jpa.repository.UnterbuchungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnterbuchungService {

    @Autowired
    UnterbuchungRepository repository;

    public Unterbuchung add(Unterbuchung val) {
        return repository.save(val);
    }
}