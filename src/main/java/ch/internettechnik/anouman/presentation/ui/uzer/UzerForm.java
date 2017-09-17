package ch.internettechnik.anouman.presentation.ui.uzer;

import ch.internettechnik.anouman.backend.entity.Uzer;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.jboss.logging.Logger;
import org.vaadin.viritin.form.AbstractForm;

/**
 * Created by michzuerch on 09.08.15.
 */
@ViewScoped
public class UzerForm extends AbstractForm<Uzer> {
    private static final Logger LOGGER = Logger.getLogger(UzerForm.class);

    //@Inject
    //TemplateKontoplanSelect kontoplan;

    TextField principal = new TextField("principal");
    PasswordField pazzword = new PasswordField("password");
    TextField description = new TextField("description");

    public UzerForm() {
        super(Uzer.class);
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
                principal, pazzword, description
        ), getToolbar());
    }


}
