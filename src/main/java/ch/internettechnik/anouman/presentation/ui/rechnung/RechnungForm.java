package ch.internettechnik.anouman.presentation.ui.rechnung;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AdresseFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.fields.IntegerField;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

/**
 * Created by michzuerch on 09.08.15.
 */
@ViewScoped
public class RechnungForm extends AbstractForm<Rechnung> {
    private static Logger logger = LoggerFactory.getLogger(RechnungForm.class.getName());

    NativeSelect<Adresse> adresse = new NativeSelect<>("Adresse");
    DateField rechnungsdatum = new DateField("rechnungsdatum");
    TextField bezeichnung = new TextField("Bezeichnung");
    IntegerField faelligInTagen = new IntegerField("Fällig in Tagen");
    CheckBox bezahlt = new CheckBox("Bezahlt");
    CheckBox verschickt = new CheckBox("Verschickt");

    @Inject
    AdresseFacade facade;

    public RechnungForm() {
        super(Rechnung.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        adresse.setCaption("Adresse");
        adresse.setEmptySelectionAllowed(false);

        adresse.setItemCaptionGenerator(item -> item.getFirma() + " id:" + item.getId());
        adresse.setItems(facade.findAll());

        getBinder().forField(rechnungsdatum)
                .withConverter(new com.vaadin.data.converter.LocalDateToDateConverter())
                .bind("rechnungsdatum");


        return new VerticalLayout(new FormLayout(
                adresse, bezeichnung, rechnungsdatum, faelligInTagen, bezahlt, verschickt
        ), getToolbar());
    }


}
