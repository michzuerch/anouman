package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "ArticleCategory")
public class ArticleCategory extends AbstractEntity {
    @NotNull
    private String description;

    @OneToMany(mappedBy = "articleCategory", cascade = CascadeType.ALL)
    private List<Article> articles;

    private ArticleCategory(Builder builder) {
        setDescription(builder.description);
        setArticles(builder.articles);
    }

    public ArticleCategory() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public static final class Builder {
        private @NotNull String description;
        private List<Article> articles;

        public Builder() {
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder articles(List<Article> val) {
            articles = val;
            return this;
        }

        public ArticleCategory build() {
            return new ArticleCategory(this);
        }
    }
}
