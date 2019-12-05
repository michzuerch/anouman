package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "Kontogruppe")
@Data
@Builder
public class Kontogruppe extends AbstractEntity {
    @ManyToOne
    private Address address;
}
