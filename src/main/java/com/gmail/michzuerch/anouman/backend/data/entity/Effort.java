package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity(name = "Effort")
@Data
@Builder
public class Effort extends AbstractEntity {
    @NotNull
    @Size(min = 3)
    private String title;

    @NotNull
    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean resizeable;

    private boolean movable;

    @ManyToOne
    private Invoice invoice;
}
