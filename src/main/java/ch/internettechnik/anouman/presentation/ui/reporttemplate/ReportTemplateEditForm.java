package ch.internettechnik.anouman.presentation.ui.reporttemplate;

import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.jboss.logging.Logger;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
public class ReportTemplateEditForm extends AbstractForm<ReportTemplate> {
    private static final Logger LOGGER = Logger.getLogger(ReportTemplateEditForm.class);

    TextField bezeichnung = new MTextField("Bezeichnung");
    TemplateTextField template = new TemplateTextField();
    TextField filename = new MTextField("Dateiname");

    public ReportTemplateEditForm() {
        super(ReportTemplate.class);
    }


    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Template Edit");
        openInModalPopup.setWidth("800px");
        openInModalPopup.setHeight("800px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        template.setCaption("Report Template Edit");
        template.setWidth("600px");
        template.setSizeFull();
        VerticalLayout layout = new VerticalLayout();
        layout.addComponents(bezeichnung, filename);
        layout.addComponentsAndExpand(template);
        layout.addComponent(getToolbar());
        return layout;
    }
}
