package com.gmail.michzuerch.anouman.presentation.streamsource;

import com.vaadin.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ArtikelbildStreamSource implements StreamResource.StreamSource {
    private byte[] image = new byte[0];

    public ArtikelbildStreamSource(byte[] image) {
        this.image = image;
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(image);
    }
}
