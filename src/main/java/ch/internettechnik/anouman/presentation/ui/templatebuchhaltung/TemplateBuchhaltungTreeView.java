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
    private Grid<TemplateKontogruppe> kontogruppeGrid = new Grid<>();
    private Grid<TemplateKontoart> kontoartGrid = new Grid<>();
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
    private TemplateKontoartFacade templateKontoartFacade;

    @Inject
    private TemplateKontogruppeFacade templateKontogruppeFacade;

    @Inject
    private TemplateKontoFacade templateKontoFacade;

    @Inject
    private TemplateBuchhaltungForm templateBuchhaltungForm;

    @Inject
    private TemplateKontoklasseForm templateKontoklasseForm;

    @Inject
    private TemplateKontogruppeForm templateKontogruppeForm;

    @Inject
    private TemplateKontoartForm templateKontoartForm;

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
            bodyLayout.removeComponent(kontogruppeGrid);
            bodyLayout.removeComponent(kontoartGrid);
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
                    kontogruppeGrid = createGridTemplateKontogruppe(selectedItem);
                    addGridBtn = createButtonAddTemplateKontogruppe(templateKontoklasseFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontogruppeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontogruppeGrid, 4);
                }
                if (selectedItem.getType().equals("KG")) {
                    kontoartGrid = createGridTemplateKontoart(selectedItem);
                    addGridBtn = createButtonAddTemplateKontoart(templateKontogruppeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontoartGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoartGrid, 4);
                }
                if (selectedItem.getType().equals("KA")) {
                    sammelkontoGrid = createGridTemplateSammelkonto(selectedItem);
                    addGridBtn = createButtonAddTemplateSammelkonto(templateKontoartFacade.findBy(selectedItem.getId()));

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
        //button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
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

    private Button createButtonAddTemplateKontogruppe(TemplateKontoklasse templateKontoklasse) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Kontogruppe");
        button.addClickListener(event -> {
            kontogruppeGrid.asSingleSelect().clear();
            templateKontogruppeForm.setEntity(new TemplateKontogruppe());
            templateKontogruppeForm.openInModalPopup();
            templateKontogruppeForm.setSavedHandler(val -> {
                TemplateKontoklasse kk = templateKontoklasseFacade.findBy(templateKontoklasse.getId());
                val.setTemplateKontoklasse(kk);
                val = templateKontogruppeFacade.save(val);
                updateTree(val.getTemplateKontoklasse().getId());
                kontogruppeGrid.select(val);
                templateKontogruppeForm.closePopup();
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontoart(TemplateKontogruppe templateKontogruppe) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Kontoart");
        button.addClickListener(event -> {
            kontoartGrid.asSingleSelect().clear();
            templateKontoartForm.setEntity(new TemplateKontoart());
            templateKontoartForm.openInModalPopup();
            templateKontoartForm.setSavedHandler(val -> {
                TemplateKontogruppe kg = templateKontogruppeFacade.findBy(templateKontogruppe.getId());
                val.setTemplateKontogruppe(templateKontogruppe);
                val = templateKontoartFacade.save(val);
                updateTree(val.getTemplateKontogruppe().getId());
                kontoartGrid.select(val);
                templateKontoartForm.closePopup();
            });
        });
        return button;
    }


    private Button createButtonAddTemplateSammelkonto(TemplateKontoart templateKontoart) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Sammelkonto");
        button.addClickListener(event -> {
            kontoartGrid.asSingleSelect().clear();
            templateSammelkontoForm.setEntity(new TemplateSammelkonto());
            templateSammelkontoForm.openInModalPopup();
            templateSammelkontoForm.setSavedHandler(val -> {
                TemplateKontogruppe kg = templateKontogruppeFacade.findBy(templateKontoart.getId());
                val.setTemplateKontoart(templateKontoart);
                val = templateSammelkontoFacade.save(val);
                updateTree(val.getTemplateKontoart().getId());
                sammelkontoGrid.select(val);
                templateSammelkontoForm.closePopup();
            });
        });
        return button;
    }


    private Button createButtonAddTemplateKonto(TemplateSammelkonto templateSammelkonto) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Konto");
        button.addClickListener(event -> {
            kontoGrid.asSingleSelect().clear();
            templateKontoForm.setEntity(new TemplateKonto());
            templateKontoForm.openInModalPopup();
            templateKontoForm.setSavedHandler(val -> {
                TemplateSammelkonto sk = templateSammelkontoFacade.findBy(templateSammelkonto.getId());
                val.setTemplateSammelkonto(sk);
                val = templateKontoFacade.save(val);
                updateTree(val.getTemplateSammelkonto().getId());
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

                //@todo Problem mit Version bei Speichern
                grid.setItems(buchhaltung.getTemplateKontoklasses());
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

    private Grid<TemplateKontogruppe> createGridTemplateKontogruppe(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateKontogruppe> binderTemplateKontogruppe = new BeanValidationBinder<>(TemplateKontogruppe.class);

        Grid<TemplateKontogruppe> grid = new Grid<>();
        grid.setCaption("Kontogruppen");
        grid.setSizeFull();
        TemplateKontoklasse kontoklasse = templateKontoklasseFacade.findBy(val.getId());
        grid.setItems(kontoklasse.getTemplateKontogruppes());
        binderTemplateKontogruppe.bind(bezeichnungFld, TemplateKontogruppe::getBezeichnung, TemplateKontogruppe::setBezeichnung);
        binderTemplateKontogruppe.bind(kontonummerFld, TemplateKontogruppe::getKontonummer, TemplateKontogruppe::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKontogruppe);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKontogruppe.isValid()) {
                templateKontogruppeFacade.save(event.getBean());
                grid.setItems(kontoklasse.getTemplateKontogruppes());
            }
        });

        grid.addColumn(TemplateKontogruppe::getId).setCaption("Id");
        grid.addColumn(TemplateKontogruppe::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontogruppe::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKontogruppe kontogruppe = (TemplateKontogruppe) event.getItem();
                    TemplateKontoklasse kontoklasse1 = kontogruppe.getTemplateKontoklasse();
                    Notification.show("Lösche Kontogruppe id:" + kontogruppe.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    kontoklasse1.getTemplateKontogruppes().remove(kontogruppe);
                    kontoklasse1 = templateKontoklasseFacade.save(kontoklasse1);
                    templateKontogruppeFacade.delete(kontogruppe);
                    grid.setItems(kontoklasse.getTemplateKontogruppes());
                    updateTree(kontoklasse1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateKontoart> createGridTemplateKontoart(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateKontoart> binderTemplateKontoart = new BeanValidationBinder<>(TemplateKontoart.class);

        Grid<TemplateKontoart> grid = new Grid<>();
        grid.setCaption("Kontoarten");
        grid.setSizeFull();
        TemplateKontogruppe kontogruppe = templateKontogruppeFacade.findBy(val.getId());
        grid.setItems(kontogruppe.getTemplateKontoarts());

        binderTemplateKontoart.bind(bezeichnungFld, TemplateKontoart::getBezeichnung, TemplateKontoart::setBezeichnung);
        binderTemplateKontoart.bind(kontonummerFld, TemplateKontoart::getKontonummer, TemplateKontoart::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKontoart);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKontoart.isValid()) {
                templateKontoartFacade.save(event.getBean());
                grid.setItems(kontogruppe.getTemplateKontoarts());
            }
        });

        grid.addColumn(TemplateKontoart::getId).setCaption("Id");
        grid.addColumn(TemplateKontoart::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontoart::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKontoart kontoart = (TemplateKontoart) event.getItem();
                    TemplateKontogruppe kontogruppe1 = kontoart.getTemplateKontogruppe();
                    Notification.show("Lösche Kontoart id:" + kontoart.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    kontogruppe1.getTemplateKontoarts().remove(kontoart);
                    kontogruppe1 = templateKontogruppeFacade.save(kontogruppe1);
                    templateKontoartFacade.delete(kontoart);
                    grid.setItems(kontogruppe.getTemplateKontoarts());
                    updateTree(kontogruppe1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateSammelkonto> createGridTemplateSammelkonto(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateSammelkonto> binderTemplateSammelkonto = new BeanValidationBinder<>(TemplateSammelkonto.class);

        Grid<TemplateSammelkonto> grid = new Grid<>();
        grid.setCaption("Sammelkonti");
        grid.setSizeFull();
        TemplateKontoart kontoart = templateKontoartFacade.findBy(val.getId());
        grid.setItems(kontoart.getTemplateSammelkontos());

        binderTemplateSammelkonto.bind(bezeichnungFld, TemplateSammelkonto::getBezeichnung, TemplateSammelkonto::setBezeichnung);
        binderTemplateSammelkonto.bind(kontonummerFld, TemplateSammelkonto::getKontonummer, TemplateSammelkonto::setKontonummer);

        grid.getEditor().setBinder(binderTemplateSammelkonto);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateSammelkonto.isValid()) {
                templateSammelkontoFacade.save(event.getBean());
                grid.setItems(kontoart.getTemplateSammelkontos());
            }
        });

        grid.addColumn(TemplateSammelkonto::getId).setCaption("Id");
        grid.addColumn(TemplateSammelkonto::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateSammelkonto::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(konto -> "ändern",
                new ButtonRenderer(event -> {
                    templateSammelkontoForm.setEntity((TemplateSammelkonto) event.getItem());
                    templateSammelkontoForm.openInModalPopup();
                    templateSammelkontoForm.setSavedHandler(sammelkonto -> {
                        templateSammelkontoFacade.save(sammelkonto);
                        updateTree(sammelkonto.getTemplateKontoart().getId());
                        grid.setItems(kontoart.getTemplateSammelkontos());
                        grid.select(sammelkonto);
                        templateSammelkontoForm.closePopup();
                    });
                    templateSammelkontoForm.setResetHandler(sammelkonto -> {
                        grid.setItems(kontoart.getTemplateSammelkontos());
                        grid.select(sammelkonto);
                        templateSammelkontoForm.closePopup();
                    });
                })).setCaption("ändern");


        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateSammelkonto sammelkonto = (TemplateSammelkonto) event.getItem();
                    TemplateKontoart kontoart1 = sammelkonto.getTemplateKontoart();
                    Notification.show("Lösche Sammelkonto id:" + sammelkonto.getId());
                    kontoart1.getTemplateSammelkontos().remove(sammelkonto);
                    templateSammelkontoFacade.delete(sammelkonto);
                    grid.setItems(kontoart.getTemplateSammelkontos());
                    updateTree(kontoart1.getId());
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
            templateKontoklasse.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                templateKontogruppe.getTemplateKontoarts().stream().forEach(templateKontoart -> {
                    templateKontoart.getTemplateSammelkontos().stream().forEach(templateSammelkonto -> {
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
        kontoklasses.forEach(kontoklasse -> {
            TemplateBuchhaltungTreeData valKK = new TemplateBuchhaltungTreeData(kontoklasse.getId(), "KK", kontoklasse.getBezeichnung() + " KoNr:" + kontoklasse.getShowKontonummer());
            list.add(valKK);
            buchhaltungTreeData.addItem(valBH, valKK);
            if (valKK.getId() == selectId) buchhaltungTree.select(valKK);
            kontoklasse.getTemplateKontogruppes().forEach(kontogruppe -> {
                TemplateBuchhaltungTreeData valKG = new TemplateBuchhaltungTreeData(kontogruppe.getId(), "KG", kontogruppe.getBezeichnung() + " KoNr:" + kontogruppe.getShowKontonummer());
                list.add(valKG);
                buchhaltungTreeData.addItem(valKK, valKG);
                if (valKG.getId() == selectId) buchhaltungTree.select(valKG);
                kontogruppe.getTemplateKontoarts().forEach(kontoart -> {
                    TemplateBuchhaltungTreeData valKA = new TemplateBuchhaltungTreeData(kontoart.getId(), "KA", kontoart.getBezeichnung() + " KoNr:" + kontoart.getShowKontonummer());
                    list.add(valKA);
                    buchhaltungTreeData.addItem(valKG, valKA);
                    if (valKA.getId() == selectId) buchhaltungTree.select(valKA);
                    kontoart.getTemplateSammelkontos().forEach(templateSammelkonto -> {
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
