package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.apache.commons.io.FileUtils;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@ViewScoped
public class ArtikelbildForm extends AbstractForm<Artikelbild> implements Upload.Receiver, Upload.SucceededListener {
    @Inject
    ArtikelFacade artikelFacade;

    File tempFile;
    String filename;

    NativeSelect<Artikel> artikel = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    Upload bild = new Upload();

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
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        artikel.setCaption("Artikel");
        artikel.setItemCaptionGenerator(artikel1 -> artikel1.getBezeichnung() + " id:" + artikel1.getId());
        artikel.setItems(artikelFacade.findAll());
        artikel.setEmptySelectionAllowed(false);
        bild.setReceiver(this::receiveUpload);
        return new VerticalLayout(new FormLayout(
                artikel, titel, bild
        ), getToolbar());
    }

    @Override
    public OutputStream receiveUpload(String filename, String s1) {
        OutputStream outputStream = null;
        this.filename = filename;
        try {
            tempFile = File.createTempFile(this.filename, ".tmp");
            outputStream = new FileOutputStream(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        try {
            Artikelbild artikelbild = new Artikelbild();
            byte[] bytes = FileUtils.readFileToByteArray(tempFile);
            //bild.setValue(bytes);
            //this.getEntity().setBild(bytes);
            //this.getBinder().getBean().setBild(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
    }
}
