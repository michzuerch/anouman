package ch.internettechnik.anouman.presentation.ui.artikel;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelkategorieDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
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


// @todo : java.lang.IllegalStateException: Property type 'java.util.Date' doesn't match the field type 'java.time.LocalDateTime'.
// Binding should be configured manually using converter.
@CDIView("ArtikelView")
public class ArtikelView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ArtikelView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    ComboBox<Artikelkategorie> filterArtikelkategorie = new ComboBox<>();
    Grid<Artikel> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    private ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;

    @Inject
    private ArtikelForm artikelForm;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterTextBezeichnung.setPlaceholder("Filter Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);
        filterArtikelkategorie.setPlaceholder("Filter Artikelkategorie");
        filterArtikelkategorie.setItems(artikelkategorieDeltaspikeFacade.findAll());
        filterArtikelkategorie.setItemCaptionGenerator(artikelkategorie -> artikelkategorie.getBezeichnung() + " id:" + artikelkategorie.getId());
        filterArtikelkategorie.setEmptySelectionAllowed(false);
        filterArtikelkategorie.addValueChangeListener(valueChangeEvent -> updateList());

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
            if (target.equals("artikelkategorieId")) {
                filterArtikelkategorie.setSelectedItem(artikelkategorieDeltaspikeFacade.findBy(id));
                updateList();
            } else if (target.equals("id")) {
                grid.select(artikelDeltaspikeFacade.findBy(id));
            }
        }

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
            filterArtikelkategorie.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            Artikel artikel = new Artikel();
            artikel.setArtikelkategorie(artikelkategorieDeltaspikeFacade.findAll().get(0));
            artikel.setStueckpreis(0d);
            artikel.setAnzahl(0d);
            if (!filterArtikelkategorie.isEmpty()) artikel.setArtikelkategorie(filterArtikelkategorie.getValue());
            artikelForm.setEntity(artikel);
            artikelForm.openInModalPopup();
            artikelForm.setSavedHandler(val -> {
                artikelDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                artikelForm.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterArtikelkategorie, filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Artikel");
        grid.setCaptionAsHtml(true);
        grid.addColumn(Artikel::getId).setCaption("id");
        grid.addColumn(Artikel::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Artikel::getMengeneinheit).setCaption("Mengeneinheit");
        grid.addColumn(Artikel::getAnzahl).setCaption("Anzahl");
        grid.addColumn(Artikel::getStueckpreis).setCaption("Stückpreis");
        grid.addColumn(artikel -> artikel.getArtikelkategorie().getBezeichnung() + " id:" + artikel.getArtikelkategorie().getId(),
                new ButtonRenderer(event -> {
                    Artikel artikel = (Artikel) event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("ArtikelkategorieView/id/" + artikel.getArtikelkategorie().getId());
                })
        ).setCaption("Artikelkategorie").setStyleGenerator(item -> "v-align-center");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(aufwand -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Artikel id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    artikelDeltaspikeFacade.delete((Artikel) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(adresse -> "ändern",
                new ButtonRenderer(event -> {
                    artikelForm.setEntity((Artikel) event.getItem());
                    artikelForm.openInModalPopup();
                    artikelForm.setSavedHandler(val -> {
                        artikelDeltaspikeFacade.save(val);
                        updateList();
                        grid.select(val);
                        artikelForm.closePopup();
                    });
                    artikelForm.setResetHandler(val -> {
                        updateList();
                        grid.select(val);
                        artikelForm.closePopup();
                    });
                }));

        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    public void updateList() {
        if ((!filterArtikelkategorie.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Artikelkategorie und Bezeichnung
            logger.debug("Suche mit Rechnung und Titel:" + filterArtikelkategorie.getValue().getId() + "," + filterTextBezeichnung.getValue());
            grid.setItems(artikelDeltaspikeFacade.findByArtikelkategorieAndBezeichnungLikeIgnoreCase(filterArtikelkategorie.getValue(), filterTextBezeichnung.getValue() + "%"));
            return;
        } else if ((!filterArtikelkategorie.isEmpty()) && (filterTextBezeichnung.isEmpty())) {
            //Suche mit Artikelkategorie
            logger.debug("Suche mit Rechnung:" + filterArtikelkategorie.getValue().getId());
            grid.setItems(artikelDeltaspikeFacade.findByArtikelkategorie(filterArtikelkategorie.getValue()));
            return;
        } else if ((filterArtikelkategorie.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Titel:" + filterTextBezeichnung.getValue());
            grid.setItems(artikelDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(artikelDeltaspikeFacade.findAll());
    }

}
