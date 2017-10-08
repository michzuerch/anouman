package ch.internettechnik.anouman.presentation.ui.templatemehrwertsteuercode;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateKontoFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
public class TemplateMehrwertsteuercodeForm extends AbstractForm<TemplateMehrwertsteuercode> {

    @Inject
    TemplateBuchhaltungFacade templateBuchhaltungFacade;

    @Inject
    TemplateKontoFacade templateKontoFacade;

    NativeSelect<TemplateBuchhaltung> templateBuchhaltung = new NativeSelect<>();
    NativeSelect<TemplateKonto> templateMehrwertsteuerKonto = new NativeSelect<>();
    TextField bezeichnung = new TextField("Bezeichnung");
    TextField code = new TextField("Code");
    TextField prozent = new TextField("Prozent");

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
            templateKontoklasse.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                templateKontogruppe.getTemplateKontoarts().stream().forEach(templateKontoart -> {
                    templateKontoart.getTemplateKontos().stream().forEach(templateKonto1 -> {
                        list.add(templateKonto1);
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
        templateBuchhaltung.setItems(templateBuchhaltungFacade.findAll());
        templateBuchhaltung.setSelectedItem(templateBuchhaltungFacade.findAll().get(0));

        templateMehrwertsteuerKonto.setCaption("Template Konto");
        templateMehrwertsteuerKonto.setEmptySelectionAllowed(false);
        templateMehrwertsteuerKonto.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getId());
        templateMehrwertsteuerKonto.setItems(createTemplateKontoList(templateBuchhaltung.getValue()));


        templateBuchhaltung.addValueChangeListener(event -> {
            templateMehrwertsteuerKonto.setItems(createTemplateKontoList(templateBuchhaltung.getValue()));
        });

        getBinder().forField(prozent).withConverter(
                new StringToFloatConverter("Muss Prozent Zahl sein")
        ).bind("prozent");

        return new VerticalLayout(new FormLayout(templateBuchhaltung, templateMehrwertsteuerKonto, code, bezeichnung, prozent), getToolbar());
    }


}
