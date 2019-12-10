package com.gmail.michzuerch.anouman.backend.repositories;

import com.gmail.michzuerch.anouman.backend.data.entity.InvoicePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicePositionRepository extends JpaRepository<InvoicePosition, Long> {
}
