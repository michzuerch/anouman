
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "Article")
@NamedEntityGraphs({ @NamedEntityGraph(name = "ArticleHasArticlePictures", attributeNodes = {
        @NamedAttributeNode("articlePictures") }) })
@Data
public class Article extends AbstractEntity {
    @NotNull
    private String description;

    @NotNull
    private String descriptionLong;

    private String quantityUnit;

    @Digits(integer = 8, fraction = 2, message = "Muss ein gültiger Betrag sein")
    private Double stueckpreis;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticlePicture> articlePictures;

    @ManyToOne
    private ArticleCategory articleCategory;
}
