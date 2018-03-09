package com.gmail.michzuerch.anouman.presentation.ui.rechnungsposition;

import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.entity.Rechnungsposition;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.AnzahlField;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.BetragField;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.ui.NumberField;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

/**
 * Created by michzuerch on 09.08.15.
 */
public class RechnungspositionForm extends AbstractForm<Rechnungsposition> {
    private static Logger logger = LoggerFactory.getLogger(RechnungspositionForm.class.getName());

    NativeSelect<Rechnung> rechnung = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea bezeichnunglang = new TextArea("Bezeichnung Lang");
    TextField mengeneinheit = new TextField("Mengeneinheit");
    BetragField stueckpreis = new BetragField("Stückpreis");
    AnzahlField anzahl = new AnzahlField("Anzahl");

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    public RechnungspositionForm() {
        super(Rechnungsposition.class);

    }

    public void lockSelect() {
        rechnung.setEnabled(false);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Rechnungsposition");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {

        rechnung.setCaption("Rechnung");
        rechnung.setEmptySelectionAllowed(false);
        rechnung.setItems(rechnungDeltaspikeFacade.findAll());
        rechnung.setItemCaptionGenerator(rechnung1 -> rechnung1.getBezeichnung());

        getBinder().forField(stueckpreis).withConverter(
                NumberField.getConverter("Muss Betrag sein")
        ).bind("stueckpreis");

        getBinder().forField(anzahl).withConverter(
                NumberField.getConverter("Muss Anzahl sein")
        ).bind("anzahl");

        return new VerticalLayout(new FormLayout(
                rechnung, bezeichnung, bezeichnunglang, mengeneinheit, stueckpreis, anzahl
        ), getToolbar());
    }


}
