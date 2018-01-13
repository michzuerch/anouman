package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildDeltaspikeFacade;
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
@CDIView("ArtikelbildView")
public class ArtikelbildView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ArtikelbildView.class.getName());

    TextField filterTextTitel = new TextField();
    Grid<Artikelbild> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private ArtikelbildDeltaspikeFacade artikelbildDeltaspikeFacade;

    @Inject
    private ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    private ArtikelbildForm artikelbildForm;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterTextTitel.setPlaceholder("Filter Titel");
        filterTextTitel.addValueChangeListener(e -> updateList());
        filterTextTitel.setValueChangeMode(ValueChangeMode.LAZY);

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

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextTitel.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            Artikelbild artikelbild = new Artikelbild();
            artikelbildForm.setEntity(artikelbild);
            artikelbildForm.openInModalPopup();
            artikelbildForm.setSavedHandler(val -> {
                artikelbildDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                artikelbildForm.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextTitel, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Artikelbild");
        grid.setCaptionAsHtml(true);
        grid.addColumn(Artikelbild::getId).setCaption("id");
        grid.addColumn(Artikelbild::getTitel).setCaption("Titel");
        grid.addColumn(Artikelbild::getSize).setCaption("Size");
        grid.addColumn(Artikelbild::getMimetype).setCaption("Mimetype");

//        grid.addColumn(bild -> bild.getArtikel().getBezeichnung() + " id:" + bild.getArtikel().getId(),
//                new ButtonRenderer(event -> {
//                    Artikelbild bild = (Artikelbild) event.getItem();
//                    UI.getCurrent().getNavigator().navigateTo("Artikel/id/" + bild.getArtikel().getId());
//                })
//        ).setCaption("Artikel").setStyleGenerator(item -> "v-align-center");

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

        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    public void updateList() {
        if (!filterTextTitel.isEmpty()) {
            //Suche mit Titel
            logger.debug("Suche mit Titel:" + filterTextTitel.getValue());
            grid.setItems(artikelbildDeltaspikeFacade.findByTitelLikeIgnoreCase(filterTextTitel.getValue() + "%"));
            return;
        }
        grid.setItems(artikelbildDeltaspikeFacade.findAll());
    }

}
