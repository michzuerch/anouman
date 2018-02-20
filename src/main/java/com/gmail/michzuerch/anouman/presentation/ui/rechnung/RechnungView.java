package com.gmail.michzuerch.anouman.presentation.ui.rechnung;

import com.gmail.michzuerch.anouman.backend.entity.Adresse;
import com.gmail.michzuerch.anouman.backend.entity.Aufwand;
import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.entity.Rechnungsposition;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.*;
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

@CDIView("RechnungView")
public class RechnungView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RechnungView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    ComboBox<Adresse> filterAdresse = new ComboBox<>();
    Grid<Rechnung> grid = new Grid<>();

    @Inject
    private RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    private RechnungspositionDeltaspikeFacade rechnungspositionDeltaspikeFacade;

    @Inject
    private AufwandDeltaspikeFacade aufwandDeltaspikeFacade;

    @Inject
    private AdresseDeltaspikeFacade adresseDeltaspikeFacade;

    @Inject
    private ReportJasperDeltaspikeFacade reportJasperDeltaspikeFacade;

    @Inject
    private RechnungForm form;

    @Inject
    private RechnungPrintWindow rechnungPrintWindow;

    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Row);
        layout.setAlignItems(AlignItems.FlexEnd);
        layout.setJustifyContent(JustifyContent.SpaceBetween);
        layout.setAlignContent(AlignContent.Stretch);
        layout.setFlexWrap(FlexWrap.Wrap);

        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);
        filterAdresse.setPlaceholder("Filter für Adresse");
        filterAdresse.setItems(adresseDeltaspikeFacade.findAll());
        filterAdresse.setItemCaptionGenerator(item -> item.getFirma() + " " + item.getNachname() + " " + item.getOrt() + " id:" + item.getId());
        filterAdresse.addValueChangeListener(valueChangeEvent -> updateList());

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
            filterAdresse.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            Rechnung val = new Rechnung();
            val.setAdresse(adresseDeltaspikeFacade.findAll().get(0));
            if (!filterAdresse.isEmpty()) val.setAdresse(filterAdresse.getValue());
            val.setFaelligInTagen(30);
            form.setEntity(val);
            form.openInModalPopup();
            form.setSavedHandler(rechnung -> {
                rechnungDeltaspikeFacade.save(rechnung);
                updateList();
                grid.select(rechnung);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterAdresse, filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setWidth(50, Unit.PERCENTAGE);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Rechnung");
        grid.setCaptionAsHtml(true);
        grid.addColumn(Rechnung::getId).setCaption("id");
        grid.addColumn(Rechnung::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Rechnung::getRechnungsdatum).setCaption("Rechnungdatum");
        grid.addColumn(Rechnung::getFaelligInTagen).setCaption("Fällig in Tagen");
        grid.addColumn(Rechnung::getRechnungstotal).setCaption("Total").setStyleGenerator(item -> "v-align-right");
        grid.addColumn(Rechnung::isBezahlt).setCaption("Bezahlt");
        grid.addColumn(Rechnung::isVerschickt).setCaption("Verschickt");
        grid.addColumn(rechnung -> rechnung.getAdresse().getFirma() + " " +
                        rechnung.getAdresse().getNachname() + " " + rechnung.getAdresse().getOrt() + " id:" + rechnung.getAdresse().getId(),
                new ButtonRenderer(event -> {
                    Rechnung rechnung = (Rechnung) event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("AdresseView/id/" + rechnung.getAdresse().getId());
                })
        ).setCaption("Adresse").setStyleGenerator(item -> "v-align-center");

        grid.addColumn(rechnung -> rechnung.getAnzahlRechnungspositionen(), new ButtonRenderer(event -> {
            Rechnung rechnung = (Rechnung) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("RechnungspositionView/rechnungId/" + rechnung.getId());
        })).setCaption("Rechnungspositionen").setStyleGenerator(item -> "v-align-center");

        grid.addColumn(rechnung -> rechnung.getAnzahlAufwands(), new ButtonRenderer(event -> {
            Rechnung rechnung = (Rechnung) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("AufwandView/rechnungId/" + rechnung.getId());
        })).setCaption("Aufwand").setStyleGenerator(item -> "v-align-center");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(rechnung -> "löschen",
                new ButtonRenderer(event -> {
                    Rechnung rechnung = (Rechnung) event.getItem();
                    rechnung = rechnungDeltaspikeFacade.findBy(rechnung.getId());

                    for (Rechnungsposition rp : rechnung.getRechnungspositionen()) {
                        rechnungspositionDeltaspikeFacade.delete(rp);

                    }

                    for (Aufwand aw : rechnung.getAufwands()) {
                        aufwandDeltaspikeFacade.delete(aw);
                    }

                    rechnungDeltaspikeFacade.delete(rechnung);

                    Notification.show("Lösche Rechnung id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    updateList();
                })
        );

        grid.addColumn(rechnung -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((Rechnung) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(rechnung -> {
                        rechnungDeltaspikeFacade.save(rechnung);
                        updateList();
                        grid.select(rechnung);
                        form.closePopup();
                    });
                    form.setResetHandler(rechnung -> {
                        updateList();
                        grid.select(rechnung);
                        form.closePopup();
                    });
                }));

        /*
        grid.addColumn(rechnung -> "print "+rechnung.getId(), new ButtonRenderer(event -> {
            rechnungPrintWindow.setRechnung((Rechnung) event.getItem());
            rechnungPrintWindow.lazyInit();
            rechnungPrintWindow.openInModalPopup();

        }));
        */

        grid.addColumn(rechnung -> "Print", new ButtonRenderer(rendererClickEvent -> {
            Rechnung val = (Rechnung) rendererClickEvent.getItem();
            getUI().getNavigator().navigateTo("RechnungPrintView/id/" + val.getId());
        }));

        grid.addColumn(rechnung -> "detail", new ButtonRenderer(rendererClickEvent -> {
            Rechnung val = (Rechnung) rendererClickEvent.getItem();
            getUI().getNavigator().navigateTo("RechnungDetailView/id/" + val.getId());
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
            if (target.equals("adresseId")) {
                filterAdresse.setSelectedItem(adresseDeltaspikeFacade.findBy(id));
                updateList();
            } else if (target.equals("id")) {
                grid.select(rechnungDeltaspikeFacade.findBy(id));
            }
        }

        updateList();
    }

    public void updateList() {
        if ((!filterAdresse.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Adresse und Bezeichnung
            logger.debug("Suche mit Adresse und Bezeichnung:" + filterAdresse.getValue().getId() + "," + filterTextBezeichnung.getValue());
            grid.setItems(
                    rechnungDeltaspikeFacade.findByAdresseAndBezeichnungLikeIgnoreCase(
                            filterAdresse.getValue(), filterTextBezeichnung.getValue() + "%"));
            return;
        } else if ((filterAdresse.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(rechnungDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        } else if ((!filterAdresse.isEmpty()) && (filterTextBezeichnung.isEmpty())) {
            //Suche mit Adresse
            logger.debug("Suche mit Adresse:" + filterAdresse.getValue());
            grid.setItems(rechnungDeltaspikeFacade.findByAdresse(filterAdresse.getValue()));
            return;
        }
        grid.setItems(rechnungDeltaspikeFacade.findAll());
    }
}
