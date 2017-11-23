package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;
import server.droporchoose.UploadComponent;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ViewScoped
public class ArtikelbildForm extends AbstractForm<Artikelbild> {
    @Inject
    ArtikelFacade artikelFacade;

    NativeSelect<Artikel> artikel = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    UploadComponent bild = new UploadComponent();

    @Inject
    ArtikelbildFacade artikelbildFacade;

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

        bild.setWidth(300, Unit.PIXELS);
        bild.setHeight(200, Unit.PIXELS);
        bild.setCaption("File upload");
        bild.setReceivedCallback(this::uploadReceived);
        // optional callbacks
        //	uploadComponent.setStartedCallback(this::uploadStarted);
        //	uploadComponent.setProgressCallback(this::uploadProgress);
        //	uploadComponent.setFailedCallback(this::uploadFailed);

        return new VerticalLayout(new FormLayout(
                artikel, titel, bild
        ), getToolbar());
    }

    private void uploadReceived(String fileName, Path path) {
        //System.err.println("Upload finished: " + fileName + ", Path:" +path);
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            getEntity().setBild(data);
            System.err.println("len:" + data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
