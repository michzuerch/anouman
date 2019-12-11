package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "TemplateKontogruppe")
public class TemplateKontogruppe extends AbstractEntity {
    @NotNull
    private String kontonummer;

    @NotNull
    private String description;

    @ManyToOne
    private TemplateKontoHauptgruppe templateKontoHauptgruppe;

    @OneToMany(mappedBy = "templateKontogruppe", cascade = CascadeType.ALL)
    private List<TemplateKonto> templateKontos;
}
