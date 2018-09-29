package com.gmail.michzuerch.anouman.backend.jpa.service.report.css;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSSImage;
import com.gmail.michzuerch.anouman.backend.jpa.repository.report.css.ReportCSSImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportCSSImageService {

    @Autowired
    ReportCSSImageRepository repository;

    public ReportCSSImage add(ReportCSSImage val) {
        return repository.save(val);
    }
}