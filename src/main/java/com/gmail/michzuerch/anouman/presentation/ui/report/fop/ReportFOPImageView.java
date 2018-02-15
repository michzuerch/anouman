package com.gmail.michzuerch.anouman.presentation.ui.report.fop;

import com.gmail.michzuerch.anouman.backend.entity.report.fop.ReportFOP;
import com.gmail.michzuerch.anouman.backend.entity.report.fop.ReportFOPImage;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportFOPDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportFOPImageDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.Menu;
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

@CDIView("ReportFOPImageView")
public class ReportFOPImageView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportFOPImageView.class.getName());

    Grid<ReportFOPImage> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    @Inject
    private Menu menu;

    @Inject
    private ReportFOPDeltaspikeFacade reportFOPDeltaspikeFacade;

    @Inject
    private ReportFOPImageDeltaspikeFacade reportFOPImageDeltaspikeFacade;

    @Inject
    private ReportFOPImageForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            ReportFOPImage reportFOPImage = new ReportFOPImage();
            form.setEntity(reportFOPImage);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                System.err.println("Save:" + val);
                //reportFOPImageDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });


        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Report FOP Bild");
        grid.setCaptionAsHtml(true);
        grid.addColumn(ReportFOPImage::getId).setCaption("id");
        grid.addColumn(ReportFOPImage::getBezeichnung).setCaption("Bezeichnung");
        //grid.addColumn(ReportFOPImage::getSize).setCaption("Grösse");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    ReportFOPImage reportFOPImage = (ReportFOPImage) event.getItem();
                    ReportFOP reportFOP = reportFOPDeltaspikeFacade.findBy(reportFOPImage.getReportFOP().getId());
                    reportFOP.getReportFOPImages().remove(reportFOPImage);
                    reportFOPDeltaspikeFacade.save(reportFOP);
                    reportFOPImageDeltaspikeFacade.delete((ReportFOPImage) event.getItem());
                    updateList();
                    Notification.show("Lösche id:" + reportFOPImage.getId(), Notification.Type.HUMANIZED_MESSAGE);
                })
        );
        grid.addColumn(report -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((ReportFOPImage) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        //val.setTemplateCompiled(form.getCompiledReport());
                        reportFOPImageDeltaspikeFacade.save(val);
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

        //@todo Downloadbutton für Report
        grid.setSizeFull();
        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    private void updateList() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(reportFOPImageDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(reportFOPImageDeltaspikeFacade.findAll());
    }
}
