package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.TemplateBuchhaltungTreeView;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.StatusChangeEvent;
import com.vaadin.data.StatusChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.fields.IntegerField;

import javax.inject.Inject;
import java.time.LocalDate;

@CDIView(value = "BuchhaltungCreate")
public class BuchhaltungCreateView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungTreeView.class.getName());
    private BeanValidationBinder<Buchhaltung> binder = new BeanValidationBinder<>(Buchhaltung.class);
    private TextField bezeichnungField = new TextField("Bezeichnung");
    private IntegerField jahrField = new IntegerField("Jahr");
    private NativeSelect<TemplateBuchhaltung> templateBuchhaltungSelect = new NativeSelect<>();
    private Button createBuchhaltungBtn = new Button("Erstelle Buchhaltung");

    @Inject
    BuchhaltungFacade buchhaltungFacade;
    @Inject
    KontoklasseFacade kontoklasseFacade;
    @Inject
    KontohauptgruppeFacade kontohauptgruppeFacade;
    @Inject
    KontogruppeFacade kontogruppeFacade;
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

        binder.forField(bezeichnungField).bind(Buchhaltung::getBezeichnung, Buchhaltung::setBezeichnung);
        binder.forField(jahrField).bind(Buchhaltung::getJahr, Buchhaltung::setJahr);
        createBuchhaltungBtn.setEnabled(false);


        Buchhaltung buchhaltung = new Buchhaltung();
        LocalDate currentDate = LocalDate.now();
        buchhaltung.setJahr(currentDate.getYear());
        binder.setBean(buchhaltung);

        binder.addStatusChangeListener(new StatusChangeListener() {
            @Override
            public void statusChange(StatusChangeEvent statusChangeEvent) {
                if (binder.isValid()) {
                    createBuchhaltungBtn.setEnabled(true);
                } else {
                    createBuchhaltungBtn.setEnabled(false);
                }
            }
        });

        buchhaltungForm.setEntity(buchhaltung);
        FormLayout layout = new FormLayout();
        layout.addComponents(templateBuchhaltungSelect, bezeichnungField, jahrField, createBuchhaltungBtn);
        addComponent(menu);
        addComponent(layout);

        createBuchhaltungBtn.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                TemplateBuchhaltung templateBuchhaltung = templateBuchhaltungSelect.getValue();
                templateBuchhaltung = templateBuchhaltungFacade.findBy(templateBuchhaltung.getId());
                Buchhaltung buchhaltung1 = binder.getBean();
                buchhaltung1 = buchhaltungFacade.save(buchhaltung1);
                for (TemplateKontoklasse templateKontoklasse : templateBuchhaltung.getTemplateKontoklasses()) {
                    Kontoklasse kontoklasse = new Kontoklasse();
                    kontoklasse.setBezeichnung(templateKontoklasse.getBezeichnung());
                    kontoklasse.setKontonummer(templateKontoklasse.getKontonummer());
                    kontoklasse.setBuchhaltung(buchhaltung1);
                    kontoklasse = kontoklasseFacade.save(kontoklasse);
                    buchhaltung1.getKontoklasse().add(kontoklasse);
                    buchhaltung1 = buchhaltungFacade.save(buchhaltung1);

                    for (TemplateKontohauptgruppe templateKontohauptgruppe : templateKontoklasse.getTemplateKontohauptgruppes()) {
                        Kontohauptgruppe kontohauptgruppe = new Kontohauptgruppe();
                        kontohauptgruppe.setBezeichnung(templateKontohauptgruppe.getBezeichnung());
                        kontohauptgruppe.setKontonummer(templateKontohauptgruppe.getKontonummer());
                        kontohauptgruppe.setKontoklasse(kontoklasse);
                        kontohauptgruppe = kontohauptgruppeFacade.save(kontohauptgruppe);
                        kontoklasse.getKontohauptgruppes().add(kontohauptgruppe);
                        kontoklasse = kontoklasseFacade.save(kontoklasse);

                        for (TemplateKontogruppe templateKontogruppe : templateKontohauptgruppe.getTemplateKontogruppes()) {
                            Kontogruppe kontogruppe = new Kontogruppe();
                            kontogruppe.setBezeichnung(templateKontogruppe.getBezeichnung());
                            kontogruppe.setKontonummer(templateKontogruppe.getKontonummer());
                            kontogruppe.setKontohauptgruppe(kontohauptgruppe);
                            kontogruppe = kontogruppeFacade.save(kontogruppe);
                            kontohauptgruppe.getKontogruppes().add(kontogruppe);
                            kontohauptgruppe = kontohauptgruppeFacade.save(kontohauptgruppe);

                            for (TemplateKonto templateKonto : templateKontogruppe.getTemplateKontos()) {
                                Konto konto = new Konto();
                                konto.setBezeichnung(templateKonto.getBezeichnung());
                                konto.setKontonummer(templateKonto.getKontonummer());
                                konto.setKontogruppe(kontogruppe);
                                konto = kontoFacade.save(konto);
                                kontogruppe.getKontos().add(konto);
                                kontogruppe = kontogruppeFacade.save(kontogruppe);
                            }
                        }
                    }
                }
                buchhaltung1 = buchhaltungFacade.save(buchhaltung1);
                Notification.show("Buchhaltung erstellt id: " + buchhaltung1.getId());
                UI.getCurrent().getNavigator().navigateTo("BuchhaltungTree/id/" + buchhaltung1.getId());
            }

        });
    }
}
