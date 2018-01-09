package ch.internettechnik.anouman.presentation.ui.artikelkategorie;

import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@UIScope
public class ArtikelkategorieForm extends AbstractForm<Artikelkategorie> {

    TextField bezeichnung = new TextField("Bezeichnung");

    public ArtikelkategorieForm() {
        super(Artikelkategorie.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Artikelkategorie");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(
                bezeichnung
        ), getToolbar());
    }


}
