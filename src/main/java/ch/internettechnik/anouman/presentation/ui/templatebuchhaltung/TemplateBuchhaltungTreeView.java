package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.form.*;
import ch.internettechnik.anouman.presentation.ui.templatemehrwertsteuercode.TemplateMehrwertsteuercodeForm;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CDIView(value = "TemplateBuchhaltungTree")
public class TemplateBuchhaltungTreeView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungTreeView.class.getName());

    private Tree<TemplateBuchhaltungTreeData> buchhaltungTree = new Tree<>();
    private Grid<TemplateKontoklasse> kontoklasseGrid = new Grid<>();
    private Grid<TemplateKontohauptgruppe> kontohauptgruppeGrid = new Grid<>();
    private Grid<TemplateKontogruppe> kontogruppeGrid = new Grid<>();
    private Grid<TemplateSammelkonto> sammelkontoGrid = new Grid<>();
    private Grid<TemplateKonto> kontoGrid = new Grid<>();
    private ComboBox<TemplateBuchhaltung> buchhaltungSelect = new ComboBox<>();
    private Button addBuchhaltungBtn = new Button("Erstelle Buchhaltung");
    private Button addGridBtn = new Button("Erstelle Konto");
    private Button mehrwertsteuercodeBtn = new Button("Mehrwertsteuercode");
    private Window windowMehrwertsteuercode;

    private TemplateBuchhaltungTreeData treeRootItem;

    @Inject
    private Menu menu;

    @Inject
    private TemplateBuchhaltungFacade templateBuchhaltungFacade;

    @Inject
    private TemplateKontoklasseFacade templateKontoklasseFacade;

    @Inject
    private TemplateKontohauptgruppeFacade templateKontohauptgruppeFacade;

    @Inject
    private TemplateKontogruppeFacade templateKontogruppeFacade;

    @Inject
    private TemplateKontoFacade templateKontoFacade;

    @Inject
    private TemplateBuchhaltungForm templateBuchhaltungForm;

    @Inject
    private TemplateKontoklasseForm templateKontoklasseForm;

    @Inject
    private TemplateKontohauptgruppeForm templateKontohauptgruppeForm;

    @Inject
    private TemplateKontogruppeForm templateKontogruppeForm;

    @Inject
    private TemplateSammelkontoFacade templateSammelkontoFacade;

    @Inject
    private TemplateKontoForm templateKontoForm;

    @Inject
    private TemplateMehrwertsteuercodeFacade templateMehrwertsteuercodeFacade;

    @Inject
    private TemplateMehrwertsteuercodeForm templateMehrwertsteuercodeForm;

    @Inject
    private TemplateSammelkontoForm templateSammelkontoForm;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");
        HorizontalLayout toolsLayout = new HorizontalLayout();
        HorizontalLayout bodyLayout = new HorizontalLayout();
        buchhaltungSelect = createTemplateBuchhaltungSelect();
        buchhaltungSelect.setWidth(30, Unit.PERCENTAGE);

        addBuchhaltungBtn = createButtonAddTemplateBuchhaltung();
        windowMehrwertsteuercode = createTemplateMehrwertsteuerWindow(buchhaltungSelect.getValue());
        toolsLayout.addComponentsAndExpand(buchhaltungSelect);
        toolsLayout.addComponents(addBuchhaltungBtn, mehrwertsteuercodeBtn);

        addBuchhaltungBtn.setIcon(VaadinIcons.CAMERA);
        Panel buchhaltungPanel = new Panel("Template Buchhaltung");
        VerticalLayout buchhaltungLayout = new VerticalLayout();
        buchhaltungTree = new Tree<>();
        updateTree(0L);

        buchhaltungPanel.setSizeFull();
        buchhaltungLayout.setSizeFull();
        buchhaltungTree.setSizeFull();
        buchhaltungLayout.addComponentsAndExpand(buchhaltungTree);
        buchhaltungPanel.setContent(buchhaltungLayout);
        buchhaltungTree.setSelectionMode(Grid.SelectionMode.SINGLE);
        buchhaltungTree.select(treeRootItem);


//        buchhaltungTree.setItemCollapseAllowedProvider(new ItemCollapseAllowedProvider<TemplateBuchhaltungTreeData>() {
//            @Override
//            public boolean test(TemplateBuchhaltungTreeData item) {
//                return false;
//            }
//        });

        bodyLayout.addComponent(buchhaltungPanel);
        bodyLayout.setExpandRatio(buchhaltungPanel, 0);
        bodyLayout.setSizeFull();

        addGridBtn = createButtonAddTemplateKontoklasse();
        kontoklasseGrid = createGridTemplateKontoklasse(buchhaltungTree.asSingleSelect().getValue());
        bodyLayout.addComponentsAndExpand(addGridBtn, kontoklasseGrid);
        bodyLayout.setExpandRatio(addGridBtn, 0.1f);
        bodyLayout.setExpandRatio(kontoklasseGrid, 4);
        mehrwertsteuercodeBtn.addClickListener(event -> {
            UI.getCurrent().addWindow(windowMehrwertsteuercode);
        });

        buchhaltungTree.setSelectionMode(Grid.SelectionMode.SINGLE);
        buchhaltungTree.setAutoRecalculateWidth(true);

        buchhaltungSelect.addValueChangeListener(event -> {
            updateTree(0L);
            if (!(treeRootItem == null)) {
                buchhaltungTree.select(treeRootItem);
                buchhaltungTree.expand(treeRootItem);
            }
            windowMehrwertsteuercode = createTemplateMehrwertsteuerWindow(buchhaltungSelect.getValue());
        });

        buchhaltungTree.addSelectionListener(event -> {
            bodyLayout.removeComponent(kontoklasseGrid);
            bodyLayout.removeComponent(kontohauptgruppeGrid);
            bodyLayout.removeComponent(kontogruppeGrid);
            bodyLayout.removeComponent(sammelkontoGrid);
            bodyLayout.removeComponent(kontoGrid);

            bodyLayout.removeComponent(addGridBtn);
            windowMehrwertsteuercode = createTemplateMehrwertsteuerWindow(buchhaltungSelect.getValue());
            if (!buchhaltungTree.asSingleSelect().isEmpty()) {
                TemplateBuchhaltungTreeData selectedItem = buchhaltungTree.asSingleSelect().getValue();
                buchhaltungTree.expand(selectedItem);
                if (selectedItem.getType().equals("BH")) {
                    kontoklasseGrid = createGridTemplateKontoklasse(selectedItem);
                    addGridBtn = createButtonAddTemplateKontoklasse();

                    bodyLayout.addComponents(addGridBtn, kontoklasseGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoklasseGrid, 4);
                }
                if (selectedItem.getType().equals("KK")) {
                    kontohauptgruppeGrid = createGridTemplateKontohauptgruppe(selectedItem);
                    addGridBtn = createButtonAddTemplateKontohauptgruppe(templateKontoklasseFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontohauptgruppeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontohauptgruppeGrid, 4);
                }
                if (selectedItem.getType().equals("KG")) {
                    kontogruppeGrid = createGridTemplateKontogruppe(selectedItem);
                    addGridBtn = createButtonAddTemplateKontogruppe(templateKontohauptgruppeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontogruppeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontogruppeGrid, 4);
                }
                if (selectedItem.getType().equals("KA")) {
                    sammelkontoGrid = createGridTemplateSammelkonto(selectedItem);
                    addGridBtn = createButtonAddTemplateSammelkonto(templateKontogruppeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, sammelkontoGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(sammelkontoGrid, 4);
                }
                if (selectedItem.getType().equals("SK")) {
                    kontoGrid = createGridTemplateKonto(selectedItem);
                    addGridBtn = createButtonAddTemplateKonto(templateSammelkontoFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontoGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoGrid, 4);
                }
                //Notification.show("Selected from Tree:" + selectedItem.getType() + " id:" + selectedItem.getId(), Notification.Type.TRAY_NOTIFICATION);
            }
        });

        addComponents(menu, toolsLayout);
        addComponentsAndExpand(bodyLayout);
        bodyLayout.setExpandRatio(buchhaltungPanel, 1);

    }

    private Button createButtonAddTemplateBuchhaltung() {
        Button button = new Button("Erstelle Template Buchhaltung");
        button.setIcon(VaadinIcons.ASTERISK);
        button.addClickListener(event -> {
            templateBuchhaltungForm.setEntity(new TemplateBuchhaltung());
            templateBuchhaltungForm.openInModalPopup();
            templateBuchhaltungForm.setSavedHandler(val -> {
                val = templateBuchhaltungFacade.save(val);
                buchhaltungSelect.setItems(templateBuchhaltungFacade.findAll());
                buchhaltungSelect.setSelectedItem(val);
                templateBuchhaltungForm.closePopup();
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontoklasse() {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Kontoklasse");
        button.addClickListener(event -> {
            kontoklasseGrid.asSingleSelect().clear();
            templateKontoklasseForm.setEntity(new TemplateKontoklasse());
            templateKontoklasseForm.openInModalPopup();
            templateKontoklasseForm.setSavedHandler(val -> {
                TemplateBuchhaltung bh = templateBuchhaltungFacade.findBy(buchhaltungSelect.getValue().getId());
                val.setTemplateBuchhaltung(bh);
                val = templateKontoklasseFacade.save(val);
                buchhaltungSelect.setSelectedItem(val.getTemplateBuchhaltung());
                updateTree(val.getTemplateBuchhaltung().getId());
                kontoklasseGrid.setItems(templateBuchhaltungFacade.findBy(buchhaltungSelect.getValue().getId()).getTemplateKontoklasses());
                kontoklasseGrid.select(val);
                templateKontoklasseForm.closePopup();
                Notification.show("Add Kontoklasse " + val);
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontohauptgruppe(TemplateKontoklasse templateKontoklasse) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Kontohauptgruppe");
        button.addClickListener(event -> {
            kontohauptgruppeGrid.asSingleSelect().clear();
            templateKontohauptgruppeForm.setEntity(new TemplateKontohauptgruppe());
            templateKontohauptgruppeForm.openInModalPopup();
            templateKontohauptgruppeForm.setSavedHandler(val -> {
                TemplateKontoklasse kk = templateKontoklasseFacade.findBy(templateKontoklasse.getId());
                val.setTemplateKontoklasse(kk);
                val = templateKontohauptgruppeFacade.save(val);
                updateTree(val.getTemplateKontoklasse().getId());
                kontohauptgruppeGrid.select(val);
                templateKontohauptgruppeForm.closePopup();
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontogruppe(TemplateKontohauptgruppe templateKontohauptgruppe) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Kontohauptgruppe");
        button.addClickListener(event -> {
            kontogruppeGrid.asSingleSelect().clear();
            templateKontogruppeForm.setEntity(new TemplateKontogruppe());
            templateKontogruppeForm.openInModalPopup();
            templateKontogruppeForm.setSavedHandler(val -> {
                TemplateKontohauptgruppe kg = templateKontohauptgruppeFacade.findBy(templateKontohauptgruppe.getId());
                val.setTemplateKontohauptgruppe(templateKontohauptgruppe);
                val = templateKontogruppeFacade.save(val);
                updateTree(val.getTemplateKontohauptgruppe().getId());
                kontogruppeGrid.select(val);
                templateKontogruppeForm.closePopup();
            });
        });
        return button;
    }


    private Button createButtonAddTemplateKonto(TemplateKontogruppe templateKontogruppe) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Konto");
        button.addClickListener(event -> {
            kontoGrid.asSingleSelect().clear();
            templateKontoForm.setEntity(new TemplateKonto());
            templateKontoForm.openInModalPopup();
            templateKontoForm.setSavedHandler(val -> {
                TemplateKonto konto = templateKontoFacade.findBy(templateKontogruppe.getId());
                val.setTemplateKonto(konto);
                val = templateKontoFacade.save(val);
                updateTree(val.getTemplateKontogruppe().getId());
                kontoGrid.select(val);
                templateKontoForm.closePopup();
            });
        });
        return button;
    }

    private ComboBox<TemplateBuchhaltung> createTemplateBuchhaltungSelect() {
        ComboBox<TemplateBuchhaltung> select = new ComboBox<>();
        Collection<TemplateBuchhaltung> buchhaltungen = templateBuchhaltungFacade.findAll();
        select.setEmptySelectionAllowed(false);
        select.setItemCaptionGenerator(item -> item.getBezeichnung() + " id:" + item.getId());
        select.setItems(buchhaltungen);
        select.setSelectedItem(buchhaltungen.iterator().next());
        return select;
    }

    private Grid<TemplateKontoklasse> createGridTemplateKontoklasse(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateKontoklasse> binderTemplateKontoklasse = new BeanValidationBinder<>(TemplateKontoklasse.class);

        Grid<TemplateKontoklasse> grid = new Grid<>();
        grid.setCaption("Kontoklassen");
        grid.setSizeFull();
        TemplateBuchhaltung buchhaltung = templateBuchhaltungFacade.findBy(val.getId());
        grid.setItems(buchhaltung.getTemplateKontoklasses());
        binderTemplateKontoklasse.bind(bezeichnungFld, TemplateKontoklasse::getBezeichnung, TemplateKontoklasse::setBezeichnung);
        binderTemplateKontoklasse.bind(kontonummerFld, TemplateKontoklasse::getKontonummer, TemplateKontoklasse::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKontoklasse);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKontoklasse.isValid()) {
                TemplateKontoklasse templateKontoklasse = event.getBean();
                templateKontoklasse = templateKontoklasseFacade.save(templateKontoklasse);
                grid.setItems(templateKontoklasseFacade.findByTemplateBuchhaltung(buchhaltung));
            }
        });

        grid.addColumn(TemplateKontoklasse::getId).setCaption("Id");
        grid.addColumn(TemplateKontoklasse::getVersion).setCaption("Version");
        grid.addColumn(TemplateKontoklasse::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontoklasse::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKontoklasse kontoklasse = (TemplateKontoklasse) event.getItem();
                    TemplateBuchhaltung buchhaltung1 = kontoklasse.getTemplateBuchhaltung();
                    Notification.show("Lösche Kontoklasse id:" + kontoklasse);
                    buchhaltung1.getTemplateKontoklasses().remove(kontoklasse);
                    templateBuchhaltungFacade.save(buchhaltung1);
                    templateKontoklasseFacade.delete(kontoklasse);
                    grid.setItems(buchhaltung.getTemplateKontoklasses());
                    updateTree(buchhaltung1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateKontohauptgruppe> createGridTemplateKontohauptgruppe(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateKontohauptgruppe> templateKontohauptgruppeBeanValidationBinder = new BeanValidationBinder<>(TemplateKontohauptgruppe.class);

        Grid<TemplateKontohauptgruppe> grid = new Grid<>();
        grid.setCaption("Kontohauptgruppen");
        grid.setSizeFull();
        TemplateKontoklasse kontoklasse = templateKontoklasseFacade.findBy(val.getId());
        grid.setItems(kontoklasse.getTemplateKontohauptgruppes());
        templateKontohauptgruppeBeanValidationBinder.bind(bezeichnungFld, TemplateKontohauptgruppe::getBezeichnung, TemplateKontohauptgruppe::setBezeichnung);
        templateKontohauptgruppeBeanValidationBinder.bind(kontonummerFld, TemplateKontohauptgruppe::getKontonummer, TemplateKontohauptgruppe::setKontonummer);

        grid.getEditor().setBinder(templateKontohauptgruppeBeanValidationBinder);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (templateKontohauptgruppeBeanValidationBinder.isValid()) {
                TemplateKontohauptgruppe templateKontohauptgruppe = event.getBean();
                templateKontohauptgruppeFacade.save(event.getBean());
                grid.setItems(templateKontohauptgruppeFacade.findByTemplateKontoklasse(templateKontohauptgruppe.getTemplateKontoklasse()));
            }
        });

        grid.addColumn(TemplateKontohauptgruppe::getId).setCaption("Id");
        grid.addColumn(TemplateKontohauptgruppe::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontohauptgruppe::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKontohauptgruppe templateKontohauptgruppe = (TemplateKontohauptgruppe) event.getItem();
                    TemplateKontoklasse kontoklasse1 = templateKontohauptgruppe.getTemplateKontoklasse();
                    Notification.show("Lösche Kontohauptgruppe id:" + templateKontohauptgruppe.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    kontoklasse1.getTemplateKontohauptgruppes().remove(templateKontohauptgruppe);
                    kontoklasse1 = templateKontoklasseFacade.save(kontoklasse1);
                    templateKontohauptgruppeFacade.delete(templateKontohauptgruppe);
                    grid.setItems(kontoklasse.getTemplateKontohauptgruppes());
                    updateTree(kontoklasse1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateKontogruppe> createGridTemplateKontogruppe(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateKontogruppe> binderTemplateKontogruppe = new BeanValidationBinder<>(TemplateKontogruppe.class);

        Grid<TemplateKontogruppe> grid = new Grid<>();
        grid.setCaption("Kontogruppen");
        grid.setSizeFull();
        TemplateKontohauptgruppe templateKontohauptgruppe = templateKontohauptgruppeFacade.findBy(val.getId());
        grid.setItems(templateKontohauptgruppe.getTemplateKontogruppes());

        binderTemplateKontogruppe.bind(bezeichnungFld, TemplateKontogruppe::getBezeichnung, TemplateKontogruppe::setBezeichnung);
        binderTemplateKontogruppe.bind(kontonummerFld, TemplateKontogruppe::getKontonummer, TemplateKontogruppe::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKontogruppe);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKontogruppe.isValid()) {
                templateKontogruppeFacade.save(event.getBean());
                grid.setItems(templateKontohauptgruppe.getTemplateKontogruppes());
            }
        });

        grid.addColumn(TemplateKontogruppe::getId).setCaption("Id");
        grid.addColumn(TemplateKontogruppe::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontogruppe::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKontogruppe templateKontogruppe = (TemplateKontogruppe) event.getItem();
                    TemplateKontohauptgruppe templateKontohauptgruppe1 = templateKontogruppe.getTemplateKontohauptgruppe();
                    Notification.show("Lösche Kontohauptgruppe id:" + templateKontogruppe.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    templateKontohauptgruppe1.getTemplateKontogruppes().remove(templateKontogruppe);
                    templateKontohauptgruppe1 = templateKontohauptgruppeFacade.save(templateKontohauptgruppe1);
                    templateKontogruppeFacade.delete(templateKontogruppe);
                    grid.setItems(templateKontohauptgruppe.getTemplateKontogruppes());
                    updateTree(templateKontohauptgruppe1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateKonto> createGridTemplateKonto(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateKonto> binderTemplateKonto = new BeanValidationBinder<>(TemplateKonto.class);

        Grid<TemplateKonto> grid = new Grid<>();
        grid.setCaption("Konti");
        grid.setSizeFull();
        TemplateSammelkonto sammelkonto = templateSammelkontoFacade.findBy(val.getId());
        grid.setItems(sammelkonto.getTemplateKontos());

        binderTemplateKonto.bind(bezeichnungFld, TemplateKonto::getBezeichnung, TemplateKonto::setBezeichnung);
        binderTemplateKonto.bind(kontonummerFld, TemplateKonto::getKontonummer, TemplateKonto::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKonto);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKonto.isValid()) {
                templateKontoFacade.save(event.getBean());
                grid.setItems(sammelkonto.getTemplateKontos());
            }
        });

        grid.addColumn(TemplateKonto::getId).setCaption("Id");
        grid.addColumn(TemplateKonto::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKonto::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(konto -> "ändern",
                new ButtonRenderer(event -> {
                    templateKontoForm.setEntity((TemplateKonto) event.getItem());
                    templateKontoForm.openInModalPopup();
                    templateKontoForm.setSavedHandler(konto -> {
                        templateKontoFacade.save(konto);
                        updateTree(konto.getTemplateSammelkonto().getId());
                        grid.setItems(sammelkonto.getTemplateKontos());
                        grid.select(konto);
                        templateKontoForm.closePopup();
                    });
                    templateKontoForm.setResetHandler(konto -> {
                        grid.setItems(sammelkonto.getTemplateKontos());
                        grid.select(konto);
                        templateKontoForm.closePopup();
                    });
                })).setCaption("ändern");


        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKonto konto = (TemplateKonto) event.getItem();
                    TemplateSammelkonto templateSammelkonto = konto.getTemplateSammelkonto();
                    Notification.show("Lösche Konto id:" + konto.getId());
                    templateSammelkonto.getTemplateKontos().remove(konto);
                    templateSammelkonto = templateSammelkontoFacade.save(templateSammelkonto);
                    templateKontoFacade.delete(konto);
                    grid.setItems(sammelkonto.getTemplateKontos());
                    updateTree(templateSammelkonto.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }


    private List<TemplateKonto> createTemplateKontoList(TemplateBuchhaltung buchhaltung) {
        List<TemplateKonto> list = new ArrayList<>();

        buchhaltung.getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
            templateKontoklasse.getTemplateKontohauptgruppes().stream().forEach(templateKontohauptgruppe -> {
                templateKontohauptgruppe.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                    templateKontogruppe.getTemplateSammelkontos().stream().forEach(templateSammelkonto -> {
                        templateSammelkonto.getTemplateKontos().stream().forEach(templateKonto -> {
                            list.add(templateKonto);
                        });
                    });
                });
            });
        });
        return list;
    }

    private Window createTemplateMehrwertsteuerWindow(TemplateBuchhaltung buchhaltung) {
        TemplateBuchhaltung bh = templateBuchhaltungFacade.findBy(buchhaltung.getId());
        HorizontalLayout layout = new HorizontalLayout();

        Grid<TemplateMehrwertsteuercode> grid = new Grid<>();

        grid.setCaption("Mehrwertsteuercodes");
        grid.setItems(bh.getTemplateMehrwertsteuercodes());
        grid.getEditor().setEnabled(true);

        grid.getEditor().addSaveListener(event -> {
            if (grid.getEditor().getBinder().isValid()) {
                templateMehrwertsteuercodeFacade.save(event.getBean());
                grid.setItems(getMehrwertsteuerList());
            }
        });

        // @todo Lieber direkt Float als String...
//        TextField prozentFld = new TextField();
        // @todo Validator geht nicht...
//        grid.getEditor().getBinder().forField(prozentFld).withValidator(s -> Validator.isFloat(s)==true, "kein Float");

        //TextField prozentField = new TextField();
        //grid.getEditor().getBinder().forField(TemplateMehrwertsteuercode, "code").withConverter(new StringToDoubleConverter("Muss Betrag sein")
        //).bind("stueckpreis");
        grid.addColumn(TemplateMehrwertsteuercode::getId).setCaption("Id");
        grid.addColumn(TemplateMehrwertsteuercode::getVersion).setCaption("Vers");
        grid.addColumn(TemplateMehrwertsteuercode::getCode).setEditorComponent(new TextField(), TemplateMehrwertsteuercode::setCode).setCaption("Code");
        grid.addColumn(TemplateMehrwertsteuercode::getBezeichnung).setEditorComponent(new TextField(), TemplateMehrwertsteuercode::setBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(TemplateMehrwertsteuercode::getProzentString).setEditorComponent(new TextField(), TemplateMehrwertsteuercode::setProzentString).setCaption("Prozent");
        grid.addColumn(TemplateMehrwertsteuercode::isVerkauf).setEditorComponent(new CheckBox(), TemplateMehrwertsteuercode::setVerkauf).setCaption("Verkauf");
        grid.addColumn(templateMehrwertsteuercode -> templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getBezeichnung()
                + " " + templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getId()).setCaption("Konto");
        grid.addColumn(templateMehrwertsteuercode -> templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getShowKontonummer()).setCaption("KoNr");
        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateMehrwertsteuercode mehrwertsteuercode = (TemplateMehrwertsteuercode) event.getItem();
                    mehrwertsteuercode = templateMehrwertsteuercodeFacade.findBy(mehrwertsteuercode.getId());
                    TemplateBuchhaltung buchhaltung1 = mehrwertsteuercode.getTemplateBuchhaltung();
                    buchhaltung1 = templateBuchhaltungFacade.findBy(buchhaltung1.getId());
                    buchhaltung1.getTemplateMehrwertsteuercodes().remove(mehrwertsteuercode);
                    buchhaltung1 = templateBuchhaltungFacade.save(buchhaltung1);

                    Notification.show("Löschen Template Mehrwertsteuercode id:" + mehrwertsteuercode.getId());
                    templateMehrwertsteuercodeFacade.delete(mehrwertsteuercode);
                    grid.setItems(templateMehrwertsteuercodeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue()));
                }));
        grid.addColumn(event -> "ändern",
                new ButtonRenderer(event -> {
                    TemplateMehrwertsteuercode mehrwertsteuercode = (TemplateMehrwertsteuercode) event.getItem();
                    mehrwertsteuercode = templateMehrwertsteuercodeFacade.findBy(mehrwertsteuercode.getId());
                    templateMehrwertsteuercodeForm.setEntity(mehrwertsteuercode);
                    templateMehrwertsteuercodeForm.lockSelect();
                    templateMehrwertsteuercodeForm.openInModalPopup();
                    templateMehrwertsteuercodeForm.setSavedHandler(val -> {
                        templateMehrwertsteuercodeFacade.save(val);
                        grid.setItems(templateMehrwertsteuercodeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue()));
                        grid.select(val);
                        templateMehrwertsteuercodeForm.closePopup();
                    });
                    templateMehrwertsteuercodeForm.setResetHandler(val -> {
                        grid.setItems(templateMehrwertsteuercodeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue()));
                        grid.select(val);
                        templateMehrwertsteuercodeForm.closePopup();
                    });

                }));

        Button mehrwertsteuerAddBtn = new Button("Add Mehrwertsteuercode", event -> {
            grid.asSingleSelect().clear();
            TemplateMehrwertsteuercode mehrwertsteuercode = new TemplateMehrwertsteuercode();
            mehrwertsteuercode.setTemplateBuchhaltung(buchhaltungSelect.getValue());
            mehrwertsteuercode.setTemplateMehrwertsteuerKonto(createTemplateKontoList(buchhaltungSelect.getValue()).get(0));

            mehrwertsteuercode.setProzent(8f);

            templateMehrwertsteuercodeForm.setWidth(500, Unit.PIXELS);
            templateMehrwertsteuercodeForm.lockSelect();
            templateMehrwertsteuercodeForm.setEntity(mehrwertsteuercode);
            templateMehrwertsteuercodeForm.openInModalPopup();
            templateMehrwertsteuercodeForm.setSavedHandler(templateMehrwertsteuercode -> {
                templateMehrwertsteuercodeFacade.save(templateMehrwertsteuercode);
                grid.setItems(getMehrwertsteuerList());
                grid.select(templateMehrwertsteuercode);
                templateMehrwertsteuercodeForm.closePopup();
            });

        });

        mehrwertsteuerAddBtn.setIcon(VaadinIcons.ASTERISK);
        mehrwertsteuerAddBtn.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        mehrwertsteuerAddBtn.setSizeUndefined();
        layout.addComponents(mehrwertsteuerAddBtn);
        layout.addComponentsAndExpand(grid);
        layout.setMargin(true);

        Window window = new Window("Template Mehrwersteuercode");
        window.setContent(layout);
        window.setClosable(true);
        window.setModal(true);
        window.setResizable(true);
        window.setDraggable(true);
        window.setWidth("1200px");
        window.setHeight("600px");
        window.center();
        return window;
    }


    private List<TemplateMehrwertsteuercode> getMehrwertsteuerList() {
        return templateMehrwertsteuercodeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue());
    }


    private void updateTree(Long selectId) {
        TemplateBuchhaltung buchhaltung = templateBuchhaltungFacade.findBy(buchhaltungSelect.getValue().getId());
        Collection<TemplateKontoklasse> kontoklasses = buchhaltung.getTemplateKontoklasses();
        TreeData<TemplateBuchhaltungTreeData> buchhaltungTreeData = new TreeData<>();
        TreeDataProvider<TemplateBuchhaltungTreeData> provider = new TreeDataProvider<>(buchhaltungTreeData);

        List<TemplateBuchhaltungTreeData> list = new ArrayList<>();

        buchhaltungTreeData.clear();
        buchhaltungTree.setDataProvider(provider);
        TemplateBuchhaltungTreeData valBH = new TemplateBuchhaltungTreeData(buchhaltung.getId(), "BH", buchhaltung.getBezeichnung() + " id:" + buchhaltung.getId());
        treeRootItem = valBH;
        list.add(valBH);
        buchhaltungTreeData.addItem(null, valBH);
        if (valBH.getId() == selectId) buchhaltungTree.select(valBH);
        kontoklasses.forEach(templateKontoklasse -> {
            TemplateBuchhaltungTreeData valKK = new TemplateBuchhaltungTreeData(templateKontoklasse.getId(), "KK", templateKontoklasse.getBezeichnung() + " KoNr:" + templateKontoklasse.getShowKontonummer());
            list.add(valKK);
            buchhaltungTreeData.addItem(valBH, valKK);
            if (valKK.getId() == selectId) buchhaltungTree.select(valKK);
            templateKontoklasse.getTemplateKontohauptgruppes().forEach(templateKontohauptgruppe -> {
                TemplateBuchhaltungTreeData valKG = new TemplateBuchhaltungTreeData(templateKontohauptgruppe.getId(), "KG", templateKontohauptgruppe.getBezeichnung() + " KoNr:" + templateKontohauptgruppe.getShowKontonummer());
                list.add(valKG);
                buchhaltungTreeData.addItem(valKK, valKG);
                if (valKG.getId() == selectId) buchhaltungTree.select(valKG);
                templateKontohauptgruppe.getTemplateKontogruppes().forEach(templateKontogruppe -> {
                    TemplateBuchhaltungTreeData valKA = new TemplateBuchhaltungTreeData(templateKontogruppe.getId(), "KA", templateKontogruppe.getBezeichnung() + " KoNr:" + templateKontogruppe.getShowKontonummer());
                    list.add(valKA);
                    buchhaltungTreeData.addItem(valKG, valKA);
                    if (valKA.getId() == selectId) buchhaltungTree.select(valKA);
                    templateKontogruppe.getTemplateSammelkontos().forEach(templateSammelkonto -> {
                        TemplateBuchhaltungTreeData valSK = new TemplateBuchhaltungTreeData(templateSammelkonto.getId(), "SK", templateSammelkonto.getBezeichnung() + templateSammelkonto.getShowKontonummer());
                        list.add(valSK);
                        buchhaltungTreeData.addItem(valKA, valSK);
                        if (valSK.getId() == selectId) buchhaltungTree.select(valSK);
                    });
                });
            });
        });
        buchhaltungTree.expand(valBH);
        list.forEach(templateBuchhaltungTreeData -> buchhaltungTree.expand(templateBuchhaltungTreeData));
    }
}
