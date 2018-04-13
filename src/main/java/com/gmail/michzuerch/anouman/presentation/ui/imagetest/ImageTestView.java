package com.gmail.michzuerch.anouman.presentation.ui.imagetest;

import com.gmail.michzuerch.anouman.backend.entity.ImageTest;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ImageTestDeltaspikeFacade;
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
@CDIView("ImageTestView")
public class ImageTestView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ImageTestView.class.getName());

    TextField filterTextTitel = new TextField();
    Grid<ImageTest> grid = new Grid<>();

    @Inject
    private ImageTestDeltaspikeFacade imageTestDeltaspikeFacade;

    @Inject
    private ImageTestForm imageTestForm;

    private Component createContent() {
        HorizontalLayout layout = new HorizontalLayout();

        filterTextTitel.setPlaceholder("Filter Titel");
        filterTextTitel.addValueChangeListener(e -> updateList());
        filterTextTitel.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextTitel.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            ImageTest imageTest = new ImageTest();
//            try {
//                imageTest.setBild(IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("/EmptyFieldValues/EmptyImage.jpg")));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
            imageTestForm.setEntity(imageTest);
            imageTestForm.openInModalPopup();

            imageTestForm.setSavedHandler(val -> {
                System.err.println("savedHandler bild len:" + val.getBild().length);
                imageTestDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                imageTestForm.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(
                filterTextTitel, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        grid.addColumn(ImageTest::getId).setCaption("id");
        grid.addColumn(ImageTest::getTitel).setCaption("Titel");
        grid.addColumn(imageTest -> imageTest.getBild().length).setCaption("Length");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(imageTest1 -> "löschen",
                new ButtonRenderer(event -> {
                    ImageTest imageTest = (ImageTest) event.getItem();
                    Notification.show("Lösche ImageTest id:" + imageTest, Notification.Type.HUMANIZED_MESSAGE);
                    imageTestDeltaspikeFacade.delete(imageTest);
                    updateList();
                })
        );

        grid.addColumn(imageTest -> "ändern",
                new ButtonRenderer(event -> {
                    imageTestForm.setEntity((ImageTest) event.getItem());
                    imageTestForm.openInModalPopup();
                    imageTestForm.setSavedHandler(val -> {
                        imageTestDeltaspikeFacade.save(val);
                        updateList();
                        grid.select(val);
                        imageTestForm.closePopup();
                    });
                    imageTestForm.setResetHandler(val -> {
                        updateList();
                        grid.select(val);
                        imageTestForm.closePopup();
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
                grid.select(imageTestDeltaspikeFacade.findBy(id));
            }
        }

        updateList();
    }

    public void updateList() {
        if (!filterTextTitel.isEmpty()) {
            //Suche mit Titel
            logger.debug("Suche mit Titel:" + filterTextTitel.getValue());
            grid.setItems(imageTestDeltaspikeFacade.findByTitelLikeIgnoreCase(filterTextTitel.getValue() + "%"));
            return;
        }
        grid.setItems(imageTestDeltaspikeFacade.findAll());
    }

}
