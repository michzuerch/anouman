package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildFacade;
import ch.internettechnik.anouman.presentation.ui.stream.ArtikelBildStream;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;
import server.droporchoose.UploadComponent;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ViewScoped
public class ArtikelbildForm extends AbstractForm<Artikelbild> {
    @Inject
    ArtikelFacade artikelFacade;

    @Inject
    ArtikelbildFacade artikelbildFacade;

    NativeSelect<Artikel> artikel = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    UploadComponent bild = new UploadComponent();
    Image image = new Image("Bild");

    StreamResource.StreamSource imagesource = new ArtikelBildStream();
    StreamResource resource =
            new StreamResource(imagesource, "Bild.png");

    public ArtikelbildForm() {
        super(Artikelbild.class);
    }

    public void lockSelect() {
        artikel.setEnabled(false);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Artikelbild");
        openInModalPopup.setWidth("500px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        artikel.setCaption("Artikel");
        artikel.setItemCaptionGenerator(artikel1 -> artikel1.getBezeichnung() + " id:" + artikel1.getId());
        artikel.setItems(artikelFacade.findAll());
        artikel.setEmptySelectionAllowed(false);
        bild.setWidth(100, Unit.PIXELS);
        bild.setHeight(60, Unit.PIXELS);
        bild.setCaption("File upload");
        bild.setReceivedCallback(this::uploadReceived);
        // optional callbacks
        //	uploadComponent.setStartedCallback(this::uploadStarted);
        //	uploadComponent.setProgressCallback(this::uploadProgress);
        //	uploadComponent.setFailedCallback(this::uploadFailed);

        return new VerticalLayout(new FormLayout(
                artikel, titel, bild, image
        ), getToolbar());
    }

    private void uploadReceived(String fileName, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            getEntity().setBild(data);

            StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                public InputStream getStream() {
                    return (data == null) ? null : new ByteArrayInputStream(data);
                }
            };
            StreamResource imageResource = new StreamResource(streamSource, fileName);
            //image = new Embedded("", (Resource)imageResource);

            image.setCaption("Testbild");
            image.setSource(imageResource);
            //image = new Image("Testbild", imageResource);
            System.err.println(Files.probeContentType(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*
    private void updateImage() {
        if (getEntity().getBild().length>0) {
            image
        }
    }
*/

}
