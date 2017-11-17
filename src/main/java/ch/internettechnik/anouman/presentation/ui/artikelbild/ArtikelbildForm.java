package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;
import java.io.File;

@ViewScoped
public class ArtikelbildForm extends AbstractForm<Artikelbild> {
    @Inject
    ArtikelFacade artikelFacade;

    File tempFile;
    String filename;

    NativeSelect<Artikel> artikel = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    UploadField bild = new UploadField();

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
        bild.setCaption("Bild");
        bild.setDisplayUpload(true);
        bild.setFieldType(UploadField.FieldType.BYTE_ARRAY);
        artikel.setItemCaptionGenerator(artikel1 -> artikel1.getBezeichnung() + " id:" + artikel1.getId());
        artikel.setItems(artikelFacade.findAll());
        artikel.setEmptySelectionAllowed(false);
        return new VerticalLayout(new FormLayout(
                artikel, titel, bild
        ), getToolbar());
    }
}
