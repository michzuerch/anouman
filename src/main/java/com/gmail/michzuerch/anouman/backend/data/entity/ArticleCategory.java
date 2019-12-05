package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "ArticleCategory")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "ArticleCategoryHasArticles", attributeNodes = {@NamedAttributeNode("articles")})})
@Data
public class ArticleCategory extends AbstractEntity {
    @NotNull
    private String description;

    @OneToMany(mappedBy = "articleCategory", cascade = CascadeType.ALL)
    private List<Article> articles;

}
