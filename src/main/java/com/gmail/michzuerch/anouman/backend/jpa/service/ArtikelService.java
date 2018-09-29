package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Artikel;
import com.gmail.michzuerch.anouman.backend.jpa.repository.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtikelService {

    @Autowired
    ArtikelRepository repository;

    public Artikel add(Artikel val) {
        return repository.save(val);
    }
}