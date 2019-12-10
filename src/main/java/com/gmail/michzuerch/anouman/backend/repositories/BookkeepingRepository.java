package com.gmail.michzuerch.anouman.backend.repositories;

import com.gmail.michzuerch.anouman.backend.data.entity.Bookkeeping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookkeepingRepository extends JpaRepository<Bookkeeping, Long> {
}
