package com.gmail.michzuerch.anouman.presentation.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.teemusa.flexlayout.*;
import org.vaadin.viritin.label.RichText;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Theme("anouman")
@CDIView("AboutView")
public class AboutView extends HorizontalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(AboutView.class.getName());

    @Inject
    Menu menu;


    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Column);
        layout.setAlignItems(AlignItems.FlexStart);
        layout.setJustifyContent(JustifyContent.SpaceAround);
        layout.setAlignContent(AlignContent.FlexStart);
        layout.setFlexWrap(FlexWrap.Nowrap);

        layout.addComponents(new RichText().withMarkDownResource("/about.md"));
        layout.setSizeFull();
        return layout;
    }


    @PostConstruct
    void init() {
        logger.debug("AboutView init");
        addComponent(createContent());
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        logger.debug("AboutView enter");
    }
}
