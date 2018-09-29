package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Uzer;
import com.gmail.michzuerch.anouman.backend.jpa.repository.UzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UzerService {

    @Autowired
    UzerRepository repository;

    public Uzer add(Uzer val) {
        return repository.save(val);
    }
}