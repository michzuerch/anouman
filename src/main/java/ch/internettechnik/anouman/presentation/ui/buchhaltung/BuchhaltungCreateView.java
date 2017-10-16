package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.TemplateBuchhaltungTreeView;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView(value = "BuchhaltungCreate")
public class BuchhaltungCreateView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungTreeView.class.getName());

    NativeSelect<TemplateBuchhaltung> templateBuchhaltungSelect = new NativeSelect<>();
    @Inject
    BuchhaltungFacade buchhaltungFacade;
    @Inject
    KontoklasseFacade kontoklasseFacade;
    @Inject
    KontogruppeFacade kontogruppeFacade;
    @Inject
    KontoartFacade kontoartFacade;
    @Inject
    KontoFacade kontoFacade;
    @Inject
    TemplateBuchhaltungFacade templateBuchhaltungFacade;
    @Inject
    BuchhaltungForm buchhaltungForm;
    @Inject
    private Menu menu;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        templateBuchhaltungSelect.setCaption("Template Buchhaltung");
        templateBuchhaltungSelect.setItemCaptionGenerator(templateBuchhaltung -> templateBuchhaltung.getBezeichnung() + " " + templateBuchhaltung.getId());
        templateBuchhaltungSelect.setItems(templateBuchhaltungFacade.findAll());
        templateBuchhaltungSelect.setEmptySelectionAllowed(false);
        templateBuchhaltungSelect.setSelectedItem(templateBuchhaltungFacade.findAll().get(0));

        Buchhaltung buchhaltung = new Buchhaltung();
        buchhaltung.setJahr(2017);
        buchhaltungForm.setEntity(buchhaltung);
        FormLayout layout = new FormLayout();
        layout.addComponents(templateBuchhaltungSelect, buchhaltungForm.createContent(), buchhaltungForm.getToolbar());
        addComponent(menu);
        addComponent(layout);

        buchhaltungForm.setSavedHandler(val -> {
                    if (buchhaltungForm.getBinder().isValid()) {

                        TemplateBuchhaltung templateBuchhaltung = templateBuchhaltungSelect.getValue();
                        templateBuchhaltung = templateBuchhaltungFacade.findBy(templateBuchhaltung.getId());

                        val = buchhaltungFacade.save(val);
                        for (TemplateKontoklasse templateKontoklasse : templateBuchhaltung.getTemplateKontoklasses()) {
                            Kontoklasse kontoklasse = new Kontoklasse();
                            kontoklasse.setBezeichnung(templateKontoklasse.getBezeichnung());
                            kontoklasse.setKontonummer(templateKontoklasse.getKontonummer());
                            kontoklasse.setBuchhaltung(val);
                            kontoklasse = kontoklasseFacade.save(kontoklasse);
                            val.getKontoklasse().add(kontoklasse);
                            val = buchhaltungFacade.save(val);

                            for (TemplateKontogruppe templateKontogruppe : templateKontoklasse.getTemplateKontogruppes()) {
                                Kontogruppe kontogruppe = new Kontogruppe();
                                kontogruppe.setBezeichnung(templateKontogruppe.getBezeichnung());
                                kontogruppe.setKontonummer(templateKontogruppe.getKontonummer());
                                kontogruppe.setKontoklasse(kontoklasse);
                                kontogruppe = kontogruppeFacade.save(kontogruppe);
                                kontoklasse.getKontogruppes().add(kontogruppe);
                                kontoklasse = kontoklasseFacade.save(kontoklasse);

                                for (TemplateKontoart templateKontoart : templateKontogruppe.getTemplateKontoarts()) {
                                    Kontoart kontoart = new Kontoart();
                                    kontoart.setBezeichnung(templateKontoart.getBezeichnung());
                                    kontoart.setKontonummer(templateKontoart.getKontonummer());
                                    kontoart.setKontogruppe(kontogruppe);
                                    kontoart = kontoartFacade.save(kontoart);
                                    kontogruppe.getKontoarts().add(kontoart);
                                    kontogruppe = kontogruppeFacade.save(kontogruppe);

                                    for (TemplateKonto templateKonto : templateKontoart.getTemplateKontos()) {
                                        Konto konto = new Konto();
                                        konto.setBezeichnung(templateKonto.getBezeichnung());
                                        konto.setBemerkung(templateKonto.getBemerkung());
                                        konto.setKontonummer(templateKonto.getKontonummer());
                                        konto.setKontoart(kontoart);
                                        konto = kontoFacade.save(konto);
                                        kontoart.getKontos().add(konto);
                                        kontoart = kontoartFacade.save(kontoart);
                                    }
                                }
                            }

                        }
                        val = buchhaltungFacade.save(val);
                        Notification.show("Buchhaltung erstellt id: " + val.getId());
                    }
                }
        );


    }
}
