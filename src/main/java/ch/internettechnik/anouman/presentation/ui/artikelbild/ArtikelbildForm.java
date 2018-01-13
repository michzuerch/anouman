package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildDeltaspikeFacade;
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

public class ArtikelbildForm extends AbstractForm<Artikelbild> {
    @Inject
    ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    ArtikelbildDeltaspikeFacade artikelbildDeltaspikeFacade;

    NativeSelect<Artikel> artikel = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    UploadComponent bild = new UploadComponent();
    Image image = new Image("Bild");

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
        artikel.setItems(artikelDeltaspikeFacade.findAll());
        artikel.setEmptySelectionAllowed(false);
        bild.setWidth(100, Unit.PIXELS);
        bild.setHeight(60, Unit.PIXELS);
        bild.setCaption("File upload");
        bild.setReceivedCallback(this::uploadReceived);

        image.setHeight(100, Unit.PIXELS);

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
            getEntity().setMimetype(Files.probeContentType(path));

            StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                public InputStream getStream() {
                    return (data == null) ? null : new ByteArrayInputStream(data);
                }
            };
            StreamResource imageResource = new StreamResource(streamSource, fileName);

            image.setCaption("Testbild");
            image.setSource(imageResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
