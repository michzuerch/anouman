
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "ArticleCategory")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "ArticleCategoryHasArticles", attributeNodes = { @NamedAttributeNode("articles") }) })
@Data
public class ArticleCategory extends AbstractEntity {
    @NotNull
    private String description;

    @OneToMany(mappedBy = "articleCategory", cascade = CascadeType.ALL)
    private List<Article> articles;

}
