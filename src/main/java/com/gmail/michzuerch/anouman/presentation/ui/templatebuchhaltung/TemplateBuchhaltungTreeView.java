package com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung;

import com.gmail.michzuerch.anouman.backend.entity.*;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.*;
import com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung.form.*;
import com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung.templatemehrwertsteuercode.TemplateMehrwertsteuercodeForm;
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
import org.vaadin.teemusa.flexlayout.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CDIView("TemplateBuchhaltungTreeView")
public class TemplateBuchhaltungTreeView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungTreeView.class.getName());

    private Tree<TemplateBuchhaltungTreeData> buchhaltungTree = new Tree<>();
    private Grid<TemplateKontoklasse> kontoklasseGrid = new Grid<>();
    private Grid<TemplateKontohauptgruppe> kontohauptgruppeGrid = new Grid<>();
    private Grid<TemplateKontogruppe> kontogruppeGrid = new Grid<>();
    private Grid<TemplateKonto> kontoGrid = new Grid<>();
    private ComboBox<TemplateBuchhaltung> buchhaltungSelect = new ComboBox<>();
    private Button addBuchhaltungBtn = new Button("Erstelle Buchhaltung");
    private Button addGridBtn = new Button("Erstelle Konto");
    private Button mehrwertsteuercodeBtn = new Button("Mehrwertsteuercode");
    private Window windowMehrwertsteuercode;

    private TemplateBuchhaltungTreeData treeRootItem;

    @Inject
    private TemplateBuchhaltungDeltaspikeFacade templateBuchhaltungDeltaspikeFacade;

    @Inject
    private TemplateKontoklasseDeltaspikeFacade templateKontoklasseDeltaspikeFacade;

    @Inject
    private TemplateKontohauptgruppeDeltaspikeFacade templateKontohauptgruppeDeltaspikeFacade;

    @Inject
    private TemplateKontogruppeDeltaspikeFacade templateKontogruppeDeltaspikeFacade;

    @Inject
    private TemplateKontoDeltaspikeFacade templateKontoDeltaspikeFacade;

    @Inject
    private TemplateBuchhaltungForm templateBuchhaltungForm;

    @Inject
    private TemplateKontoklasseForm templateKontoklasseForm;

    @Inject
    private TemplateKontohauptgruppeForm templateKontohauptgruppeForm;

    @Inject
    private TemplateKontogruppeForm templateKontogruppeForm;

    @Inject
    private TemplateKontoForm templateKontoForm;

    @Inject
    private TemplateMehrwertsteuercodeDeltaspikeFacade templateMehrwertsteuercodeDeltaspikeFacade;

    @Inject
    private TemplateMehrwertsteuercodeForm templateMehrwertsteuercodeForm;


    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Row);
        layout.setAlignItems(AlignItems.FlexEnd);
        layout.setJustifyContent(JustifyContent.SpaceBetween);
        layout.setAlignContent(AlignContent.Stretch);
        layout.setFlexWrap(FlexWrap.Wrap);

        HorizontalLayout toolsLayout = new HorizontalLayout();
        HorizontalLayout bodyLayout = new HorizontalLayout();
        buchhaltungSelect = createTemplateBuchhaltungSelect();

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
            bodyLayout.removeComponent(kontoGrid);

            bodyLayout.removeComponent(addGridBtn);
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
                    addGridBtn = createButtonAddTemplateKontohauptgruppe(templateKontoklasseDeltaspikeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontohauptgruppeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontohauptgruppeGrid, 4);
                }
                if (selectedItem.getType().equals("HG")) {
                    kontogruppeGrid = createGridTemplateKontogruppe(selectedItem);
                    addGridBtn = createButtonAddTemplateKontogruppe(templateKontohauptgruppeDeltaspikeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontogruppeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontogruppeGrid, 4);
                }
                if (selectedItem.getType().equals("KG")) {
                    kontoGrid = createGridTemplateKonto(selectedItem);
                    addGridBtn = createButtonAddTemplateKonto(templateKontogruppeDeltaspikeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontoGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoGrid, 4);
                }
                //Notification.show("Selected from Tree:" + selectedItem.getType() + " id:" + selectedItem.getId(), Notification.Type.TRAY_NOTIFICATION);
            }
        });

        layout.addComponents(toolsLayout, bodyLayout);
        layout.setSizeFull();
        return layout;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(createContent());
        setSizeFull();
    }

    private Button createButtonAddTemplateBuchhaltung() {
        Button button = new Button("Erstelle Template Buchhaltung");
        button.setIcon(VaadinIcons.ASTERISK);
        button.addClickListener(event -> {
            templateBuchhaltungForm.setEntity(new TemplateBuchhaltung());
            templateBuchhaltungForm.openInModalPopup();
            templateBuchhaltungForm.setSavedHandler(val -> {
                val = templateBuchhaltungDeltaspikeFacade.save(val);
                buchhaltungSelect.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
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
                TemplateBuchhaltung bh = templateBuchhaltungDeltaspikeFacade.findBy(buchhaltungSelect.getValue().getId());
                val.setTemplateBuchhaltung(bh);
                val = templateKontoklasseDeltaspikeFacade.save(val);
                bh.getTemplateKontoklasses().add(val);
                bh = templateBuchhaltungDeltaspikeFacade.save(bh);
                buchhaltungSelect.setSelectedItem(val.getTemplateBuchhaltung());
                updateTree(val.getTemplateBuchhaltung().getId());
                kontoklasseGrid.setItems(templateBuchhaltungDeltaspikeFacade.findBy(buchhaltungSelect.getValue().getId()).getTemplateKontoklasses());
                kontoklasseGrid.select(val);
                templateKontoklasseForm.closePopup();
                Notification.show("Add Kontoklasse id:" + val.getId());
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
                TemplateKontoklasse kk = templateKontoklasseDeltaspikeFacade.findBy(templateKontoklasse.getId());
                val.setTemplateKontoklasse(kk);
                val = templateKontohauptgruppeDeltaspikeFacade.save(val);
                kk.getTemplateKontohauptgruppes().add(val);
                kk = templateKontoklasseDeltaspikeFacade.save(kk);
                updateTree(val.getTemplateKontoklasse().getId());
                kontohauptgruppeGrid.select(val);
                templateKontohauptgruppeForm.closePopup();
                Notification.show("Add Kontohauptgruppe id:" + val.getId());
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontogruppe(TemplateKontohauptgruppe templateKontohauptgruppe) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Template Kontogruppe");
        button.addClickListener(event -> {
            kontogruppeGrid.asSingleSelect().clear();
            templateKontogruppeForm.setEntity(new TemplateKontogruppe());
            templateKontogruppeForm.openInModalPopup();
            templateKontogruppeForm.setSavedHandler(val -> {
                TemplateKontohauptgruppe hg = templateKontohauptgruppeDeltaspikeFacade.findBy(templateKontohauptgruppe.getId());
                val.setTemplateKontohauptgruppe(hg);
                val = templateKontogruppeDeltaspikeFacade.save(val);
                hg.getTemplateKontogruppes().add(val);
                hg = templateKontohauptgruppeDeltaspikeFacade.save(hg);
                updateTree(val.getTemplateKontohauptgruppe().getId());
                kontogruppeGrid.select(val);
                templateKontogruppeForm.closePopup();
                Notification.show("Add Kontogruppe id:" + val.getId());
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
                TemplateKontogruppe kg = templateKontogruppeDeltaspikeFacade.findBy(templateKontogruppe.getId());
                val.setTemplateKontogruppe(kg);
                val = templateKontoDeltaspikeFacade.save(val);
                kg.getTemplateKontos().add(val);
                kg = templateKontogruppeDeltaspikeFacade.save(kg);
                updateTree(val.getTemplateKontogruppe().getId());
                kontoGrid.select(val);
                templateKontoForm.closePopup();
                Notification.show("Add Konto id:" + val.getId());
            });
        });
        return button;
    }

    private ComboBox<TemplateBuchhaltung> createTemplateBuchhaltungSelect() {
        ComboBox<TemplateBuchhaltung> select = new ComboBox<>();
        Collection<TemplateBuchhaltung> buchhaltungen = templateBuchhaltungDeltaspikeFacade.findAll();
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
        TemplateBuchhaltung buchhaltung = templateBuchhaltungDeltaspikeFacade.findBy(val.getId());
        grid.setItems(buchhaltung.getTemplateKontoklasses());
        binderTemplateKontoklasse.bind(bezeichnungFld, TemplateKontoklasse::getBezeichnung, TemplateKontoklasse::setBezeichnung);
        binderTemplateKontoklasse.bind(kontonummerFld, TemplateKontoklasse::getKontonummer, TemplateKontoklasse::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKontoklasse);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKontoklasse.isValid()) {
                TemplateKontoklasse templateKontoklasse = event.getBean();
                templateKontoklasse = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse);
                grid.setItems(templateKontoklasseDeltaspikeFacade.findByTemplateBuchhaltung(buchhaltung));
                grid.select(templateKontoklasseDeltaspikeFacade.findBy(event.getBean().getId()));
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
                    buchhaltung1 = templateBuchhaltungDeltaspikeFacade.save(buchhaltung1);
                    templateKontoklasseDeltaspikeFacade.delete(kontoklasse);
                    grid.setItems(templateKontoklasseDeltaspikeFacade.findByTemplateBuchhaltung(buchhaltung1));
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
        TemplateKontoklasse kontoklasse = templateKontoklasseDeltaspikeFacade.findBy(val.getId());
        grid.setItems(kontoklasse.getTemplateKontohauptgruppes());
        templateKontohauptgruppeBeanValidationBinder.bind(bezeichnungFld, TemplateKontohauptgruppe::getBezeichnung, TemplateKontohauptgruppe::setBezeichnung);
        templateKontohauptgruppeBeanValidationBinder.bind(kontonummerFld, TemplateKontohauptgruppe::getKontonummer, TemplateKontohauptgruppe::setKontonummer);

        grid.getEditor().setBinder(templateKontohauptgruppeBeanValidationBinder);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (templateKontohauptgruppeBeanValidationBinder.isValid()) {
                TemplateKontohauptgruppe templateKontohauptgruppe = event.getBean();
                templateKontohauptgruppeDeltaspikeFacade.save(event.getBean());
                grid.setItems(templateKontohauptgruppeDeltaspikeFacade.findByTemplateKontoklasse(templateKontohauptgruppe.getTemplateKontoklasse()));
                grid.select(templateKontohauptgruppeDeltaspikeFacade.findBy(event.getBean().getId()));
            }
        });

        grid.addColumn(TemplateKontohauptgruppe::getId).setCaption("Id");
        grid.addColumn(TemplateKontohauptgruppe::getVersion).setCaption("Version");
        grid.addColumn(TemplateKontohauptgruppe::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontohauptgruppe::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKontohauptgruppe templateKontohauptgruppe = (TemplateKontohauptgruppe) event.getItem();
                    TemplateKontoklasse kontoklasse1 = templateKontohauptgruppe.getTemplateKontoklasse();
                    kontoklasse1.getTemplateKontohauptgruppes().remove(templateKontohauptgruppe);
                    kontoklasse1 = templateKontoklasseDeltaspikeFacade.save(kontoklasse1);
                    templateKontohauptgruppeDeltaspikeFacade.delete(templateKontohauptgruppe);
                    grid.setItems(templateKontohauptgruppeDeltaspikeFacade.findByTemplateKontoklasse(kontoklasse1));
                    updateTree(kontoklasse1.getId());
                    Notification.show("Lösche Kontohauptgruppe id:" + templateKontohauptgruppe.getId(), Notification.Type.HUMANIZED_MESSAGE);
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
        TemplateKontohauptgruppe templateKontohauptgruppe = templateKontohauptgruppeDeltaspikeFacade.findBy(val.getId());
        grid.setItems(templateKontohauptgruppe.getTemplateKontogruppes());

        binderTemplateKontogruppe.bind(bezeichnungFld, TemplateKontogruppe::getBezeichnung, TemplateKontogruppe::setBezeichnung);
        binderTemplateKontogruppe.bind(kontonummerFld, TemplateKontogruppe::getKontonummer, TemplateKontogruppe::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKontogruppe);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKontogruppe.isValid()) {
                templateKontogruppeDeltaspikeFacade.save(event.getBean());
                grid.setItems(templateKontogruppeDeltaspikeFacade.findByTemplateKontohauptgruppe(event.getBean().getTemplateKontohauptgruppe()));
                grid.select(templateKontogruppeDeltaspikeFacade.findBy(event.getBean().getId()));
            }
        });

        grid.addColumn(TemplateKontogruppe::getId).setCaption("Id");
        grid.addColumn(TemplateKontogruppe::getVersion).setCaption("Version");
        grid.addColumn(TemplateKontogruppe::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontogruppe::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKontogruppe templateKontogruppe = (TemplateKontogruppe) event.getItem();
                    TemplateKontohauptgruppe templateKontohauptgruppe1 = templateKontogruppe.getTemplateKontohauptgruppe();
                    Notification.show("Lösche Kontohauptgruppe id:" + templateKontogruppe.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    templateKontohauptgruppe1.getTemplateKontogruppes().remove(templateKontogruppe);
                    templateKontohauptgruppe1 = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe1);
                    templateKontogruppeDeltaspikeFacade.delete(templateKontogruppe);
                    grid.setItems(templateKontogruppeDeltaspikeFacade.findByTemplateKontohauptgruppe(templateKontohauptgruppe1));
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
        TemplateKontogruppe templateKontogruppe = templateKontogruppeDeltaspikeFacade.findBy(val.getId());
        grid.setItems(templateKontogruppe.getTemplateKontos());

        binderTemplateKonto.bind(bezeichnungFld, TemplateKonto::getBezeichnung, TemplateKonto::setBezeichnung);
        binderTemplateKonto.bind(kontonummerFld, TemplateKonto::getKontonummer, TemplateKonto::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKonto);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKonto.isValid()) {
                TemplateKonto templateKonto = event.getBean();
                templateKontoDeltaspikeFacade.save(templateKonto);
                TemplateKontogruppe templateKontogruppe1 = templateKontogruppeDeltaspikeFacade.findBy(templateKonto.getTemplateKontogruppe().getId());
                grid.setItems(templateKontoDeltaspikeFacade.findByTemplateKontogruppe(templateKontogruppe1));
                grid.select(templateKontoDeltaspikeFacade.findBy(event.getBean().getId()));
            }
        });

        grid.addColumn(TemplateKonto::getId).setCaption("Id");
        grid.addColumn(TemplateKonto::getVersion).setCaption("Version");
        grid.addColumn(TemplateKonto::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKonto::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(konto -> "ändern",
                new ButtonRenderer(event -> {
                    templateKontoForm.setEntity((TemplateKonto) event.getItem());
                    templateKontoForm.openInModalPopup();
                    templateKontoForm.setSavedHandler(konto -> {
                        templateKontoDeltaspikeFacade.save(konto);
                        TemplateKontogruppe templateKontogruppe1 = konto.getTemplateKontogruppe();
                        updateTree(templateKontogruppe1.getId());
                        grid.setItems(templateKontogruppe1.getTemplateKontos());
                        grid.select(konto);
                        templateKontoForm.closePopup();
                    });
                    templateKontoForm.setResetHandler(konto -> {
                        grid.setItems(templateKontogruppe.getTemplateKontos());
                        grid.select(konto);
                        templateKontoForm.closePopup();
                    });
                })).setCaption("ändern");


        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateKonto konto = (TemplateKonto) event.getItem();
                    Notification.show("Lösche Konto id:" + konto.getId());
                    TemplateKontogruppe templateKontogruppe1 = konto.getTemplateKontogruppe();
                    templateKontogruppe1.getTemplateKontos().remove(konto);
                    templateKontogruppe1 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe1);
                    templateKontoDeltaspikeFacade.delete(konto);
                    grid.setItems(templateKontoDeltaspikeFacade.findByTemplateKontogruppe(templateKontogruppe1));
                    updateTree(templateKontogruppe1.getId());
                })
        ).setCaption("Löschen");

        return grid;
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

    private Window createTemplateMehrwertsteuerWindow(TemplateBuchhaltung buchhaltung) {
        TemplateBuchhaltung bh = templateBuchhaltungDeltaspikeFacade.findBy(buchhaltung.getId());
        HorizontalLayout layout = new HorizontalLayout();

        Grid<TemplateMehrwertsteuercode> grid = new Grid<>();

        grid.setCaption("Mehrwertsteuercodes");
        grid.setItems(bh.getTemplateMehrwertsteuercodes());
        grid.getEditor().setEnabled(true);

        grid.getEditor().addSaveListener(event -> {
            if (grid.getEditor().getBinder().isValid()) {
                templateMehrwertsteuercodeDeltaspikeFacade.save(event.getBean());
                grid.setItems(getMehrwertsteuerList());
            }
        });

        grid.addColumn(TemplateMehrwertsteuercode::getId).setCaption("Id");
        grid.addColumn(TemplateMehrwertsteuercode::getVersion).setCaption("Vers");
        grid.addColumn(TemplateMehrwertsteuercode::getCode).setEditorComponent(new TextField(), TemplateMehrwertsteuercode::setCode).setCaption("Code");
        grid.addColumn(TemplateMehrwertsteuercode::getBezeichnung).setEditorComponent(new TextField(), TemplateMehrwertsteuercode::setBezeichnung).setCaption("Bezeichnung");
        //@todo Editor NumberField mit Double (Siehe AdresseForm mit Konfiguration Numberfield
        //grid.addColumn(TemplateMehrwertsteuercode::getProzent).setEditorComponent(new NumberField(), TemplateMehrwertsteuercode::setProzent).setCaption("Prozent");
        grid.addColumn(TemplateMehrwertsteuercode::isVerkauf).setEditorComponent(new CheckBox(), TemplateMehrwertsteuercode::setVerkauf).setCaption("Verkauf");
        grid.addColumn(templateMehrwertsteuercode -> templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getBezeichnung()
                + " " + templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getId()).setCaption("Konto");
        grid.addColumn(templateMehrwertsteuercode -> templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getShowKontonummer()).setCaption("KoNr");
        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateMehrwertsteuercode mehrwertsteuercode = (TemplateMehrwertsteuercode) event.getItem();
                    mehrwertsteuercode = templateMehrwertsteuercodeDeltaspikeFacade.findBy(mehrwertsteuercode.getId());
                    TemplateBuchhaltung buchhaltung1 = mehrwertsteuercode.getTemplateBuchhaltung();
                    buchhaltung1 = templateBuchhaltungDeltaspikeFacade.findBy(buchhaltung1.getId());
                    buchhaltung1.getTemplateMehrwertsteuercodes().remove(mehrwertsteuercode);
                    buchhaltung1 = templateBuchhaltungDeltaspikeFacade.save(buchhaltung1);

                    Notification.show("Löschen Template Mehrwertsteuercode id:" + mehrwertsteuercode.getId());
                    templateMehrwertsteuercodeDeltaspikeFacade.delete(mehrwertsteuercode);
                    grid.setItems(templateMehrwertsteuercodeDeltaspikeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue()));
                }));
        grid.addColumn(event -> "ändern",
                new ButtonRenderer(event -> {
                    TemplateMehrwertsteuercode mehrwertsteuercode = (TemplateMehrwertsteuercode) event.getItem();
                    mehrwertsteuercode = templateMehrwertsteuercodeDeltaspikeFacade.findBy(mehrwertsteuercode.getId());
                    templateMehrwertsteuercodeForm.setEntity(mehrwertsteuercode);
                    templateMehrwertsteuercodeForm.lockSelect();
                    templateMehrwertsteuercodeForm.openInModalPopup();
                    templateMehrwertsteuercodeForm.setSavedHandler(val -> {
                        templateMehrwertsteuercodeDeltaspikeFacade.save(val);
                        grid.setItems(templateMehrwertsteuercodeDeltaspikeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue()));
                        grid.select(val);
                        templateMehrwertsteuercodeForm.closePopup();
                    });
                    templateMehrwertsteuercodeForm.setResetHandler(val -> {
                        grid.setItems(templateMehrwertsteuercodeDeltaspikeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue()));
                        grid.select(val);
                        templateMehrwertsteuercodeForm.closePopup();
                    });

                }));

        Button mehrwertsteuerAddBtn = new Button("Add Mehrwertsteuercode", event -> {
            grid.asSingleSelect().clear();
            TemplateMehrwertsteuercode mehrwertsteuercode = new TemplateMehrwertsteuercode();
            mehrwertsteuercode.setTemplateBuchhaltung(buchhaltungSelect.getValue());
            mehrwertsteuercode.setTemplateMehrwertsteuerKonto(createTemplateKontoList(buchhaltungSelect.getValue()).get(0));

            mehrwertsteuercode.setProzent(8d);

            templateMehrwertsteuercodeForm.setWidth(500, Unit.PIXELS);
            templateMehrwertsteuercodeForm.lockSelect();
            templateMehrwertsteuercodeForm.setEntity(mehrwertsteuercode);
            templateMehrwertsteuercodeForm.openInModalPopup();
            templateMehrwertsteuercodeForm.setSavedHandler(templateMehrwertsteuercode -> {
                templateMehrwertsteuercodeDeltaspikeFacade.save(templateMehrwertsteuercode);
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
        return templateMehrwertsteuercodeDeltaspikeFacade.findByTemplateBuchhaltung(buchhaltungSelect.getValue());
    }


    private void updateTree(Long selectId) {
        TemplateBuchhaltung buchhaltung = templateBuchhaltungDeltaspikeFacade.findBy(buchhaltungSelect.getValue().getId());
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
                TemplateBuchhaltungTreeData valHG = new TemplateBuchhaltungTreeData(templateKontohauptgruppe.getId(), "HG", templateKontohauptgruppe.getBezeichnung() + " KoNr:" + templateKontohauptgruppe.getShowKontonummer());
                list.add(valHG);
                buchhaltungTreeData.addItem(valKK, valHG);
                if (valHG.getId() == selectId) buchhaltungTree.select(valHG);
                templateKontohauptgruppe.getTemplateKontogruppes().forEach(templateKontogruppe -> {
                    TemplateBuchhaltungTreeData valKG = new TemplateBuchhaltungTreeData(templateKontogruppe.getId(), "KG", templateKontogruppe.getBezeichnung() + " KoNr:" + templateKontogruppe.getShowKontonummer());
                    list.add(valKG);
                    buchhaltungTreeData.addItem(valHG, valKG);
                    if (valKG.getId() == selectId) buchhaltungTree.select(valKG);
                });
            });
        });
        buchhaltungTree.expand(valBH);
        list.forEach(templateBuchhaltungTreeData -> buchhaltungTree.expand(templateBuchhaltungTreeData));
    }
}
