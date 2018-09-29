package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Buchhaltung;
import org.springframework.data.repository.CrudRepository;

public interface BuchhaltungRepository extends CrudRepository<Buchhaltung, Long> {
}
