package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.UzerRole;
import com.gmail.michzuerch.anouman.backend.jpa.repository.UzerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UzerRoleService {

    @Autowired
    UzerRoleRepository repository;

    public UzerRole add(UzerRole val) {
        return repository.save(val);
    }
}