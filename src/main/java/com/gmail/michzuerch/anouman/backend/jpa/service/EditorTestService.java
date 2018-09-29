package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.EditorTest;
import com.gmail.michzuerch.anouman.backend.jpa.repository.EditorTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorTestService {

    @Autowired
    EditorTestRepository repository;

    public EditorTest add(EditorTest val) {
        return repository.save(val);
    }
}