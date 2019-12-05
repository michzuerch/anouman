package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "Article")
@NamedEntityGraphs({@NamedEntityGraph(name = "ArticleHasArticlePictures", attributeNodes = {
        @NamedAttributeNode("articlePictures")})})
@Data
@Builder
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
