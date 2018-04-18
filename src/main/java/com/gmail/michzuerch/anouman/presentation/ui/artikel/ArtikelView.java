package com.gmail.michzuerch.anouman.presentation.ui.artikel;

import com.gmail.michzuerch.anouman.backend.entity.Artikel;
import com.gmail.michzuerch.anouman.backend.entity.Artikelkategorie;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelkategorieDeltaspikeFacade;
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


@CDIView("ArtikelView")
public class ArtikelView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ArtikelView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    ComboBox<Artikelkategorie> filterArtikelkategorie = new ComboBox<>();
    Grid<Artikel> grid = new Grid<>();

    @Inject
    private ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    private ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;

    @Inject
    private ArtikelForm artikelForm;

    private void createContent() {
        filterTextBezeichnung.setPlaceholder("Filter Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);
        filterArtikelkategorie.setPlaceholder("Filter Artikelkategorie");
        filterArtikelkategorie.setItems(artikelkategorieDeltaspikeFacade.findAll());
        filterArtikelkategorie.setItemCaptionGenerator(artikelkategorie -> artikelkategorie.getBezeichnung() + " id:" + artikelkategorie.getId());
        filterArtikelkategorie.setEmptySelectionAllowed(false);
        filterArtikelkategorie.addValueChangeListener(valueChangeEvent -> updateList());

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

        // Render a button that deletes the data row (item)
        grid.addColumn(artikel -> "löschen",
                new ButtonRenderer(event -> {
                    Artikel artikel = (Artikel) event.getItem();
                    Notification.show("Lösche Artikel id:" + artikel.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    artikelDeltaspikeFacade.delete(artikel);
                    updateList();
                })
        );

        grid.addColumn(artikel -> "ändern",
                new ButtonRenderer(event -> {
                    Artikel artikel = (Artikel) event.getItem();
                    artikelForm.setEntity(artikel);
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
        grid.setSizeFull();
        setMargin(false);
        setSpacing(false);
        addComponents(tools, grid);
        setExpandRatio(grid, 1);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        createContent();
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
        updateList();
    }

    public void updateList() {
        if ((!filterArtikelkategorie.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Artikelkategorie und Bezeichnung
            logger.debug("Suche mit Artikelkategorie und Bezeichnung:" + filterArtikelkategorie.getValue().getId() + "," + filterTextBezeichnung.getValue());
            grid.setItems(artikelDeltaspikeFacade.findByArtikelkategorieAndBezeichnungLikeIgnoreCase(filterArtikelkategorie.getValue(), filterTextBezeichnung.getValue() + "%"));
            return;
        } else if ((!filterArtikelkategorie.isEmpty()) && (filterTextBezeichnung.isEmpty())) {
            //Suche mit Artikelkategorie
            logger.debug("Suche mit Artikelkategorie:" + filterArtikelkategorie.getValue().getId());
            grid.setItems(artikelDeltaspikeFacade.findByArtikelkategorie(filterArtikelkategorie.getValue()));
            return;
        } else if ((filterArtikelkategorie.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(artikelDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(artikelDeltaspikeFacade.findAll());
    }

}
