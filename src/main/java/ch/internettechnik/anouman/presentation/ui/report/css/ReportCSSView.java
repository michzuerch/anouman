package ch.internettechnik.anouman.presentation.ui.report.css;

import ch.internettechnik.anouman.backend.entity.report.css.ReportCSS;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportCSSDeltaspikeFacade;
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
import java.io.File;

@CDIView("ReportCSSView")
public class ReportCSSView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportCSSView.class.getName());

    File tempFile;
    String filename;

    Grid<ReportCSS> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    @Inject
    private Menu menu;

    @Inject
    private ReportCSSDeltaspikeFacade facade;

    @Inject
    private ReportCSSForm form;

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
            ReportCSS reportCSS = new ReportCSS();
            form.setEntity(reportCSS);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Report CSS");
        grid.setCaptionAsHtml(true);
        grid.addColumn(ReportCSS::getId).setCaption("id");
        grid.addColumn(ReportCSS::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(ReportCSS::getFilename).setCaption("Dateiname");
        grid.addColumn(ReportCSS::getSizeCSS).setCaption("CSS Grösse");
        grid.addColumn(ReportCSS::getSizeHTML).setCaption("HTML Grösse");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((ReportCSS) event.getItem());
                    updateList();
                })
        );

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
            grid.setItems(facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(facade.findAll());
    }
}
