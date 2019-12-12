package com.gmail.michzuerch.anouman.backend.data.entity.report;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportCSS extends AbstractEntity {
    private String bezeichnung;

    @ManyToOne
    private ReportCSS reportCSS;

    private String mimeType;

    @Basic(fetch = FetchType.LAZY)
    private byte[] image;
}