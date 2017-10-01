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
import java.util.Collection;

@CDIView(value = "TemplateBuchhaltungTree")
public class TemplateBuchhaltungTreeView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungTreeView.class.getName());

    private Tree<TemplateBuchhaltungTreeData> buchhaltungTree = new Tree<>();
    private Grid<TemplateKontoklasse> kontoklasseGrid = new Grid<>();
    private Grid<TemplateKontogruppe> kontogruppeGrid = new Grid<>();
    private Grid<TemplateKontoart> kontoartGrid = new Grid<>();
    private Grid<TemplateKonto> kontoGrid = new Grid<>();
    private Grid<TemplateMehrwertsteuercode> mehrwertsteuercodeGrid = new Grid<>();
    private ComboBox<TemplateBuchhaltung> buchhaltungSelect = new ComboBox<>();
    private Button addBuchhaltungBtn = new Button("Erstelle Buchhaltung");
    private Button addGridBtn = new Button("Erstelle Konto");
    private Button mehrwertsteuercodeBtn = new Button("Mehrwertsteuercode");
    private PopupView popupMehrwertsteuercode;

    private TemplateBuchhaltungTreeData treeRootItem;

    @Inject
    private Menu menu;

    @Inject
    private TemplateBuchhaltungFacade templateBuchhaltungFacade;

    @Inject
    private TemplateKontoklasseFacade templateKontoklasseFacade;

    @Inject
    private TemplateKontogruppeFacade templateKontogruppeFacade;

    @Inject
    private TemplateKontoartFacade templateKontoartFacade;

    @Inject
    private TemplateKontoFacade templateKontoFacade;

    @Inject
    private TemplateMehrwertsteuercodeFacade templateMehrwertsteuercodeFacade;

    @Inject
    private TemplateBuchhaltungForm templateBuchhaltungForm;

    @Inject
    private TemplateKontoklasseForm templateKontoklasseForm;

    @Inject
    private TemplateKontogruppeForm templateKontogruppeForm;

    @Inject
    private TemplateKontoartForm templateKontoartForm;

    @Inject
    private TemplateKontoForm templateKontoForm;

    @Inject
    private TemplateMehrwertsteuercodeForm templateMehrwertsteuercodeForm;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");
        HorizontalLayout toolsLayout = new HorizontalLayout();

        HorizontalLayout bodyLayout = new HorizontalLayout();

        buchhaltungSelect = createBuchhaltungSelect();

        addBuchhaltungBtn = createButtonAddTemplateBuchhaltung();
        popupMehrwertsteuercode = new PopupView("Mwst", createMehrwertsteuerPopup());
        toolsLayout.addComponentsAndExpand(buchhaltungSelect);
        toolsLayout.addComponents(addBuchhaltungBtn, mehrwertsteuercodeBtn, popupMehrwertsteuercode);


        toolsLayout.setWidth(50, Unit.PERCENTAGE);
        addBuchhaltungBtn.setIcon(VaadinIcons.CAMERA);
        buchhaltungTree = createBuchhaltungTree();
        buchhaltungTree.select(treeRootItem);

        bodyLayout.addComponent(buchhaltungTree);
        bodyLayout.setExpandRatio(buchhaltungTree, 0);
        bodyLayout.setSizeFull();

        addGridBtn = createButtonAddTemplateKontoklasse();
        kontoklasseGrid = createGridKontoklasse(buchhaltungTree.asSingleSelect().getValue());
        bodyLayout.addComponentsAndExpand(addGridBtn, kontoklasseGrid);
        bodyLayout.setExpandRatio(addGridBtn, 0.1f);
        bodyLayout.setExpandRatio(kontoklasseGrid, 4);

        mehrwertsteuercodeBtn.addClickListener(event -> {
            popupMehrwertsteuercode.setPopupVisible(true);
        });

        buchhaltungTree.setSelectionMode(Grid.SelectionMode.SINGLE);

        buchhaltungSelect.addValueChangeListener(event -> {
            buchhaltungTree.setDataProvider(updateProvider(event.getValue()));
            if (!(treeRootItem == null)) {
                buchhaltungTree.select(treeRootItem);
                buchhaltungTree.expand(treeRootItem);
            }
        });

        buchhaltungTree.addSelectionListener(event -> {
            bodyLayout.removeComponent(kontoklasseGrid);
            bodyLayout.removeComponent(kontogruppeGrid);
            bodyLayout.removeComponent(kontoartGrid);
            bodyLayout.removeComponent(kontoGrid);
            bodyLayout.removeComponent(addGridBtn);
            bodyLayout.removeComponent(mehrwertsteuercodeGrid);
            mehrwertsteuercodeGrid = createMehrwertsteuercodeGrid(buchhaltungSelect.getValue());
            if (!buchhaltungTree.asSingleSelect().isEmpty()) {
                TemplateBuchhaltungTreeData selectedItem = buchhaltungTree.asSingleSelect().getValue();
                buchhaltungTree.expand(selectedItem);
                if (selectedItem.getType().equals("BH")) {
                    kontoklasseGrid = createGridKontoklasse(selectedItem);
                    addGridBtn = createButtonAddTemplateKontoklasse();

                    bodyLayout.addComponents(addGridBtn, kontoklasseGrid, mehrwertsteuercodeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoklasseGrid, 4);
                    bodyLayout.setExpandRatio(mehrwertsteuercodeGrid, 1);
                }
                if (selectedItem.getType().equals("KK")) {
                    kontogruppeGrid = createGridKontogruppe(selectedItem);
                    addGridBtn = createButtonAddTemplateKontogruppe(templateKontoklasseFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontogruppeGrid, mehrwertsteuercodeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontogruppeGrid, 4);
                    bodyLayout.setExpandRatio(mehrwertsteuercodeGrid, 1);
                }
                if (selectedItem.getType().equals("KG")) {
                    kontoartGrid = createGridKontoart(selectedItem);
                    addGridBtn = createButtonAddTemplateKontoart(templateKontogruppeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontoartGrid, mehrwertsteuercodeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoartGrid, 4);
                    bodyLayout.setExpandRatio(mehrwertsteuercodeGrid, 1);
                }
                if (selectedItem.getType().equals("KA")) {
                    kontoGrid = createGridKonto(selectedItem);
                    addGridBtn = createButtonAddTemplateKonto(templateKontoartFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontoGrid, mehrwertsteuercodeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoGrid, 4);
                    bodyLayout.setExpandRatio(mehrwertsteuercodeGrid, 1);
                }
                //Notification.show("Selected from Tree:" + selectedItem.getType() + " id:" + selectedItem.getId(), Notification.Type.TRAY_NOTIFICATION);
            }
        });

        addComponents(menu, toolsLayout);
        addComponentsAndExpand(bodyLayout);
        bodyLayout.setExpandRatio(buchhaltungTree, 1);

    }

    private Button createButtonAddTemplateBuchhaltung() {
        Button button = new Button("Erstelle Template Buchhaltung");
        button.setIcon(VaadinIcons.ASTERISK);
        //button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.addClickListener(event -> {
            templateBuchhaltungForm.setEntity(new TemplateBuchhaltung());
            templateBuchhaltungForm.openInModalPopup();
            templateBuchhaltungForm.setSavedHandler(val -> {
                templateBuchhaltungFacade.save(val);
                TemplateBuchhaltung templateBuchhaltung = templateBuchhaltungFacade.findBy(val.getId());
                buchhaltungSelect.setItems(templateBuchhaltungFacade.findAll());
                buchhaltungSelect.setSelectedItem(val);
                buchhaltungTree.setDataProvider(updateProvider(val));
                templateBuchhaltungForm.closePopup();
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontoklasse() {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.addClickListener(event -> {
            kontoklasseGrid.asSingleSelect().clear();
            templateKontoklasseForm.setEntity(new TemplateKontoklasse());
            templateKontoklasseForm.openInModalPopup();
            templateKontoklasseForm.setSavedHandler(val -> {
                val.setTemplateBuchhaltung(buchhaltungSelect.getValue());
                templateKontoklasseFacade.save(val);
                buchhaltungTree.setDataProvider(updateProvider(buchhaltungSelect.getValue()));

                kontoklasseGrid.setItems(val.getTemplateBuchhaltung().getTemplateKontoklasses());
                kontoklasseGrid.select(val);
                templateKontoklasseForm.closePopup();
                Notification.show("Add Kontoklasse " + val.getId(), Notification.Type.TRAY_NOTIFICATION);
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontoart(TemplateKontogruppe templateKontogruppe) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.addClickListener(event -> {
            kontoartGrid.asSingleSelect().clear();
            templateKontoartForm.setEntity(new TemplateKontoart());
            templateKontoartForm.openInModalPopup();
            templateKontoartForm.setSavedHandler(val -> {
                val.setTemplateKontogruppe(templateKontogruppe);
                templateKontoartFacade.save(val);
                buchhaltungTree.setDataProvider(updateProvider(buchhaltungSelect.getValue()));
                kontoartGrid.select(val);
                templateKontoartForm.closePopup();
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKontogruppe(TemplateKontoklasse templateKontoklasse) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.addClickListener(event -> {
            kontogruppeGrid.asSingleSelect().clear();
            templateKontogruppeForm.setEntity(new TemplateKontogruppe());
            templateKontogruppeForm.openInModalPopup();
            templateKontogruppeForm.setSavedHandler(val -> {
                val.setTemplateKontoklasse(templateKontoklasse);
                templateKontogruppeFacade.save(val);
                buchhaltungTree.setDataProvider(updateProvider(buchhaltungSelect.getValue()));
                kontogruppeGrid.select(val);
                templateKontogruppeForm.closePopup();
            });
        });
        return button;
    }

    private Button createButtonAddTemplateKonto(TemplateKontoart templateKontoart) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.addClickListener(event -> {
            kontoGrid.asSingleSelect().clear();
            templateKontoForm.setEntity(new TemplateKonto());
            templateKontoForm.openInModalPopup();
            templateKontoForm.setSavedHandler(val -> {
                val.setTemplateKontoart(templateKontoart);
                templateKontoFacade.save(val);
                buchhaltungTree.setDataProvider(updateProvider(buchhaltungSelect.getValue()));
                kontoGrid.select(val);
                templateKontoForm.closePopup();
            });
        });
        return button;
    }

    private ComboBox<TemplateBuchhaltung> createBuchhaltungSelect() {
        ComboBox<TemplateBuchhaltung> select = new ComboBox<>();
        Collection<TemplateBuchhaltung> buchhaltungen = templateBuchhaltungFacade.findAll();
        select.setEmptySelectionAllowed(false);
        select.setItemCaptionGenerator(item -> item.getBezeichnung() + " id:" + item.getId());
        select.setItems(buchhaltungen);
        select.setSelectedItem(buchhaltungen.iterator().next());
        return select;
    }

    private Grid<TemplateKontoklasse> createGridKontoklasse(TemplateBuchhaltungTreeData val) {
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
                templateKontoklasseFacade.save(event.getBean());
                grid.setItems(buchhaltung.getTemplateKontoklasses());
            }
        });

        grid.addColumn(TemplateKontoklasse::getId).setCaption("Id");
        grid.addColumn(TemplateKontoklasse::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKontoklasse::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Kontoklasse id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    templateKontoklasseFacade.delete((TemplateKontoklasse) event.getItem());
                    grid.setItems(buchhaltung.getTemplateKontoklasses());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateKontogruppe> createGridKontogruppe(TemplateBuchhaltungTreeData val) {
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
                    Notification.show("Lösche Kontogruppe id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    templateKontogruppeFacade.delete((TemplateKontogruppe) event.getItem());
                    grid.setItems(kontoklasse.getTemplateKontogruppes());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateKontoart> createGridKontoart(TemplateBuchhaltungTreeData val) {
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
                    Notification.show("Lösche Kontoart id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    templateKontoartFacade.delete((TemplateKontoart) event.getItem());
                    grid.setItems(kontogruppe.getTemplateKontoarts());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateKonto> createGridKonto(TemplateBuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<TemplateKonto> binderTemplateKonto = new BeanValidationBinder<>(TemplateKonto.class);

        Grid<TemplateKonto> grid = new Grid<>();
        grid.setCaption("Konti");
        grid.setSizeFull();
        TemplateKontoart kontoart = templateKontoartFacade.findBy(val.getId());
        grid.setItems(kontoart.getTemplateKontos());

        binderTemplateKonto.bind(bezeichnungFld, TemplateKonto::getBezeichnung, TemplateKonto::setBezeichnung);
        binderTemplateKonto.bind(kontonummerFld, TemplateKonto::getKontonummer, TemplateKonto::setKontonummer);

        grid.getEditor().setBinder(binderTemplateKonto);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binderTemplateKonto.isValid()) {
                templateKontoFacade.save(event.getBean());
                grid.setItems(kontoart.getTemplateKontos());
            }
        });

        grid.addColumn(TemplateKonto::getId).setCaption("Id");
        grid.addColumn(TemplateKonto::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(TemplateKonto::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Kontoklasse id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    templateKontoklasseFacade.delete((TemplateKontoklasse) event.getItem());
                    grid.setItems(kontoart.getTemplateKontos());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<TemplateMehrwertsteuercode> createMehrwertsteuercodeGrid(TemplateBuchhaltung buchhaltung) {
        Grid<TemplateMehrwertsteuercode> grid = new Grid<>();
        grid.setCaption("Mehrwertsteuercodes");
        grid.setItems(buchhaltung.getTemplateMehrwertsteuercodes());
        grid.addColumn(TemplateMehrwertsteuercode::getCode);
        grid.addColumn(TemplateMehrwertsteuercode::getBezeichnung);
        grid.addColumn(TemplateMehrwertsteuercode::getProzent);
        return grid;
    }

    private VerticalLayout createMehrwertsteuerPopup() {
        VerticalLayout layout = new VerticalLayout();
        Grid<TemplateMehrwertsteuercode> grid = createMehrwertsteuercodeGrid(buchhaltungSelect.getValue());
        layout.addComponent(grid);
        return layout;
    }

    private Tree<TemplateBuchhaltungTreeData> createBuchhaltungTree() {
        Tree<TemplateBuchhaltungTreeData> tree = new Tree<>();

        TemplateBuchhaltung buchhaltung;

        if (!buchhaltungSelect.getSelectedItem().isPresent()) {
            buchhaltungSelect.setSelectedItem(templateBuchhaltungFacade.findAll().get(0));
        }

        buchhaltung = buchhaltungSelect.getSelectedItem().get();
        tree.setDataProvider(updateProvider(buchhaltung));

        return tree;
    }

    private TreeDataProvider<TemplateBuchhaltungTreeData> updateProvider(TemplateBuchhaltung buchhaltung) {
        Collection<TemplateKontoklasse> kontoklasses = buchhaltung.getTemplateKontoklasses();
        TreeData<TemplateBuchhaltungTreeData> buchhaltungTreeData = new TreeData<>();
        TreeDataProvider<TemplateBuchhaltungTreeData> provider = new TreeDataProvider<>(buchhaltungTreeData);
        buchhaltungTreeData.clear();

        TemplateBuchhaltungTreeData valBH = new TemplateBuchhaltungTreeData(buchhaltung.getId(), "BH", buchhaltung.getBezeichnung() + " id:" + buchhaltung.getId());
        treeRootItem = valBH;
        buchhaltungTreeData.addItem(null, valBH);
        kontoklasses.forEach(kontoklasse -> {
            TemplateBuchhaltungTreeData valKK = new TemplateBuchhaltungTreeData(kontoklasse.getId(), "KK", kontoklasse.getBezeichnung() + " KoNr:" + kontoklasse.getShowKontonummer());
            buchhaltungTreeData.addItem(valBH, valKK);
            kontoklasse.getTemplateKontogruppes().forEach(kontogruppe -> {
                TemplateBuchhaltungTreeData valKG = new TemplateBuchhaltungTreeData(kontogruppe.getId(), "KG", kontogruppe.getBezeichnung() + " KoNr:" + kontogruppe.getShowKontonummer());
                buchhaltungTreeData.addItem(valKK, valKG);
                kontogruppe.getTemplateKontoarts().forEach(kontoart -> {
                    TemplateBuchhaltungTreeData valKA = new TemplateBuchhaltungTreeData(kontoart.getId(), "KA", kontoart.getBezeichnung() + " KoNr:" + kontoart.getShowKontonummer());
                    buchhaltungTreeData.addItem(valKG, valKA);
                    /*

                    Keine Konten mehr im Tree weil diese schon im Grid angezeigt werden
                    kontoart.getTemplateKontos().forEach(konto -> {
                        TemplateBuchhaltungTreeData valKO = new TemplateBuchhaltungTreeData(konto.getId(), "KO", konto.getBezeichnung() + " KoNr:" + konto.getShowKontonummer());
                        buchhaltungTreeData.addItem(valKA, valKO);
                    });
                     */
                });
            });
        });
        provider.refreshAll();
        return provider;
    }
}
