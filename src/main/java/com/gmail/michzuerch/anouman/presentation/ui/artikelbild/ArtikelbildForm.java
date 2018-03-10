package com.gmail.michzuerch.anouman.presentation.ui.artikelbild;

import com.gmail.michzuerch.anouman.backend.entity.Artikel;
import com.gmail.michzuerch.anouman.backend.entity.Artikelbild;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.ImageField;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

public class ArtikelbildForm extends AbstractForm<Artikelbild> {
    @Inject
    ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    ArtikelbildDeltaspikeFacade artikelbildDeltaspikeFacade;

    NativeSelect<Artikel> artikel = new NativeSelect<>();
    TextField titel = new TextField("Titel");
    ImageField image = new ImageField("Image");

    Button debugButton = new Button("Debug");

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

        debugButton.addClickListener(event -> {
            //Notification.show(String.valueOf(image.getValue().length),Notification.Type.ERROR_MESSAGE);
            getBinder().getFields().forEach(hasValue -> System.err.println("Field:" + hasValue.getValue()));
            //Notification.show(getBinder().getFields()String.valueOf(image.getValue().length),Notification.Type.ERROR_MESSAGE);
        });

        return new VerticalLayout(new FormLayout(
                artikel, titel, image, debugButton
        ), getToolbar());
    }
}
