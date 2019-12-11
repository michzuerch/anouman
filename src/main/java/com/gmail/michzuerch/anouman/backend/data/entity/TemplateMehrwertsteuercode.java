package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateMehrwertsteuercode")
public class TemplateMehrwertsteuercode extends AbstractEntity {
    @ManyToOne
    private TemplateBookkeeping templateBookkeeping;

}
