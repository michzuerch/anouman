package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "ArticlePicture")
@Data
@Builder
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
