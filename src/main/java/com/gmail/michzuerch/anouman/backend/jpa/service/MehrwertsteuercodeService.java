package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Mehrwertsteuercode;
import com.gmail.michzuerch.anouman.backend.jpa.repository.MehrwertsteuercodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MehrwertsteuercodeService {

    @Autowired
    MehrwertsteuercodeRepository repository;

    public Mehrwertsteuercode add(Mehrwertsteuercode val) {
        return repository.save(val);
    }
}