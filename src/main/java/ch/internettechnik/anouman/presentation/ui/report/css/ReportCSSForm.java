package ch.internettechnik.anouman.presentation.ui.report.css;

import ch.internettechnik.anouman.backend.entity.report.css.ReportCSS;
import ch.internettechnik.anouman.presentation.ui.converter.ByteToStringConverter;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
public class ReportCSSForm extends AbstractForm<ReportCSS> {
    TextField bezeichnung = new TextField("Bezeichnung");
    TextArea template = new TextArea("Template String");

    public ReportCSSForm() {
        super(ReportCSS.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report CSS");
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
