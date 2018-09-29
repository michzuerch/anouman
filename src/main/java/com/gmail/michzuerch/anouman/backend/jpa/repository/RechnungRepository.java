package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Rechnung;
import org.springframework.data.repository.CrudRepository;

public interface RechnungRepository extends CrudRepository<Rechnung, Long> {
}
