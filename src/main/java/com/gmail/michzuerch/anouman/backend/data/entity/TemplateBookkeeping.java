package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateBookkeeping")
@Data
public class TemplateBookkeeping extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Address address;


}
