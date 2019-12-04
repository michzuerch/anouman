
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity(name = "ArticlePicture")
@Data
public class ArticlePicture extends AbstractEntity {
    @NotNull
    @Size(min = 3)
    private String titel;

    private String mimetype;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToOne
    private Article article;

}
