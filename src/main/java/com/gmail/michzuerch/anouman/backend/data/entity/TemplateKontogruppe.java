package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateKontogruppe")
@Data
@Builder
public class TemplateKontogruppe extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Address address;


}
