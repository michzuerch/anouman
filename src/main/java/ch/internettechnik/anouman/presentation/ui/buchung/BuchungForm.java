package ch.internettechnik.anouman.presentation.ui.buchung;

import ch.internettechnik.anouman.backend.entity.Buchung;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.vaadin.viritin.form.AbstractForm;

/**
 * Created by michzuerch on 09.08.15.
 */
@ViewScoped
public class BuchungForm extends AbstractForm<Buchung> {

    //@Inject
    //TemplateKontoplanSelect kontoplan;


    public BuchungForm() {
        super(Buchung.class);
        /*
        getBinder().forField(stundensatz).withConverter(
                new StringToFloatConverter("Muss Betrag sein")
        ).bind("stundensatz");
        */
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {


        return new VerticalLayout(new FormLayout(

        ), getToolbar());
    }


}
