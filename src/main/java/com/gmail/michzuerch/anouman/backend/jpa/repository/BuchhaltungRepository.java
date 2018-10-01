package com.gmail.michzuerch.anouman.backend.jpa.repository;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Buchhaltung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuchhaltungRepository extends JpaRepository<Buchhaltung, Long> {
}
