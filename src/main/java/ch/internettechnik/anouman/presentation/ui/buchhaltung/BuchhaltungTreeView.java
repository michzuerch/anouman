package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.buchhaltung.form.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CDIView(value = "BuchhaltungTree")
public class BuchhaltungTreeView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BuchhaltungTreeView.class.getName());

    private Tree<BuchhaltungTreeData> buchhaltungTree = new Tree<>();
    private Grid<Kontoklasse> kontoklasseGrid = new Grid<>();
    private Grid<Kontohauptgruppe> kontohauptgruppeGrid = new Grid<>();
    private Grid<Kontogruppe> kontogruppeGrid = new Grid<>();
    private Grid<Konto> kontoGrid = new Grid<>();
    private ComboBox<Buchhaltung> buchhaltungSelect = new ComboBox<>();
    private Button addBuchhaltungBtn = new Button("Erstelle Buchhaltung");
    private Button addGridBtn = new Button("Erstelle Konto");
    private Button mehrwertsteuercodeBtn = new Button("Mehrwertsteuercode");
    private Window windowMehrwertsteuercode;

    private BuchhaltungTreeData treeRootItem;

    @Inject
    private Menu menu;

    @Inject
    private BuchhaltungFacade buchhaltungFacade;

    @Inject
    private KontoklasseFacade kontoklasseFacade;

    @Inject
    private KontohauptgruppeFacade kontohauptgruppeFacade;

    @Inject
    private KontogruppeFacade kontogruppeFacade;

    @Inject
    private KontoFacade kontoFacade;

    @Inject
    private BuchhaltungForm buchhaltungForm;

    @Inject
    private KontoklasseForm kontoklasseForm;

    @Inject
    private KontohauptgruppeForm kontohauptgruppeForm;

    @Inject
    private KontogruppeForm kontogruppeForm;

    @Inject
    private KontoForm kontoForm;

    @Inject
    private MehrwertsteuercodeFacade mehrwertsteuercodeFacade;

    @Inject
    private MehrwertsteuercodeForm mehrwertsteuercodeForm;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        buchhaltungSelect = createBuchhaltungSelect();
        buchhaltungSelect.setWidth(30, Unit.PERCENTAGE);
        if (viewChangeEvent.getParameters() != null) {
            String[] msgs = viewChangeEvent.getParameters().split("/");
            String target = new String();
            Long id = new Long(0);
            for (String msg : msgs) {
                if (target.isEmpty()) {
                    target = msg;
                } else {
                    id = Long.valueOf(msg);
                }
            }
            if (target.equals("id")) {
                buchhaltungSelect.setSelectedItem(buchhaltungFacade.findBy(id));
            }
        }

        setStyleName("anouman-background");
        HorizontalLayout toolsLayout = new HorizontalLayout();
        HorizontalLayout bodyLayout = new HorizontalLayout();

        addBuchhaltungBtn = createButtonAddBuchhaltung();
        windowMehrwertsteuercode = createMehrwertsteuerWindow(buchhaltungSelect.getValue());
        toolsLayout.addComponentsAndExpand(buchhaltungSelect);
        toolsLayout.addComponents(addBuchhaltungBtn, mehrwertsteuercodeBtn);

        addBuchhaltungBtn.setIcon(VaadinIcons.CAMERA);
        Panel buchhaltungPanel = new Panel("Buchhaltung");
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

        addGridBtn = createButtonAddKontoklasse();
        kontoklasseGrid = createGridKontoklasse(buchhaltungTree.asSingleSelect().getValue());
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
            windowMehrwertsteuercode = createMehrwertsteuerWindow(buchhaltungSelect.getValue());
        });

        buchhaltungTree.addSelectionListener(event -> {
            bodyLayout.removeComponent(kontoklasseGrid);
            bodyLayout.removeComponent(kontohauptgruppeGrid);
            bodyLayout.removeComponent(kontogruppeGrid);
            bodyLayout.removeComponent(kontoGrid);

            bodyLayout.removeComponent(addGridBtn);
            windowMehrwertsteuercode = createMehrwertsteuerWindow(buchhaltungSelect.getValue());
            if (!buchhaltungTree.asSingleSelect().isEmpty()) {
                BuchhaltungTreeData selectedItem = buchhaltungTree.asSingleSelect().getValue();
                buchhaltungTree.expand(selectedItem);
                if (selectedItem.getType().equals("BH")) {
                    kontoklasseGrid = createGridKontoklasse(selectedItem);
                    addGridBtn = createButtonAddKontoklasse();

                    bodyLayout.addComponents(addGridBtn, kontoklasseGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontoklasseGrid, 4);
                }
                if (selectedItem.getType().equals("KK")) {
                    kontohauptgruppeGrid = createGridKontohauptgruppe(selectedItem);
                    addGridBtn = createButtonAddKontohauptgruppe(kontoklasseFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontohauptgruppeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontohauptgruppeGrid, 4);
                }
                if (selectedItem.getType().equals("HG")) {
                    kontogruppeGrid = createGridKontogruppe(selectedItem);
                    addGridBtn = createButtonAddKontogruppe(kontohauptgruppeFacade.findBy(selectedItem.getId()));

                    bodyLayout.addComponents(addGridBtn, kontogruppeGrid);
                    bodyLayout.setExpandRatio(addGridBtn, 0.1f);
                    bodyLayout.setExpandRatio(kontogruppeGrid, 4);
                }
                if (selectedItem.getType().equals("KG")) {
                    kontoGrid = createGridKonto(selectedItem);
                    addGridBtn = createButtonAddKonto(kontogruppeFacade.findBy(selectedItem.getId()));

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

    private Button createButtonAddBuchhaltung() {
        Button button = new Button("Erstelle Buchhaltung");
        button.setIcon(VaadinIcons.ASTERISK);
        button.addClickListener(event -> {
            Buchhaltung buchhaltung = new Buchhaltung();
            LocalDate currentDate = LocalDate.now();
            buchhaltung.setJahr(currentDate.getYear());

            buchhaltungForm.setEntity(buchhaltung);
            buchhaltungForm.openInModalPopup();
            buchhaltungForm.setSavedHandler(val -> {
                val = buchhaltungFacade.save(val);
                buchhaltungSelect.setItems(buchhaltungFacade.findAll());
                buchhaltungSelect.setSelectedItem(val);
                buchhaltungForm.closePopup();
            });
        });
        return button;
    }

    private Button createButtonAddKontoklasse() {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Kontoklasse");
        button.addClickListener(event -> {
            kontoklasseGrid.asSingleSelect().clear();
            kontoklasseForm.setEntity(new Kontoklasse());
            kontoklasseForm.openInModalPopup();
            kontoklasseForm.setSavedHandler(val -> {
                Buchhaltung bh = buchhaltungFacade.findBy(buchhaltungSelect.getValue().getId());
                val.setBuchhaltung(bh);
                val = kontoklasseFacade.save(val);
                bh.getKontoklasse().add(val);
                bh = buchhaltungFacade.save(bh);
                buchhaltungSelect.setSelectedItem(val.getBuchhaltung());
                updateTree(val.getBuchhaltung().getId());
                kontoklasseGrid.setItems(buchhaltungFacade.findBy(buchhaltungSelect.getValue().getId()).getKontoklasse());
                kontoklasseGrid.select(val);
                kontoklasseForm.closePopup();
                Notification.show("Add Kontoklasse id:" + val.getId());
            });
        });
        return button;
    }

    private Button createButtonAddKontohauptgruppe(Kontoklasse kontoklasse) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Kontohauptgruppe");
        button.addClickListener(event -> {
            kontohauptgruppeGrid.asSingleSelect().clear();
            kontohauptgruppeForm.setEntity(new Kontohauptgruppe());
            kontohauptgruppeForm.openInModalPopup();
            kontohauptgruppeForm.setSavedHandler(val -> {
                Kontoklasse kk = kontoklasseFacade.findBy(kontoklasse.getId());
                val.setKontoklasse(kk);
                val = kontohauptgruppeFacade.save(val);
                kk.getKontohauptgruppes().add(val);
                kk = kontoklasseFacade.save(kk);
                updateTree(val.getKontoklasse().getId());
                kontohauptgruppeGrid.select(val);
                kontohauptgruppeForm.closePopup();
                Notification.show("Add Kontohauptgruppe id:" + val.getId());
            });
        });
        return button;
    }

    private Button createButtonAddKontogruppe(Kontohauptgruppe kontohauptgruppe) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Kontogruppe");
        button.addClickListener(event -> {
            kontogruppeGrid.asSingleSelect().clear();
            kontogruppeForm.setEntity(new Kontogruppe());
            kontogruppeForm.openInModalPopup();
            kontogruppeForm.setSavedHandler(val -> {
                Kontohauptgruppe hg = kontohauptgruppeFacade.findBy(kontohauptgruppe.getId());
                val.setKontohauptgruppe(hg);
                val = kontogruppeFacade.save(val);
                hg.getKontogruppes().add(val);
                hg = kontohauptgruppeFacade.save(hg);
                updateTree(val.getKontohauptgruppe().getId());
                kontogruppeGrid.select(val);
                kontogruppeForm.closePopup();
                Notification.show("Add Kontogruppe id:" + val.getId());
            });
        });
        return button;
    }


    private Button createButtonAddKonto(Kontogruppe kontogruppe) {
        Button button = new Button();
        button.setIcon(VaadinIcons.ASTERISK);
        button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        button.setDescription("Erstelle Konto");
        button.addClickListener(event -> {
            kontoGrid.asSingleSelect().clear();
            kontoForm.setEntity(new Konto());
            kontoForm.openInModalPopup();
            kontoForm.setSavedHandler(val -> {
                Kontogruppe kg = kontogruppeFacade.findBy(kontogruppe.getId());
                val.setKontogruppe(kg);
                val = kontoFacade.save(val);
                kg.getKontos().add(val);
                kg = kontogruppeFacade.save(kg);
                updateTree(val.getKontogruppe().getId());
                kontoGrid.select(val);
                kontoForm.closePopup();
                Notification.show("Add Konto id:" + val.getId());
            });
        });
        return button;
    }

    private ComboBox<Buchhaltung> createBuchhaltungSelect() {
        ComboBox<Buchhaltung> select = new ComboBox<>();
        Collection<Buchhaltung> buchhaltungen = buchhaltungFacade.findAll();
        select.setEmptySelectionAllowed(false);
        select.setItemCaptionGenerator(item -> item.getBezeichnung() + " id:" + item.getId());
        select.setItems(buchhaltungen);
        select.setSelectedItem(buchhaltungen.iterator().next());
        return select;
    }

    private Grid<Kontoklasse> createGridKontoklasse(BuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<Kontoklasse> kontoklasseBeanValidationBinder = new BeanValidationBinder<>(Kontoklasse.class);

        Grid<Kontoklasse> grid = new Grid<>();
        grid.setCaption("Kontoklassen");
        grid.setSizeFull();
        Buchhaltung buchhaltung = buchhaltungFacade.findBy(val.getId());
        grid.setItems(buchhaltung.getKontoklasse());
        kontoklasseBeanValidationBinder.bind(bezeichnungFld, Kontoklasse::getBezeichnung, Kontoklasse::setBezeichnung);
        kontoklasseBeanValidationBinder.bind(kontonummerFld, Kontoklasse::getKontonummer, Kontoklasse::setKontonummer);

        grid.getEditor().setBinder(kontoklasseBeanValidationBinder);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (kontoklasseBeanValidationBinder.isValid()) {
                Kontoklasse kontoklasse = event.getBean();
                kontoklasse = kontoklasseFacade.save(kontoklasse);
                grid.setItems(kontoklasseFacade.findByBuchhaltung(buchhaltung));
                grid.select(kontoklasseFacade.findBy(event.getBean().getId()));
            }
        });
        grid.addColumn(Kontoklasse::getId).setCaption("Id");
        grid.addColumn(Kontoklasse::getVersion).setCaption("Version");
        grid.addColumn(Kontoklasse::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(Kontoklasse::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    Kontoklasse kontoklasse = (Kontoklasse) event.getItem();
                    Buchhaltung buchhaltung1 = kontoklasse.getBuchhaltung();
                    Notification.show("Lösche Kontoklasse id:" + kontoklasse);
                    buchhaltung1.getKontoklasse().remove(kontoklasse);
                    buchhaltung1 = buchhaltungFacade.save(buchhaltung1);
                    kontoklasseFacade.delete(kontoklasse);
                    grid.setItems(kontoklasseFacade.findByBuchhaltung(buchhaltung1));
                    updateTree(buchhaltung1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<Kontohauptgruppe> createGridKontohauptgruppe(BuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<Kontohauptgruppe> binder = new BeanValidationBinder<>(Kontohauptgruppe.class);

        Grid<Kontohauptgruppe> grid = new Grid<>();
        grid.setCaption("Kontohauptgruppen");
        grid.setSizeFull();
        Kontoklasse kontoklasse = kontoklasseFacade.findBy(val.getId());
        grid.setItems(kontoklasse.getKontohauptgruppes());
        binder.bind(bezeichnungFld, Kontohauptgruppe::getBezeichnung, Kontohauptgruppe::setBezeichnung);
        binder.bind(kontonummerFld, Kontohauptgruppe::getKontonummer, Kontohauptgruppe::setKontonummer);

        grid.getEditor().setBinder(binder);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binder.isValid()) {
                Kontohauptgruppe kontohauptgruppe = event.getBean();
                kontohauptgruppeFacade.save(event.getBean());
                grid.setItems(kontohauptgruppeFacade.findByKontoklasse(kontohauptgruppe.getKontoklasse()));
                grid.select(kontohauptgruppeFacade.findBy(event.getBean().getId()));
            }
        });

        grid.addColumn(Kontohauptgruppe::getId).setCaption("Id");
        grid.addColumn(Kontohauptgruppe::getVersion).setCaption("Version");
        grid.addColumn(Kontohauptgruppe::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(Kontohauptgruppe::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    Kontohauptgruppe kontohauptgruppe = (Kontohauptgruppe) event.getItem();
                    Kontoklasse kontoklasse1 = kontohauptgruppe.getKontoklasse();
                    kontoklasse1.getKontohauptgruppes().remove(kontohauptgruppe);
                    kontoklasse1 = kontoklasseFacade.save(kontoklasse1);
                    kontohauptgruppeFacade.delete(kontohauptgruppe);
                    grid.setItems(kontohauptgruppeFacade.findByKontoklasse(kontoklasse1));
                    updateTree(kontoklasse1.getId());
                    Notification.show("Lösche Kontohauptgruppe id:" + kontohauptgruppe.getId(), Notification.Type.HUMANIZED_MESSAGE);
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<Kontogruppe> createGridKontogruppe(BuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<Kontogruppe> binder = new BeanValidationBinder<>(Kontogruppe.class);

        Grid<Kontogruppe> grid = new Grid<>();
        grid.setCaption("Kontogruppen");
        grid.setSizeFull();
        Kontohauptgruppe kontohauptgruppe = kontohauptgruppeFacade.findBy(val.getId());
        grid.setItems(kontohauptgruppe.getKontogruppes());

        binder.bind(bezeichnungFld, Kontogruppe::getBezeichnung, Kontogruppe::setBezeichnung);
        binder.bind(kontonummerFld, Kontogruppe::getKontonummer, Kontogruppe::setKontonummer);

        grid.getEditor().setBinder(binder);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binder.isValid()) {
                kontogruppeFacade.save(event.getBean());
                grid.setItems(kontogruppeFacade.findByKontohauptgruppe(event.getBean().getKontohauptgruppe()));
                grid.select(kontogruppeFacade.findBy(event.getBean().getId()));
            }
        });

        grid.addColumn(Kontogruppe::getId).setCaption("Id");
        grid.addColumn(Kontogruppe::getVersion).setCaption("Version");
        grid.addColumn(Kontogruppe::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(Kontogruppe::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    Kontogruppe kontogruppe = (Kontogruppe) event.getItem();
                    Kontohauptgruppe kontohauptgruppe1 = kontogruppe.getKontohauptgruppe();
                    Notification.show("Lösche Kontohauptgruppe id:" + kontogruppe.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    kontohauptgruppe1.getKontogruppes().remove(kontogruppe);
                    kontohauptgruppe1 = kontohauptgruppeFacade.save(kontohauptgruppe1);
                    kontogruppeFacade.delete(kontogruppe);
                    grid.setItems(kontogruppeFacade.findByKontohauptgruppe(kontohauptgruppe1));
                    updateTree(kontohauptgruppe1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }

    private Grid<Konto> createGridKonto(BuchhaltungTreeData val) {
        TextField bezeichnungFld = new TextField();
        TextField kontonummerFld = new TextField();
        BeanValidationBinder<Konto> binder = new BeanValidationBinder<>(Konto.class);

        Grid<Konto> grid = new Grid<>();
        grid.setCaption("Konti");
        grid.setSizeFull();
        Kontogruppe kontogruppe = kontogruppeFacade.findBy(val.getId());
        grid.setItems(kontogruppe.getKontos());

        binder.bind(bezeichnungFld, Konto::getBezeichnung, Konto::setBezeichnung);
        binder.bind(kontonummerFld, Konto::getKontonummer, Konto::setKontonummer);

        grid.getEditor().setBinder(binder);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binder.isValid()) {
                Konto konto = event.getBean();
                kontoFacade.save(konto);
                Kontogruppe kontogruppe1 = kontogruppeFacade.findBy(konto.getKontogruppe().getId());
                grid.setItems(kontoFacade.findByKontogruppe(kontogruppe1));
                grid.select(kontoFacade.findBy(event.getBean().getId()));
            }
        });

        grid.addColumn(Konto::getId).setCaption("Id");
        grid.addColumn(Konto::getVersion).setCaption("Version");
        grid.addColumn(Konto::getBezeichnung).setId("bezeichnung").setEditorComponent(bezeichnungFld).setCaption("Bezeichnung");
        grid.addColumn(Konto::getKontonummer).setId("kontonummer").setEditorComponent(kontonummerFld).setCaption("Kontonummer");

        grid.addColumn(konto -> "ändern",
                new ButtonRenderer(event -> {
                    kontoForm.setEntity((Konto) event.getItem());
                    kontoForm.openInModalPopup();
                    kontoForm.setSavedHandler(konto -> {
                        kontoFacade.save(konto);
                        Kontogruppe kontogruppe1 = konto.getKontogruppe();
                        updateTree(kontogruppe1.getId());
                        grid.setItems(kontogruppe1.getKontos());
                        grid.select(konto);
                        kontoForm.closePopup();
                    });
                    kontoForm.setResetHandler(konto -> {
                        grid.setItems(kontogruppe.getKontos());
                        grid.select(konto);
                        kontoForm.closePopup();
                    });
                })).setCaption("ändern");


        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    Konto konto = (Konto) event.getItem();
                    Notification.show("Lösche Konto id:" + konto.getId());
                    Kontogruppe kontogruppe1 = konto.getKontogruppe();
                    kontogruppe1.getKontos().remove(konto);
                    kontogruppe1 = kontogruppeFacade.save(kontogruppe1);
                    kontoFacade.delete(konto);
                    grid.setItems(kontoFacade.findByKontogruppe(kontogruppe1));
                    updateTree(kontogruppe1.getId());
                })
        ).setCaption("Löschen");

        return grid;
    }


    private List<Konto> createKontoList(Buchhaltung buchhaltung) {
        List<Konto> list = new ArrayList<>();

        buchhaltung.getKontoklasse().stream().forEach(kontoklasse -> {
            kontoklasse.getKontohauptgruppes().stream().forEach(kontohauptgruppe -> {
                kontohauptgruppe.getKontogruppes().stream().forEach(kontogruppe -> {
                    kontogruppe.getKontos().stream().forEach(konto -> {
                        list.add(konto);
                    });
                });
            });
        });
        return list;
    }

    private Window createMehrwertsteuerWindow(Buchhaltung buchhaltung) {
        Buchhaltung bh = buchhaltungFacade.findBy(buchhaltung.getId());
        HorizontalLayout layout = new HorizontalLayout();

        Grid<Mehrwertsteuercode> grid = new Grid<>();

        grid.setCaption("Mehrwertsteuercodes");
        grid.setItems(bh.getMehrwertsteuercode());
        grid.getEditor().setEnabled(true);

        grid.getEditor().addSaveListener(event -> {
            if (grid.getEditor().getBinder().isValid()) {
                mehrwertsteuercodeFacade.save(event.getBean());
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
        grid.addColumn(Mehrwertsteuercode::getId).setCaption("Id");
        grid.addColumn(Mehrwertsteuercode::getVersion).setCaption("Vers");
        grid.addColumn(Mehrwertsteuercode::getCode).setEditorComponent(new TextField(), Mehrwertsteuercode::setCode).setCaption("Code");
        grid.addColumn(Mehrwertsteuercode::getBezeichnung).setEditorComponent(new TextField(), Mehrwertsteuercode::setBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Mehrwertsteuercode::getProzentString).setEditorComponent(new TextField(), Mehrwertsteuercode::setProzentString).setCaption("Prozent");
        grid.addColumn(Mehrwertsteuercode::isVerkauf).setEditorComponent(new CheckBox(), Mehrwertsteuercode::setVerkauf).setCaption("Verkauf");
        grid.addColumn(mehrwertsteuercode1 -> mehrwertsteuercode1.getMehrwertsteuerKonto().getBezeichnung()
                + " " + mehrwertsteuercode1.getMehrwertsteuerKonto().getId()).setCaption("Konto");
        grid.addColumn(templateMehrwertsteuercode -> templateMehrwertsteuercode.getMehrwertsteuerKonto().getShowKontonummer()).setCaption("KoNr");
        grid.addColumn(event -> "löschen",
                new ButtonRenderer(event -> {
                    Mehrwertsteuercode mehrwertsteuercode = (Mehrwertsteuercode) event.getItem();
                    mehrwertsteuercode = mehrwertsteuercodeFacade.findBy(mehrwertsteuercode.getId());
                    Buchhaltung buchhaltung1 = mehrwertsteuercode.getBuchhaltung();
                    buchhaltung1 = buchhaltungFacade.findBy(buchhaltung1.getId());
                    buchhaltung1.getMehrwertsteuercode().remove(mehrwertsteuercode);
                    buchhaltung1 = buchhaltungFacade.save(buchhaltung1);

                    Notification.show("Löschen Mehrwertsteuercode id:" + mehrwertsteuercode.getId());
                    mehrwertsteuercodeFacade.delete(mehrwertsteuercode);
                    grid.setItems(mehrwertsteuercodeFacade.findByBuchhaltung(buchhaltungSelect.getValue()));
                }));
        grid.addColumn(event -> "ändern",
                new ButtonRenderer(event -> {
                    Mehrwertsteuercode mehrwertsteuercode = (Mehrwertsteuercode) event.getItem();
                    mehrwertsteuercode = mehrwertsteuercodeFacade.findBy(mehrwertsteuercode.getId());
                    mehrwertsteuercodeForm.setEntity(mehrwertsteuercode);
                    mehrwertsteuercodeForm.lockSelect();
                    mehrwertsteuercodeForm.openInModalPopup();
                    mehrwertsteuercodeForm.setSavedHandler(val -> {
                        mehrwertsteuercodeFacade.save(val);
                        grid.setItems(mehrwertsteuercodeFacade.findByBuchhaltung(buchhaltungSelect.getValue()));
                        grid.select(val);
                        mehrwertsteuercodeForm.closePopup();
                    });
                    mehrwertsteuercodeForm.setResetHandler(val -> {
                        grid.setItems(mehrwertsteuercodeFacade.findByBuchhaltung(buchhaltungSelect.getValue()));
                        grid.select(val);
                        mehrwertsteuercodeForm.closePopup();
                    });

                }));

        Button mehrwertsteuerAddBtn = new Button("Add Mehrwertsteuercode", event -> {
            grid.asSingleSelect().clear();
            Mehrwertsteuercode mehrwertsteuercode = new Mehrwertsteuercode();
            mehrwertsteuercode.setBuchhaltung(buchhaltungSelect.getValue());
            mehrwertsteuercode.setMehrwertsteuerKonto(createKontoList(buchhaltungSelect.getValue()).get(0));

            mehrwertsteuercode.setProzent(8f);

            mehrwertsteuercodeForm.setWidth(500, Unit.PIXELS);
            mehrwertsteuercodeForm.lockSelect();
            mehrwertsteuercodeForm.setEntity(mehrwertsteuercode);
            mehrwertsteuercodeForm.openInModalPopup();
            mehrwertsteuercodeForm.setSavedHandler(templateMehrwertsteuercode -> {
                mehrwertsteuercodeFacade.save(templateMehrwertsteuercode);
                grid.setItems(getMehrwertsteuerList());
                grid.select(templateMehrwertsteuercode);
                mehrwertsteuercodeForm.closePopup();
            });

        });

        mehrwertsteuerAddBtn.setIcon(VaadinIcons.ASTERISK);
        mehrwertsteuerAddBtn.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        mehrwertsteuerAddBtn.setSizeUndefined();
        layout.addComponents(mehrwertsteuerAddBtn);
        layout.addComponentsAndExpand(grid);
        layout.setMargin(true);

        Window window = new Window("Mehrwersteuercode");
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


    private List<Mehrwertsteuercode> getMehrwertsteuerList() {
        return mehrwertsteuercodeFacade.findByBuchhaltung(buchhaltungSelect.getValue());
    }


    private void updateTree(Long selectId) {
        Buchhaltung buchhaltung = buchhaltungFacade.findBy(buchhaltungSelect.getValue().getId());
        Collection<Kontoklasse> kontoklasses = buchhaltung.getKontoklasse();
        TreeData<BuchhaltungTreeData> buchhaltungTreeData = new TreeData<>();
        TreeDataProvider<BuchhaltungTreeData> provider = new TreeDataProvider<>(buchhaltungTreeData);

        List<BuchhaltungTreeData> list = new ArrayList<>();

        buchhaltungTreeData.clear();
        buchhaltungTree.setDataProvider(provider);
        BuchhaltungTreeData valBH = new BuchhaltungTreeData(buchhaltung.getId(), "BH", buchhaltung.getBezeichnung() + " id:" + buchhaltung.getId());
        treeRootItem = valBH;
        list.add(valBH);
        buchhaltungTreeData.addItem(null, valBH);
        if (valBH.getId() == selectId) buchhaltungTree.select(valBH);
        kontoklasses.forEach(kontoklasse -> {
            BuchhaltungTreeData valKK = new BuchhaltungTreeData(kontoklasse.getId(), "KK", kontoklasse.getBezeichnung() + " KoNr:" + kontoklasse.getShowKontonummer());
            list.add(valKK);
            buchhaltungTreeData.addItem(valBH, valKK);
            if (valKK.getId() == selectId) buchhaltungTree.select(valKK);
            kontoklasse.getKontohauptgruppes().forEach(kontohauptgruppe -> {
                BuchhaltungTreeData valHG = new BuchhaltungTreeData(kontohauptgruppe.getId(), "HG", kontohauptgruppe.getBezeichnung() + " KoNr:" + kontohauptgruppe.getShowKontonummer());
                list.add(valHG);
                buchhaltungTreeData.addItem(valKK, valHG);
                if (valHG.getId() == selectId) buchhaltungTree.select(valHG);
                kontohauptgruppe.getKontogruppes().forEach(kontogruppe -> {
                    BuchhaltungTreeData valKG = new BuchhaltungTreeData(kontogruppe.getId(), "KG", kontogruppe.getBezeichnung() + " KoNr:" + kontogruppe.getShowKontonummer());
                    list.add(valKG);
                    buchhaltungTreeData.addItem(valHG, valKG);
                    if (valKG.getId() == selectId) buchhaltungTree.select(valKG);
                    kontogruppe.getKontos().forEach(konto -> {
                        BuchhaltungTreeData valKO = new BuchhaltungTreeData(konto.getId(), "KO", konto.getBezeichnung() + " KoNr:" + konto.getShowKontonummer());
                        list.add(valKO);
                        if (valKO.getId() == selectId) buchhaltungTree.select(valKO);
                    });
                });
            });
        });
        buchhaltungTree.expand(valBH);
        list.forEach(buchhaltungTreeData1 -> buchhaltungTree.expand(buchhaltungTreeData1));
    }
}
