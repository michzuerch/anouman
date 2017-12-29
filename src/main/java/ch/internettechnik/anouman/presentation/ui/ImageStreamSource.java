package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.server.StreamResource;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageStreamSource implements StreamResource.StreamSource {
    private ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();

    public ImageStreamSource(byte[] val) {
        try {
            if (val == null) {
                imagebuffer.write(getEmptyImage());
            } else {
                imagebuffer.write(val);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getEmptyImage() {
        try {
            byte[] val = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("/images/EmptyImage.jpg"));
            return val;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(
                imagebuffer.toByteArray());
    }

}


