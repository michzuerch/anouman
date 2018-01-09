package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.fields.IntegerField;
import org.vaadin.viritin.form.AbstractForm;

/**
 * Created by michzuerch on 09.08.15.
 */
@UIScope
public class BuchhaltungForm extends AbstractForm<Buchhaltung> {
    private static Logger logger = LoggerFactory.getLogger(BuchhaltungForm.class.getName());

    TextField bezeichnung = new TextField("Bezeichnung");
    IntegerField jahr = new IntegerField("Jahr");


    public BuchhaltungForm() {
        super(Buchhaltung.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Buchhaltung");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(
                bezeichnung, jahr
        ), getToolbar());
    }


}
