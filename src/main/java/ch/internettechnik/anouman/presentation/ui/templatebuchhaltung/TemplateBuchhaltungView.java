package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.form.TemplateBuchhaltungForm;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView(value = "TemplateBuchhaltung")
public class TemplateBuchhaltungView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    Grid<TemplateBuchhaltung> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private TemplateBuchhaltungFacade facade;

    @Inject
    private TemplateBuchhaltungForm form;

    @Inject
    private BuchhaltungFacade buchhaltungFacade;

    @Inject
    private KontoklasseFacade kontoklasseFacade;

    @Inject
    private KontogruppeFacade kontogruppeFacade;

    @Inject
    private KontoartFacade kontoartFacade;

    @Inject
    private KontoFacade kontoFacade;

    @Inject
    private SammelkontoFacade sammelkontoFacade;

    @Inject
    private MehrwertsteuercodeFacade mehrwertsteuercodeFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung...");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> filterTextBezeichnung.clear());

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new TemplateBuchhaltung());
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        Button mehrwertsteuercodeBtn = new Button(VaadinIcons.CODE);
        mehrwertsteuercodeBtn.setCaption("Mehrwertsteuercodes dieser Template-Buchhaltung");



        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Template Buchhaltung");
        grid.setCaptionAsHtml(true);
        grid.addColumn(TemplateBuchhaltung::getId).setCaption("id");
        grid.addColumn(TemplateBuchhaltung::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(buchhaltung -> buchhaltung.getTemplateKontoklasses().size()).setCaption("Anzahl Kontoklassen");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(adresse -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Template Buchhaltung id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((TemplateBuchhaltung) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(buchhaltung -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((TemplateBuchhaltung) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        facade.save(val);
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                    form.setResetHandler(val -> {
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                }));

        grid.addColumn(adresse -> "Buchhaltung erstellen",
                new ButtonRenderer(event -> {
                    TemplateBuchhaltung templateBuchhaltung = (TemplateBuchhaltung) event.getItem();

                    Buchhaltung buchhaltung = new Buchhaltung();
                    buchhaltung.setBezeichnung(templateBuchhaltung.getBezeichnung());
                    buchhaltung.setJahr(2000);
                    buchhaltung = buchhaltungFacade.save(buchhaltung);

                    for (TemplateKontoklasse templateKontoklasse : templateBuchhaltung.getTemplateKontoklasses()) {
                        Kontoklasse kontoklasse = new Kontoklasse();
                        kontoklasse.setBuchhaltung(buchhaltung);
                        kontoklasse.setBezeichnung(templateKontoklasse.getBezeichnung());
                        kontoklasse.setKontonummer(templateKontoklasse.getKontonummer());
                        kontoklasse = kontoklasseFacade.save(kontoklasse);

                        for (TemplateKontogruppe templateKontogruppe : templateKontoklasse.getTemplateKontogruppes()) {
                            Kontogruppe kontogruppe = new Kontogruppe();
                            kontogruppe.setBezeichnung(templateKontogruppe.getBezeichnung());
                            kontogruppe.setKontonummer(templateKontogruppe.getKontonummer());
                            kontogruppe = kontogruppeFacade.save(kontogruppe);

                            for (TemplateKontoart templateKontoart : templateKontogruppe.getTemplateKontoarts()) {
                                Kontoart kontoart = new Kontoart();
                                kontoart.setBezeichnung(templateKontoart.getBezeichnung());
                                kontoart.setKontonummer(templateKontoart.getKontonummer());
                                kontoart = kontoartFacade.save(kontoart);

                                for (TemplateSammelkonto templateSammelkonto : templateKontoart.getTemplateSammelkontos()) {
                                    Sammelkonto sammelkonto = new Sammelkonto();
                                    sammelkonto.setBezeichnung(templateSammelkonto.getBezeichnung());
                                    sammelkonto.setKontonummer(templateSammelkonto.getKontonummer());
                                    sammelkonto = sammelkontoFacade.save(sammelkonto);

                                    for (TemplateKonto templateKonto : templateSammelkonto.getTemplateKontos()) {
                                        Konto konto = new Konto();
                                        konto.setBezeichnung(templateKonto.getBezeichnung());
                                        konto.setKontonummer(templateKonto.getKontonummer());
                                        konto.setBemerkung(templateKonto.getBemerkung());
                                        konto = kontoFacade.save(konto);
                                    }
                                }
                            }
                        }
                    }
                    updateList();
                    Notification.show("Buchhaltung " + buchhaltung + " erstellt");
                })

        );



        updateList();

        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    public void updateList() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(facade.findAll());
    }

}
