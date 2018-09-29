package com.gmail.michzuerch.anouman.presentation.ui.report.css;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSS;
import com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSSImage;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportCSSDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportCSSImageDeltaspikeFacade;
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

@CDIView("ReportCSSImageView")
public class ReportCSSImageView extends HorizontalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportCSSImageView.class.getName());

    Grid<ReportCSSImage> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    @Inject
    private ReportCSSDeltaspikeFacade reportCSSDeltaspikeFacade;

    @Inject
    private ReportCSSImageDeltaspikeFacade reportCSSImageDeltaspikeFacade;

    @Inject
    private ReportCSSImageForm form;

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

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            ReportCSSImage reportCSSImage = new ReportCSSImage();
            form.setEntity(reportCSSImage);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                System.err.println("Save:" + val);
                //reportCSSImageDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });


        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.addColumn(ReportCSSImage::getId).setCaption("id");
        grid.addColumn(ReportCSSImage::getBezeichnung).setCaption("Bezeichnung");
        //grid.addColumn(ReportFOPImage::getSize).setCaption("Grösse");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    ReportCSSImage reportCSSImage = (ReportCSSImage) event.getItem();
                    ReportCSS reportCSS = reportCSSDeltaspikeFacade.findBy(reportCSSImage.getReportCSS().getId());
                    reportCSS.getReportCSSImages().remove(reportCSSImage);
                    reportCSSDeltaspikeFacade.save(reportCSS);
                    reportCSSImageDeltaspikeFacade.delete((ReportCSSImage) event.getItem());
                    updateList();
                    Notification.show("Lösche id:" + reportCSSImage.getId(), Notification.Type.HUMANIZED_MESSAGE);
                })
        );
        grid.addColumn(report -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((ReportCSSImage) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        reportCSSImageDeltaspikeFacade.save(val);
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

        layout.addComponents(tools, grid);
        layout.setSizeFull();
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(createContent());
        setSizeFull();

        updateList();
    }

    private void updateList() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(reportCSSImageDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(reportCSSImageDeltaspikeFacade.findAll());
    }
}
