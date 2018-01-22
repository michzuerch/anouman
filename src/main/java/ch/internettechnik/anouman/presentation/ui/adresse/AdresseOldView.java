package ch.internettechnik.anouman.presentation.ui.adresse;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView("AdresseOldView")
public class AdresseOldView extends VerticalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(AdresseOldView.class.getName());

    TextField filterTextFirma = new TextField();
    TextField filterTextNachname = new TextField();
    TextField filterTextOrt = new TextField();

    Grid<Adresse> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private AdresseDeltaspikeFacade facade;

    @Inject
    private AdresseForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
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
                grid.select(facade.findBy(id));
            }
        }

        setStyleName("anouman-background");

        filterTextFirma.setPlaceholder("Filter für Firma");
        filterTextFirma.addValueChangeListener(e -> updateList());
        filterTextFirma.setValueChangeMode(ValueChangeMode.LAZY);

        filterTextNachname.setPlaceholder("Filter für Nachname");
        filterTextNachname.addValueChangeListener(e -> updateList());
        filterTextNachname.setValueChangeMode(ValueChangeMode.LAZY);

        filterTextOrt.setPlaceholder("Filter für Ort");
        filterTextOrt.addValueChangeListener(e -> updateList());
        filterTextOrt.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextFirma.clear();
            filterTextNachname.clear();
            filterTextOrt.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            Adresse addr = new Adresse();
            addr.setStundensatz(130d);
            form.setEntity(addr);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextFirma, filterTextNachname, filterTextOrt, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Adresse");
        grid.setCaptionAsHtml(true);
        grid.addColumn(Adresse::getId).setCaption("id");
        grid.addColumn(Adresse::getFirma).setCaption("Firma");
        grid.addColumn(Adresse::getNachname).setCaption("Nachname");
        grid.addColumn(Adresse::getOrt).setCaption("Ort");


        grid.addColumn(adresse -> adresse.getAnzahlRechnungen(), new ButtonRenderer(event -> {
            Adresse adresse = (Adresse) event.getItem();
            if (adresse.getAnzahlRechnungen() > 0) {
                UI.getCurrent().getNavigator().navigateTo("RechnungOldView/adresseId/" + adresse.getId().toString());
            }
        })).setCaption("Anzahl Rechnungen").setStyleGenerator(item -> "v-align-center");
        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(adresse -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Adresse id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((Adresse) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(adresse -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((Adresse) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        facade.save(val);
                        System.err.println("Adr:" + val);
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                    form.setResetHandler(val -> {
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                }));

        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    public void updateList() {
        if ((!filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Firma und Nachname und Ort
            logger.debug("Suche mit Firma und Nachname und Ort:" + filterTextFirma.getValue() + "," + filterTextNachname.getValue() + "," + filterTextOrt.getValue());
            grid.setItems(facade.findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(
                    filterTextFirma.getValue() + "%", filterTextNachname.getValue() + "%", filterTextOrt.getValue() + "%"));
            return;
        } else if ((!filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (filterTextOrt.isEmpty())) {
            //Suche mit Firma und Nachname
            logger.debug("Suche mit Firma und Nachname:" + filterTextFirma.getValue() + "," + filterTextNachname.getValue());
            grid.setItems(facade.findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCase(filterTextFirma.getValue() + "%", filterTextNachname.getValue() + "%"));
            return;
        } else if ((!filterTextFirma.isEmpty()) && (filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Firma und Ort
            logger.debug("Suche mit Firma und Ort:" + filterTextFirma.getValue() + "," + filterTextOrt.getValue());
            grid.setItems(facade.findByFirmaLikeIgnoreCaseAndOrtLikeIgnoreCase(filterTextFirma.getValue() + "%", filterTextOrt.getValue() + "%"));
            return;
        } else if ((filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (filterTextOrt.isEmpty())) {
            //Suche mit Nachname
            logger.debug("Suche mit Nachname:" + filterTextNachname.getValue());
            grid.setItems(facade.findByNachnameLikeIgnoreCase(filterTextNachname.getValue() + "%"));
            return;
        } else if ((filterTextFirma.isEmpty()) && (filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Ort
            logger.debug("Suche mit Ort:" + filterTextOrt.getValue());
            grid.setItems(facade.findByOrtLikeIgnoreCase(filterTextOrt.getValue() + "%"));
            return;
        } else if ((!filterTextFirma.isEmpty()) && (filterTextNachname.isEmpty()) && (filterTextOrt.isEmpty())) {
            //Suche mit Firma
            logger.debug("Suche mit Firma:" + filterTextFirma.getValue());
            grid.setItems(facade.findByFirmaLikeIgnoreCase(filterTextFirma.getValue() + "%"));
            return;
        } else if ((filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Nachname und Ort
            logger.debug("Suche mit Nachname und Ort:" + filterTextNachname.getValue() + "," + filterTextOrt.getValue());
            grid.setItems(facade.findByNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(
                    filterTextNachname.getValue() + "%", filterTextOrt.getValue() + "%"));
            return;
        }

        grid.setItems(facade.findAll());
    }

}
