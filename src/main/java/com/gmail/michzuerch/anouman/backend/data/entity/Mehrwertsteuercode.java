package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "Mehrwertsteuercode")
@Data
@Builder
public class Mehrwertsteuercode extends AbstractEntity {
    @ManyToOne
    private Bookkeeping bookkeeping;
}
