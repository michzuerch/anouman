package ch.internettechnik.anouman.presentation.ui.templatemehrwertsteuercode;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

@ViewScoped
public class TemplateMehrwertsteuercodeForm extends AbstractForm<TemplateMehrwertsteuercode> {

    @Inject
    TemplateBuchhaltungFacade templateBuchhaltungFacade;

    NativeSelect<TemplateBuchhaltung> buchhaltung = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField code = new TextField("Code");
    TextField prozent = new TextField("Prozent");

    public TemplateMehrwertsteuercodeForm() {
        super(TemplateMehrwertsteuercode.class);
    }

    public void lockSelect() {
        buchhaltung.setEnabled(false);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Template Buchhaltung");
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        buchhaltung.setCaption("Template Buchhaltung");
        buchhaltung.setEmptySelectionAllowed(false);
        buchhaltung.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getId());
        buchhaltung.setItems(templateBuchhaltungFacade.findAll());

        getBinder().forField(prozent).withConverter(
                new StringToFloatConverter("Muss Prozent Zahl sein")
        ).bind("prozent");

        return new VerticalLayout(new FormLayout(buchhaltung, code, bezeichnung, prozent), getToolbar());
    }


}
