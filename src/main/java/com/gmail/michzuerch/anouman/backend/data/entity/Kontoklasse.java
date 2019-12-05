package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "Kontoklasse")
@Data
@Builder
public class Kontoklasse extends AbstractEntity {
    @ManyToOne
    private Bookkeeping bookkeeping;
}
