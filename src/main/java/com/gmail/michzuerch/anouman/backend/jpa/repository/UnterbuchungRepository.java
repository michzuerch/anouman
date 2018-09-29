package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Unterbuchung;
import org.springframework.data.repository.CrudRepository;

public interface UnterbuchungRepository extends CrudRepository<Unterbuchung, Long> {
}
