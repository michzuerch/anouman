package ch.internettechnik.anouman.presentation.ui.stream;

import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildFacade;
import com.vaadin.server.StreamResource;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ArtikelBildStream implements StreamResource.StreamSource {
    @Inject
    ArtikelbildFacade artikelbildFacade;

    ByteArrayOutputStream imagebuffer = null;

    public ArtikelBildStream() {
    }

    @Override
    public InputStream getStream() {
        try {
            // Write the image to a buffer
            imagebuffer = new ByteArrayOutputStream();
            imagebuffer.write(artikelbildFacade.findAll().get(0).getBild());
            //ImageIO.write(artikelbildFacade.findAll().get(0).getBild().clone(), "png", imagebuffer);

            // Return a stream from the buffer
            return new ByteArrayInputStream(
                    imagebuffer.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    public InputStream getStream(Long id) {
        try {
            // Write the image to a buffer
            imagebuffer = new ByteArrayOutputStream();
            imagebuffer.write(artikelbildFacade.findBy(id).getBild());
            //ImageIO.write(artikelbildFacade.findAll().get(0).getBild().clone(), "png", imagebuffer);

            // Return a stream from the buffer
            return new ByteArrayInputStream(
                    imagebuffer.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
