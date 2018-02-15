package com.gmail.michzuerch.anouman.presentation.ui.aufwand;

import com.gmail.michzuerch.anouman.backend.entity.Aufwand;
import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

/**
 * Created by michzuerch on 09.08.15.
 */
public class AufwandForm extends AbstractForm<Aufwand> {

    NativeSelect<Rechnung> rechnung = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    TextField bezeichnung = new TextField("Bezeichnung");
    DateTimeField start = new DateTimeField("Start");
    DateTimeField end = new DateTimeField("Ende");

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

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
        rechnung.setItems(rechnungDeltaspikeFacade.findAll());
        rechnung.setItemCaptionGenerator(rechnung1 -> rechnung1.getBezeichnung() + " id:" + rechnung1.getId());

//        getBinder().forField(start)
//                .withConverter(new com.vaadin.data.converter.LocalDateTimeToDateConverter(ZoneOffset.UTC))
//                .bind("start");
//
//        getBinder().forField(end)
//                .withConverter(new com.vaadin.data.converter.LocalDateTimeToDateConverter(ZoneOffset.UTC))
//                .bind("end");


        return new VerticalLayout(new FormLayout(
                rechnung, titel, bezeichnung, start, end
        ), getToolbar());
    }


}
