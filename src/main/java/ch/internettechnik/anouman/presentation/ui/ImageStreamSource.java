package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageStreamSource implements StreamResource.StreamSource {
    private ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();

    public ImageStreamSource(byte[] val) {
        try {
            imagebuffer.write(val);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(
                imagebuffer.toByteArray());
    }

}


