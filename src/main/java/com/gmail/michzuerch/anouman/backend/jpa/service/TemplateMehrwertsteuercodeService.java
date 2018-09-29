package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.TemplateMehrwertsteuercode;
import com.gmail.michzuerch.anouman.backend.jpa.repository.TemplateMehrwertsteuercodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateMehrwertsteuercodeService {

    @Autowired
    TemplateMehrwertsteuercodeRepository repository;

    public TemplateMehrwertsteuercode add(TemplateMehrwertsteuercode val) {
        return repository.save(val);
    }
}