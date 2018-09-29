package com.gmail.michzuerch.anouman.backend.jpa.service.report.fop;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.fop.ReportFOP;
import com.gmail.michzuerch.anouman.backend.jpa.repository.report.fop.ReportFOPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportFOPService {

    @Autowired
    ReportFOPRepository repository;

    public ReportFOP add(ReportFOP val) {
        return repository.save(val);
    }
}