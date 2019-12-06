package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "ArticlePicture")
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

    private ArticlePicture(Builder builder) {
        setTitel(builder.titel);
        setMimetype(builder.mimetype);
        setImage(builder.image);
        setArticle(builder.article);
    }

    public ArticlePicture() {
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public static final class Builder {
        private @NotNull @Size(min = 3) String titel;
        private String mimetype;
        private @NotNull byte[] image;
        private Article article;

        public Builder() {
        }

        public Builder titel(@NotNull @Size(min = 3) String val) {
            titel = val;
            return this;
        }

        public Builder mimetype(String val) {
            mimetype = val;
            return this;
        }

        public Builder image(@NotNull byte[] val) {
            image = val;
            return this;
        }

        public Builder article(Article val) {
            article = val;
            return this;
        }

        public ArticlePicture build() {
            return new ArticlePicture(this);
        }
    }
}
