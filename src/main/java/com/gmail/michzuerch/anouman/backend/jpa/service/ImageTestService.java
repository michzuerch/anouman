package com.gmail.michzuerch.anouman.backend.jpa.service;

import com.gmail.michzuerch.anouman.backend.jpa.domain.ImageTest;
import com.gmail.michzuerch.anouman.backend.jpa.repository.ImageTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageTestService {

    @Autowired
    ImageTestRepository repository;

    public ImageTest add(ImageTest val) {
        return repository.save(val);
    }
}