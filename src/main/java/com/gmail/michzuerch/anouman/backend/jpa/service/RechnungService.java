package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Rechnung;
import com.gmail.michzuerch.anouman.backend.jpa.repository.RechnungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechnungService {

    @Autowired
    RechnungRepository repository;

    public Rechnung add(Rechnung val) {
        return repository.save(val);
    }
}