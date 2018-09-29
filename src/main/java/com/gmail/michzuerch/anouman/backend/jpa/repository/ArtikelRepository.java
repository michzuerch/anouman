package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Artikel;
import org.springframework.data.repository.CrudRepository;

public interface ArtikelRepository extends CrudRepository<Artikel, Long> {
}
