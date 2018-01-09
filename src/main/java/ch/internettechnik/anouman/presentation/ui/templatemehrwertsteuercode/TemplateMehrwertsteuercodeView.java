package ch.internettechnik.anouman.presentation.ui.templatemehrwertsteuercode;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateKontoDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateMehrwertsteuercodeDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringView(name = "TemplateMehrwertsteuercodeView")
public class TemplateMehrwertsteuercodeView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateMehrwertsteuercodeView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    ComboBox<TemplateBuchhaltung> filterTemplateBuchhaltung = new ComboBox<>();

    Grid<TemplateMehrwertsteuercode> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private TemplateMehrwertsteuercodeDeltaspikeFacade facade;

    @Inject
    private TemplateBuchhaltungDeltaspikeFacade templateBuchhaltungDeltaspikeFacade;

    @Inject
    private TemplateKontoDeltaspikeFacade templateKontoDeltaspikeFacade;

    @Inject
    private TemplateMehrwertsteuercodeForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung...");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        filterTemplateBuchhaltung.setPlaceholder("Filter für Template Buchhaltung");
        filterTemplateBuchhaltung.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
        filterTemplateBuchhaltung.setItemCaptionGenerator(item -> item.getBezeichnung() + " id:" + item.getId());
        filterTemplateBuchhaltung.addValueChangeListener(valueChangeEvent -> updateList());

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTemplateBuchhaltung.clear();
            filterTextBezeichnung.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            TemplateMehrwertsteuercode templateMehrwertsteuercode = new TemplateMehrwertsteuercode();
            TemplateBuchhaltung templateBuchhaltung = templateBuchhaltungDeltaspikeFacade.findAll().get(0);
            templateMehrwertsteuercode.setTemplateBuchhaltung(templateBuchhaltung);
            templateMehrwertsteuercode.setProzent(8f);
            templateMehrwertsteuercode.setTemplateMehrwertsteuerKonto(createTemplateKontoList(templateBuchhaltung).get(0));

            if (!filterTemplateBuchhaltung.isEmpty())
                templateMehrwertsteuercode.setTemplateBuchhaltung(filterTemplateBuchhaltung.getValue());
            System.err.println(templateMehrwertsteuercode);
            form.setEntity(templateMehrwertsteuercode);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });


        CssLayout tools = new CssLayout();
        tools.addComponents(filterTemplateBuchhaltung, filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Template Mehrwertsteuercode");
        grid.setCaptionAsHtml(true);
        grid.addColumn(TemplateMehrwertsteuercode::getId).setCaption("id");
        grid.addColumn(TemplateMehrwertsteuercode::getCode).setCaption("Code");
        grid.addColumn(TemplateMehrwertsteuercode::getProzent).setCaption("Prozent");
        grid.addColumn(TemplateMehrwertsteuercode::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(templateMehrwertsteuercode -> templateMehrwertsteuercode.getTemplateBuchhaltung().getId()).setCaption("Template Buchhaltung");
        grid.addColumn(templateMehrwertsteuercode -> templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getId()).setCaption("Konto");
        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(adresse -> "löschen",
                new ButtonRenderer(event -> {
                    TemplateMehrwertsteuercode mehrwertsteuercode = (TemplateMehrwertsteuercode) event.getItem();
                    mehrwertsteuercode = facade.findBy(mehrwertsteuercode.getId());
                    TemplateBuchhaltung buchhaltung = mehrwertsteuercode.getTemplateBuchhaltung();
                    buchhaltung.getTemplateMehrwertsteuercodes().remove(mehrwertsteuercode);
                    templateBuchhaltungDeltaspikeFacade.save(buchhaltung);
                    Notification.show("Lösche Template Mehrwertsteuercode id:" + mehrwertsteuercode.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete(mehrwertsteuercode);
                    updateList();
                })
        );

        grid.addColumn(buchhaltung -> "ändern",
                new ButtonRenderer(event -> {
                    System.err.println(event.getItem());
                    form.setEntity((TemplateMehrwertsteuercode) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        facade.save(val);
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

    private List<TemplateKonto> createTemplateKontoList(TemplateBuchhaltung buchhaltung) {
        List<TemplateKonto> list = new ArrayList<>();

        buchhaltung.getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
            templateKontoklasse.getTemplateKontohauptgruppes().stream().forEach(templateKontohauptgruppe -> {
                templateKontohauptgruppe.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                    templateKontogruppe.getTemplateKontos().stream().forEach(templateKonto -> {
                        list.add(templateKonto);
                    });
                });
            });
        });
        return list;
    }

    public void updateList() {
        if (!filterTemplateBuchhaltung.isEmpty()) {
            logger.debug("Suche mit Template Buchhaltung");
            grid.setItems(facade.findByTemplateBuchhaltung(filterTemplateBuchhaltung.getValue()));
        } else if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        } else
            grid.setItems(facade.findAll());
    }

}
