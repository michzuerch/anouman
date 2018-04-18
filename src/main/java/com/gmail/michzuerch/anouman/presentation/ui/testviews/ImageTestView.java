package com.gmail.michzuerch.anouman.presentation.ui.testviews;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

@CDIView("ImageTest2View")
public class ImageTestView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ImageTestView.class.getName());

    private void createContent() {
        HorizontalLayout layout = new HorizontalLayout();

        Image image = new Image();
        image.setSource(new ClassResource("/header.jpg"));
        image.setWidth(900, Unit.PIXELS);

        image.setSizeFull();
        setMargin(false);
        setSpacing(false);
        addComponents(image);
        setExpandRatio(image, 1);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        createContent();
        setSizeFull();
    }
}
