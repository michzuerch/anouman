package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Rechnung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RechnungRepository extends JpaRepository<Rechnung, Long> {
}
