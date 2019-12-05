package com.gmail.michzuerch.anouman.backend.repositories;

import com.gmail.michzuerch.anouman.backend.data.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
