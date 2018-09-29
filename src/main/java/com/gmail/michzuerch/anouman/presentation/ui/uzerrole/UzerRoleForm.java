package com.gmail.michzuerch.anouman.presentation.ui.uzerrole;

import com.gmail.michzuerch.anouman.backend.jpa.domain.UzerRole;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

/**
 * Created by michzuerch on 09.08.15.
 */
public class UzerRoleForm extends AbstractForm<UzerRole> {
    private static Logger logger = LoggerFactory.getLogger(UzerRoleForm.class.getName());

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
