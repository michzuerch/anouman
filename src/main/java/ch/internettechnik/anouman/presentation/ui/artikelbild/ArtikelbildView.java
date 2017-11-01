package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildFacade;
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
@CDIView(value = "Artikelbild")
public class ArtikelbildView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ArtikelbildView.class.getName());

    TextField filterTextTitel = new TextField();
    Grid<Artikelbild> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private ArtikelbildFacade artikelbildFacade;

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
                grid.select(artikelbildFacade.findBy(id));
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
                artikelbildFacade.save(val);
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

//        grid.addColumn(artikelbild -> artikelbild.getArtikel().getBezeichnung() + " id:" + artikelbild.getArtikel().getId(),
//                new ButtonRenderer(event -> {
//                    Artikelbild artikelbild = (Artikelbild) event.getItem();
//                    UI.getCurrent().getNavigator().navigateTo("Artikel/id/" + artikelbild.getArtikel().getId());
//                })
//        ).setCaption("Artikel").setStyleGenerator(item -> "v-align-center");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(artikelbild -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Artikelbild id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    artikelbildFacade.delete((Artikelbild) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(adresse -> "ändern",
                new ButtonRenderer(event -> {
                    artikelbildForm.setEntity((Artikelbild) event.getItem());
                    artikelbildForm.openInModalPopup();
                    artikelbildForm.setSavedHandler(val -> {
                        artikelbildFacade.save(val);
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
            grid.setItems(artikelbildFacade.findByTitelLikeIgnoreCase(filterTextTitel.getValue() + "%"));
            return;
        }
        grid.setItems(artikelbildFacade.findAll());
    }

}
