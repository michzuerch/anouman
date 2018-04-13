package com.gmail.michzuerch.anouman.presentation.ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.label.RichText;

@CDIView("AboutView")
public class AboutView extends HorizontalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(AboutView.class.getName());

    private Component createContent() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.addComponents(new RichText().withMarkDownResource("/about.md"));
        layout.setSizeFull();
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        logger.debug("AboutView enter");
        addComponent(createContent());
        setSizeFull();
    }
}
