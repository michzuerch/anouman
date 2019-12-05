package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "ArticleCategory")
@Data
@Builder
public class ArticleCategory extends AbstractEntity {
    @NotNull
    private String description;

    @OneToMany(mappedBy = "articleCategory", cascade = CascadeType.ALL)
    private List<Article> articles;

}
