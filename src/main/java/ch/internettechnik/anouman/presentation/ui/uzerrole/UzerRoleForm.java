package ch.internettechnik.anouman.presentation.ui.uzerrole;

import ch.internettechnik.anouman.backend.entity.UzerRole;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.jboss.logging.Logger;
import org.vaadin.viritin.form.AbstractForm;

/**
 * Created by michzuerch on 09.08.15.
 */
@ViewScoped
public class UzerRoleForm extends AbstractForm<UzerRole> {
    private static final Logger LOGGER = Logger.getLogger(UzerRoleForm.class);

    //@Inject
    //TemplateKontoplanSelect kontoplan;
    TextField role = new TextField("role");
    TextField roleGroup = new TextField("rolegroup");

    public UzerRoleForm() {
        super(UzerRole.class);
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
                role, roleGroup
        ), getToolbar());
    }


}
