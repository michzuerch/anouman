package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Unterbuchung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnterbuchungRepository extends JpaRepository<Unterbuchung, Long> {
}
