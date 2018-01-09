package ch.internettechnik.anouman.presentation.ui.rechnungsposition;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

/**
 * Created by michzuerch on 09.08.15.
 */
@UIScope
public class RechnungspositionForm extends AbstractForm<Rechnungsposition> {
    private static Logger logger = LoggerFactory.getLogger(RechnungspositionForm.class.getName());

    NativeSelect<Rechnung> rechnung = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea bezeichnunglang = new TextArea("Bezeichnung Lang");
    TextField mengeneinheit = new TextField("Mengeneinheit");
    TextField stueckpreis = new TextField("Stückpreis");
    TextField anzahl = new TextField("Anzahl");

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
