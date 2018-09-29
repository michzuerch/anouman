package com.gmail.michzuerch.anouman.backend.jpa.service.report.jasper;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.jasper.ReportJasperImage;
import com.gmail.michzuerch.anouman.backend.jpa.repository.report.jasper.ReportJasperImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportJasperImageService {

    @Autowired
    ReportJasperImageRepository repository;

    public ReportJasperImage add(ReportJasperImage val) {
        return repository.save(val);
    }
}