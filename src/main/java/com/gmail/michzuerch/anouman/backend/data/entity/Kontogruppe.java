package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "Kontogruppe")
@Data
public class Kontogruppe extends AbstractEntity {
    @ManyToOne
    private Address address;
}
