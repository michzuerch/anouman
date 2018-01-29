package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.templatemehrwertsteuercode;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateKontoDeltaspikeFacade;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.ui.NumberField;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TemplateMehrwertsteuercodeForm extends AbstractForm<TemplateMehrwertsteuercode> {
    private static Logger logger = LoggerFactory.getLogger(TemplateMehrwertsteuercodeForm.class.getName());

    @Inject
    TemplateBuchhaltungDeltaspikeFacade templateBuchhaltungDeltaspikeFacade;

    @Inject
    TemplateKontoDeltaspikeFacade templateKontoDeltaspikeFacade;

    NativeSelect<TemplateBuchhaltung> templateBuchhaltung = new NativeSelect<>();
    NativeSelect<TemplateKonto> templateMehrwertsteuerKonto = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField code = new TextField("Code");
    NumberField prozent = new NumberField("Prozent");
    CheckBox verkauf = new CheckBox("Verkauf");

    public TemplateMehrwertsteuercodeForm() {
        super(TemplateMehrwertsteuercode.class);
    }

    public void lockSelect() {
        templateBuchhaltung.setEnabled(false);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Template Buchhaltung");
        openInModalPopup.setWidth("600px");
        return openInModalPopup;
    }

    private List<TemplateKonto> createTemplateKontoList(TemplateBuchhaltung buchhaltung) {
        List<TemplateKonto> list = new ArrayList<>();

        buchhaltung.getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
            templateKontoklasse.getTemplateKontohauptgruppes().stream().forEach(templateKontohauptgruppe -> {
                templateKontohauptgruppe.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                    templateKontogruppe.getTemplateKontos().stream().forEach(templateKonto -> {
                        list.add(templateKonto);
                    });
                });
            });
        });
        return list;
    }

    @Override
    protected Component createContent() {
        templateBuchhaltung.setCaption("Template Buchhaltung");
        templateBuchhaltung.setEmptySelectionAllowed(false);
        templateBuchhaltung.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getId());
        templateBuchhaltung.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
        templateBuchhaltung.setSelectedItem(templateBuchhaltungDeltaspikeFacade.findAll().get(0));

        templateMehrwertsteuerKonto.setCaption("Template Konto");
        templateMehrwertsteuerKonto.setEmptySelectionAllowed(false);
        templateMehrwertsteuerKonto.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getId());
        templateMehrwertsteuerKonto.setItems(createTemplateKontoList(templateBuchhaltung.getValue()));


        templateBuchhaltung.addValueChangeListener(event -> {
            templateMehrwertsteuerKonto.setItems(createTemplateKontoList(templateBuchhaltung.getValue()));
        });

        prozent.setLocale(Locale.GERMAN);
        prozent.setDecimalPrecision(1);
        prozent.setDecimalSeparator('.');
        prozent.setGroupingSeparator('\'');
        prozent.setDecimalSeparatorAlwaysShown(false);
        prozent.setMinimumFractionDigits(2);
        //prozent.setMinValue(5);

        getBinder().forField(prozent).withConverter(
                NumberField.getConverter("Muss Prozent sein")
        ).bind("prozent");

        return new VerticalLayout(new FormLayout(templateBuchhaltung, templateMehrwertsteuerKonto, code, bezeichnung, verkauf, prozent), getToolbar());
    }


}
