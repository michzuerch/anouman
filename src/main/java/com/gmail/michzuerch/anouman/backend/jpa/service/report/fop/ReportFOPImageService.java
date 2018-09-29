package com.gmail.michzuerch.anouman.backend.jpa.service.report.fop;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.fop.ReportFOPImage;
import com.gmail.michzuerch.anouman.backend.jpa.repository.report.fop.ReportFOPImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportFOPImageService {

    @Autowired
    ReportFOPImageRepository repository;

    public ReportFOPImage add(ReportFOPImage val) {
        return repository.save(val);
    }
}