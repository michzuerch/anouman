package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Konto;
import org.springframework.data.repository.CrudRepository;

public interface KontoRepository extends CrudRepository<Konto, Long> {
}
