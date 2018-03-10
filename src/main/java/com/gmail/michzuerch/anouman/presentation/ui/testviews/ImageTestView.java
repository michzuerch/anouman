package com.gmail.michzuerch.anouman.presentation.ui.testviews;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;
import org.vaadin.teemusa.flexlayout.*;

@CDIView("ImageTest2View")
public class ImageTestView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ImageTestView.class.getName());

    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Row);
        layout.setAlignItems(AlignItems.Center);
        layout.setJustifyContent(JustifyContent.SpaceBetween);
        layout.setAlignContent(AlignContent.Center);
        layout.setFlexWrap(FlexWrap.Wrap);

        Image image = new Image();
        image.setSource(new ClassResource("/header.jpg"));
        image.setWidth(900, Unit.PIXELS);

        layout.addComponents(image);
        layout.setSizeFull();
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(createContent());
        setSizeFull();
    }
}
