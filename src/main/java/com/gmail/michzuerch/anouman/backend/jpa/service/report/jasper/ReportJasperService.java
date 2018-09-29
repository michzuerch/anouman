package com.gmail.michzuerch.anouman.backend.jpa.service.report.jasper;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.jasper.ReportJasper;
import com.gmail.michzuerch.anouman.backend.jpa.repository.report.jasper.ReportJasperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportJasperService {

    @Autowired
    ReportJasperRepository repository;

    public ReportJasper add(ReportJasper val) {
        return repository.save(val);
    }
}