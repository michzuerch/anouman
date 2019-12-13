package com.gmail.michzuerch.anouman.backend.repositories;

import com.gmail.michzuerch.anouman.backend.data.entity.Unterbuchung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnterbuchungRepository extends JpaRepository<Unterbuchung, Long> {
}
