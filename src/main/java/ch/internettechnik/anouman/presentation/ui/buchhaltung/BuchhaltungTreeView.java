package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Logger;

@CDIView(value = "BuchhaltungTree")
public class BuchhaltungTreeView extends VerticalLayout implements View {
    private static final Logger LOGGER = Logger.getLogger(BuchhaltungTreeView.class.getName());

    private Grid<Kontoklasse> kontoklasseGrid = new Grid<>();
    private Grid<Kontohauptgruppe> kontohauptgruppeGrid = new Grid<>();
    private Grid<Kontogruppe> kontogruppeGrid = new Grid<>();
    private Grid<Konto> kontoGrid = new Grid<>();
    private NativeSelect<Buchhaltung> buchhaltungSelect = new NativeSelect<>();

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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");
        CssLayout tools = new CssLayout();
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        HorizontalLayout bodyLayout = new HorizontalLayout();
        bodyLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        NativeSelect<Buchhaltung> buchhaltungSelect = createNativeSelect();
        Tree<BuchhaltungTreeData> buchhaltungTree = createBuchhaltungTree();
        buchhaltungSelect.setCaption("Wähle Buchhaltung");
        buchhaltungSelect.setEmptySelectionAllowed(false);
        buchhaltungSelect.setItemCaptionGenerator(item -> item.getBezeichnung() + " id:" + item.getId());

        bodyLayout.addComponent(buchhaltungTree);
        bodyLayout.setExpandRatio(buchhaltungTree, 0);
        bodyLayout.setSizeFull();
        buchhaltungSelect.addValueChangeListener(event -> {
            buchhaltungTree.setDataProvider(updateProvider(event.getValue()));
        });

        buchhaltungTree.addItemClickListener(event -> {
            event.getItem();
        });

        buchhaltungTree.addSelectionListener(event -> {
            bodyLayout.removeComponent(kontoklasseGrid);
            bodyLayout.removeComponent(kontohauptgruppeGrid);
            bodyLayout.removeComponent(kontogruppeGrid);
            bodyLayout.removeComponent(kontoGrid);
            if (!buchhaltungTree.asSingleSelect().isEmpty()) {
                BuchhaltungTreeData selectedItem = buchhaltungTree.asSingleSelect().getValue();
                if (selectedItem.getType().equals("BH")) {
                    kontoklasseGrid = createGridKontoklasse(selectedItem);
                    bodyLayout.addComponent(kontoklasseGrid);
                    bodyLayout.setExpandRatio(kontoklasseGrid, 5);
                }
                if (selectedItem.getType().equals("KK")) {
                    kontohauptgruppeGrid = createGridKontohauptgruppe(selectedItem);
                    bodyLayout.addComponent(kontohauptgruppeGrid);
                    bodyLayout.setExpandRatio(kontohauptgruppeGrid, 5);
                }
                if (selectedItem.getType().equals("KG")) {
                    kontogruppeGrid = createGridKontogruppe(selectedItem);
                    bodyLayout.addComponent(kontogruppeGrid);
                    bodyLayout.setExpandRatio(kontogruppeGrid, 5);
                }
                if (selectedItem.getType().equals("KA")) {
                    kontoGrid = createGridKonto(selectedItem);
                    bodyLayout.addComponent(kontoGrid);
                    bodyLayout.setExpandRatio(kontoGrid, 5);
                }
            }
        });

        addComponents(menu, tools);
        addComponent(buchhaltungSelect);
        addComponentsAndExpand(bodyLayout);
        bodyLayout.setExpandRatio(buchhaltungTree, 1);

    }

    private NativeSelect<Buchhaltung> createNativeSelect() {
        NativeSelect<Buchhaltung> select = new NativeSelect<>();
        Collection<Buchhaltung> buchhaltungen = buchhaltungFacade.findAll();
        select.setItems(buchhaltungen);
        select.setEmptySelectionAllowed(false);
        return select;
    }

    private Grid<Kontoklasse> createGridKontoklasse(BuchhaltungTreeData val) {
        Grid<Kontoklasse> grid = new Grid<>();
        grid.setCaption("Kontoklassen");
        grid.setSizeFull();
        Buchhaltung buchhaltung = buchhaltungFacade.findBy(val.getId());
        grid.setItems(buchhaltung.getKontoklasse());
        grid.addColumn(Kontoklasse::getId);
        grid.addColumn(Kontoklasse::getBezeichnung);
        return grid;
    }

    private Grid<Kontohauptgruppe> createGridKontohauptgruppe(BuchhaltungTreeData val) {
        Grid<Kontohauptgruppe> grid = new Grid<>();
        grid.setCaption("Kontogruppen");
        grid.setSizeFull();
        Kontoklasse kontoklasse = kontoklasseFacade.findBy(val.getId());
        grid.setItems(kontoklasse.getKontohauptgruppes());
        grid.addColumn(Kontohauptgruppe::getId);
        grid.addColumn(Kontohauptgruppe::getBezeichnung);
        return grid;
    }

    private Grid<Kontogruppe> createGridKontogruppe(BuchhaltungTreeData val) {
        Grid<Kontogruppe> grid = new Grid<>();
        grid.setCaption("Kontoarten");
        grid.setSizeFull();
        Kontohauptgruppe kontohauptgruppe = kontohauptgruppeFacade.findBy(val.getId());
        grid.setItems(kontohauptgruppe.getKontogruppes());
        grid.addColumn(Kontogruppe::getId);
        grid.addColumn(Kontogruppe::getBezeichnung);
        return grid;
    }

    private Grid<Konto> createGridKonto(BuchhaltungTreeData val) {
        Grid<Konto> grid = new Grid<>();
        grid.setCaption("Konti");
        grid.setSizeFull();
        Kontogruppe kontogruppe = kontogruppeFacade.findBy(val.getId());
        grid.setItems(kontogruppe.getKontos());
        grid.addColumn(Konto::getId);
        grid.addColumn(Konto::getBezeichnung);
        return grid;
    }

    private Tree<BuchhaltungTreeData> createBuchhaltungTree() {
        Tree<BuchhaltungTreeData> tree = new Tree<>();

        Buchhaltung buchhaltung;

        if (!buchhaltungSelect.getSelectedItem().isPresent()) {
            buchhaltungSelect.setSelectedItem(buchhaltungFacade.findAll().get(0));
        }

        buchhaltung = buchhaltungSelect.getSelectedItem().get();
        tree.setDataProvider(updateProvider(buchhaltung));
        return tree;
    }

    private TreeDataProvider<BuchhaltungTreeData> updateProvider(Buchhaltung buchhaltung) {
        Collection<Kontoklasse> kontoklasses = buchhaltung.getKontoklasse();
        TreeData<BuchhaltungTreeData> buchhaltungTreeData = new TreeData<>();
        TreeDataProvider<BuchhaltungTreeData> provider = new TreeDataProvider<>(buchhaltungTreeData);
        buchhaltungTreeData.clear();

        BuchhaltungTreeData valBH = new BuchhaltungTreeData(buchhaltung.getId(), "BH", buchhaltung.getBezeichnung() + " id:" + buchhaltung.getId());
        buchhaltungTreeData.addItem(null, valBH);
        kontoklasses.forEach(kontoklasse -> {
            BuchhaltungTreeData valKK = new BuchhaltungTreeData(kontoklasse.getId(), "KK", kontoklasse.getBezeichnung() + " id:" + kontoklasse.getId());
            buchhaltungTreeData.addItem(valBH, valKK);
            kontoklasse.getKontohauptgruppes().forEach(kontohauptgruppe -> {
                BuchhaltungTreeData valKG = new BuchhaltungTreeData(kontohauptgruppe.getId(), "KG", kontohauptgruppe.getBezeichnung() + " id:" + kontohauptgruppe.getId());
                buchhaltungTreeData.addItem(valKK, valKG);
                kontohauptgruppe.getKontogruppes().forEach(kontogruppe -> {
                    BuchhaltungTreeData valKA = new BuchhaltungTreeData(kontogruppe.getId(), "KA", kontogruppe.getBezeichnung());
                    buchhaltungTreeData.addItem(valKG, valKA);
                    kontogruppe.getKontos().forEach(konto -> {
                        BuchhaltungTreeData valSK = new BuchhaltungTreeData(konto.getId(), "SK", konto.getBezeichnung());
                        buchhaltungTreeData.addItem(valKA, valSK);
                    });
                });
            });
        });
        provider.refreshAll();
        return provider;
    }
}
