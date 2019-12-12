package com.gmail.michzuerch.anouman.backend.data.entity.report;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportCSS extends AbstractEntity {
    @NotNull
    private String bezeichnung;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] css;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] html;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] javascript;

    @OneToMany(mappedBy = "reportCSS", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportCSSImage> reportCSSImages = new ArrayList<>();

}