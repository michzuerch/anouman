package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Buchung;
import org.springframework.data.repository.CrudRepository;

public interface BuchungRepository extends CrudRepository<Buchung, Long> {
}
