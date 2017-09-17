package ch.internettechnik.anouman.presentation.ui.rechnungsposition;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.jpa.api.RechnungService;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.ui.*;
import org.jboss.logging.Logger;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

/**
 * Created by michzuerch on 09.08.15.
 */
@ViewScoped
public class RechnungspositionForm extends AbstractForm<Rechnungsposition> {
    private static final Logger LOGGER = Logger.getLogger(RechnungspositionForm.class);

    NativeSelect<Rechnung> rechnung = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea bezeichnunglang = new TextArea("Bezeichnung Lang");
    TextField mengeneinheit = new TextField("Mengeneinheit");
    TextField stueckpreis = new TextField("Stückpreis");
    TextField anzahl = new TextField("Anzahl");

    @Inject
    RechnungService rechnungService;

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
        rechnung.setItems(rechnungService.findAll());
        rechnung.setItemCaptionGenerator(rechnung1 -> rechnung1.getBezeichnung());

        getBinder().forField(stueckpreis).withConverter(
                new StringToDoubleConverter("Muss Betrag sein")
        ).bind("stueckpreis");

        getBinder().forField(anzahl).withConverter(
                new StringToDoubleConverter("Muss Anzahl sein")
        ).bind("anzahl");

        return new VerticalLayout(new FormLayout(
                rechnung, bezeichnung, bezeichnunglang, mengeneinheit, stueckpreis, anzahl
        ), getToolbar());
    }


}
