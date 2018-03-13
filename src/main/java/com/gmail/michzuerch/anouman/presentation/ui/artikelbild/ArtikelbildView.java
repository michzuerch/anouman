package com.gmail.michzuerch.anouman.presentation.ui.artikelbild;

import com.gmail.michzuerch.anouman.backend.entity.Artikel;
import com.gmail.michzuerch.anouman.backend.entity.Artikelbild;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildDeltaspikeFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;
import org.vaadin.teemusa.flexlayout.*;

import javax.inject.Inject;


// @todo : java.lang.IllegalStateException: Property type 'java.util.Date' doesn't match the field type 'java.time.LocalDateTime'.
@CDIView("ArtikelbildView")
public class ArtikelbildView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ArtikelbildView.class.getName());

    ComboBox<Artikel> filterArtikel = new ComboBox<>();
    TextField filterTextTitel = new TextField();
    Grid<Artikelbild> grid = new Grid<>();

    @Inject
    private ArtikelbildDeltaspikeFacade artikelbildDeltaspikeFacade;

    @Inject
    private ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    private ArtikelbildForm artikelbildForm;

    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Row);
        layout.setAlignItems(AlignItems.FlexEnd);
        layout.setJustifyContent(JustifyContent.SpaceBetween);
        layout.setAlignContent(AlignContent.Stretch);
        layout.setFlexWrap(FlexWrap.Wrap);

        filterArtikel.setPlaceholder("Filter Artikel");
        filterArtikel.setItems(artikelDeltaspikeFacade.findAll());
        filterArtikel.setItemCaptionGenerator(artikel -> artikel.getId() + " " + artikel.getBezeichnung());
        filterArtikel.setEmptySelectionAllowed(false);
        filterArtikel.addValueChangeListener(valueChangeEvent -> updateList());

        filterTextTitel.setPlaceholder("Filter Titel");
        filterTextTitel.addValueChangeListener(e -> updateList());
        filterTextTitel.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterArtikel.clear();
            filterTextTitel.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            Artikelbild artikelbild = new Artikelbild();
            artikelbildForm.setEntity(artikelbild);
            artikelbildForm.openInModalPopup();

            artikelbildForm.setSavedHandler(val -> {
                System.err.println("savedHandler image len:" + val.getImage().length);
                artikelbildDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                artikelbildForm.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterArtikel, filterTextTitel, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        grid.addColumn(Artikelbild::getId).setCaption("id");
        grid.addColumn(Artikelbild::getTitel).setCaption("Titel");
        grid.addColumn(Artikelbild::getMimetype).setCaption("Mimetype");
        grid.addColumn(Artikelbild::getSize).setCaption("Size");
        grid.addColumn(artikelbild -> artikelbild.getArtikel().getBezeichnung() + " id:" + artikelbild.getArtikel().getId(),
                new ButtonRenderer(event -> {
                    Artikelbild artikelbild = (Artikelbild) event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("ArtikelView/id/" + artikelbild.getArtikel().getId());
                })
        ).setCaption("Artikel").setStyleGenerator(item -> "v-align-center");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(artikelbild -> "löschen",
                new ButtonRenderer(event -> {
                    Artikelbild bild = (Artikelbild) event.getItem();
                    Notification.show("Lösche Artikelbild id:" + bild, Notification.Type.HUMANIZED_MESSAGE);

                    Artikel artikel = artikelDeltaspikeFacade.findBy(bild.getArtikel().getId());
                    artikel.getArtikelbilds().remove(bild);
                    artikelDeltaspikeFacade.save(artikel);

                    artikelbildDeltaspikeFacade.delete(bild);
                    updateList();
                })
        );

        grid.addColumn(adresse -> "ändern",
                new ButtonRenderer(event -> {
                    artikelbildForm.setEntity((Artikelbild) event.getItem());
                    artikelbildForm.openInModalPopup();
                    artikelbildForm.setSavedHandler(val -> {
                        artikelbildDeltaspikeFacade.save(val);
                        updateList();
                        grid.select(val);
                        artikelbildForm.closePopup();
                    });
                    artikelbildForm.setResetHandler(val -> {
                        updateList();
                        grid.select(val);
                        artikelbildForm.closePopup();
                    });
                }));
        layout.addComponents(tools, grid);
        layout.setSizeFull();
        return layout;


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(createContent());
        setSizeFull();

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
                grid.select(artikelbildDeltaspikeFacade.findBy(id));
            }
        }

        updateList();
    }

    public void updateList() {
        if ((!filterArtikel.isEmpty()) && (!filterTextTitel.isEmpty())) {
            //Suche mit Artikel und Titel
            logger.debug("Suche mit Artikel " + filterArtikel.getValue() + " und Titel " + filterTextTitel);
            grid.setItems(artikelbildDeltaspikeFacade.findByArtikelAndTitelLikeIgnoreCase(filterArtikel.getValue(), filterTextTitel.getValue() + "%"));
            return;
        }
        if (!filterArtikel.isEmpty()) {
            //Suche mit Artikel
            logger.debug("Suche mit Artikel:" + filterArtikel.getValue());
            grid.setItems(artikelbildDeltaspikeFacade.findByArtikel(filterArtikel.getValue()));
            return;
        }
        if (!filterTextTitel.isEmpty()) {
            //Suche mit Titel
            logger.debug("Suche mit Titel:" + filterTextTitel.getValue());
            grid.setItems(artikelbildDeltaspikeFacade.findByTitelLikeIgnoreCase(filterTextTitel.getValue() + "%"));
            return;
        }
        grid.setItems(artikelbildDeltaspikeFacade.findAll());
    }

}
