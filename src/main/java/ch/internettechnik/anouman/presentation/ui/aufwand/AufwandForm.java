package ch.internettechnik.anouman.presentation.ui.aufwand;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;
import java.time.ZoneOffset;

/**
 * Created by michzuerch on 09.08.15.
 */
@ViewScoped
public class AufwandForm extends AbstractForm<Aufwand> {

    NativeSelect<Rechnung> rechnung = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    TextField bezeichnung = new TextField("Bezeichnung");
    DateTimeField start = new DateTimeField("Start");
    DateTimeField ende = new DateTimeField("Ende");

    @Inject
    RechnungFacade rechnungFacade;

    public AufwandForm() {
        super(Aufwand.class);
    }

    public void lockSelect() {
        rechnung.setEnabled(false);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Aufwand");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        rechnung.setCaption("Rechnung");
        rechnung.setEmptySelectionAllowed(false);
        rechnung.setItems(rechnungFacade.findAll());
        rechnung.setItemCaptionGenerator(rechnung1 -> rechnung1.getBezeichnung() + " id:" + rechnung1.getId());

        getBinder().forField(start)
                .withConverter(new com.vaadin.data.converter.LocalDateTimeToDateConverter(ZoneOffset.UTC))
                .bind("start");

        getBinder().forField(ende)
                .withConverter(new com.vaadin.data.converter.LocalDateTimeToDateConverter(ZoneOffset.UTC))
                .bind("ende");


        return new VerticalLayout(new FormLayout(
                rechnung, titel, bezeichnung, start, ende
        ), getToolbar());
    }


}
