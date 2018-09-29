package com.gmail.michzuerch.anouman.backend.jpa.service.report.css;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSS;
import com.gmail.michzuerch.anouman.backend.jpa.repository.report.css.ReportCSSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportCSSService {

    @Autowired
    ReportCSSRepository repository;

    public ReportCSS add(ReportCSS val) {
        return repository.save(val);
    }
}