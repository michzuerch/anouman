package ch.internettechnik.anouman.presentation.ui.report.fop;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOP;
import ch.internettechnik.anouman.presentation.ui.converter.ByteToStringConverter;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
public class ReportFOPForm extends AbstractForm<ReportFOP> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea template = new TextArea("Template String");

    public ReportFOPForm() {
        super(ReportFOP.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report FOP");
        //openInModalPopup.setWidth("500px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        getBinder().forField(template).withConverter(new ByteToStringConverter()).bind("template");
        return new VerticalLayout(new FormLayout(
                bezeichnung, template
        ), getToolbar());
    }

}
