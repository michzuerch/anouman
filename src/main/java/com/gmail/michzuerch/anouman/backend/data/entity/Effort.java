
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity(name = "Effort") 
@Data
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
