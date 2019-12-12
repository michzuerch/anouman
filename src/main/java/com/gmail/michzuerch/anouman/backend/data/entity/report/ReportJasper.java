package com.gmail.michzuerch.anouman.backend.data.entity.report;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportCSS extends AbstractEntity {
    @NotNull
    @Size(min = 1, max = 50)
    private String bezeichnung;

    private String filename;

    @Basic(fetch = FetchType.LAZY)
    private byte[] templateSource;

    @Basic(fetch = FetchType.LAZY)
    private byte[] templateCompiled;

    @OneToMany(mappedBy = "reportJasper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.gmail.michzuerch.anouman.backend.jpa.domain.report.jasper.ReportJasperImage> reportJasperImages = new ArrayList<>();

}