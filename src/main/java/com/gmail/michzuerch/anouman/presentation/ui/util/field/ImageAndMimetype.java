package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import java.io.Serializable;

public class ImageAndMimetype implements Serializable {
    private byte[] image;
    private String mimetype;

    public ImageAndMimetype() {
    }

    public ImageAndMimetype(byte[] image, String mimetype) {
        this.image = image;
        this.mimetype = mimetype;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
}
