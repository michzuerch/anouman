package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Konto;
import com.gmail.michzuerch.anouman.backend.jpa.repository.KontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KontoService {

    @Autowired
    KontoRepository repository;

    public Konto add(Konto val) {
        return repository.save(val);
    }
}