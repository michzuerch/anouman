package com.gmail.michzuerch.anouman.backend.repositories;

import com.gmail.michzuerch.anouman.backend.data.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
}
