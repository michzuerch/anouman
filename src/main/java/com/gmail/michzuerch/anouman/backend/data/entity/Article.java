package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "Article")
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

    private Article(Builder builder) {
        setDescription(builder.description);
        setDescriptionLong(builder.descriptionLong);
        setQuantityUnit(builder.quantityUnit);
        setStueckpreis(builder.stueckpreis);
        setArticlePictures(builder.articlePictures);
        setArticleCategory(builder.articleCategory);
    }

    public Article() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public Double getStueckpreis() {
        return stueckpreis;
    }

    public void setStueckpreis(Double stueckpreis) {
        this.stueckpreis = stueckpreis;
    }

    public List<ArticlePicture> getArticlePictures() {
        return articlePictures;
    }

    public void setArticlePictures(List<ArticlePicture> articlePictures) {
        this.articlePictures = articlePictures;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    public static final class Builder {
        private @NotNull String description;
        private @NotNull String descriptionLong;
        private String quantityUnit;
        private @Digits(integer = 8, fraction = 2, message = "Muss ein gültiger Betrag sein") Double stueckpreis;
        private List<ArticlePicture> articlePictures;
        private ArticleCategory articleCategory;

        public Builder() {
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder descriptionLong(@NotNull String val) {
            descriptionLong = val;
            return this;
        }

        public Builder quantityUnit(String val) {
            quantityUnit = val;
            return this;
        }

        public Builder stueckpreis(@Digits(integer = 8, fraction = 2, message = "Muss ein gültiger Betrag sein") Double val) {
            stueckpreis = val;
            return this;
        }

        public Builder articlePictures(List<ArticlePicture> val) {
            articlePictures = val;
            return this;
        }

        public Builder articleCategory(ArticleCategory val) {
            articleCategory = val;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
