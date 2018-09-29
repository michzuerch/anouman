package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Artikelkategorie;
import com.gmail.michzuerch.anouman.backend.jpa.repository.ArtikelkategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtikelkategorieService {

    @Autowired
    ArtikelkategorieRepository repository;

    public Artikelkategorie add(Artikelkategorie val) {
        return repository.save(val);
    }
}