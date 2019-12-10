package com.gmail.michzuerch.anouman.backend.repositories;

import com.gmail.michzuerch.anouman.backend.data.entity.Effort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EffortRepository extends JpaRepository<Effort, Long> {
}
