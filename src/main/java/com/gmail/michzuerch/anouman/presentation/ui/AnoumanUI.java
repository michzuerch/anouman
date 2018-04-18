package com.gmail.michzuerch.anouman.presentation.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by michzuerch on 09.05.17.
 */
@Theme("anouman")
@Title("Anouman")
@Push(PushMode.AUTOMATIC)
@PreserveOnRefresh
@SuppressWarnings("serial")
@CDIUI("")
public class AnoumanUI extends UI {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AnoumanUI.class.getName());

    @Inject
    private CDIViewProvider cdiViewProvider;

    @Override
    protected void init(VaadinRequest request) {
        HorizontalLayout container = new HorizontalLayout();

        Navigator navigator = new Navigator(this, container);
        navigator.addProvider(cdiViewProvider);

        HorizontalLayout naviBar = new HorizontalLayout();
        naviBar.addComponents(new Menu());

        VerticalLayout content = new VerticalLayout(naviBar, container);
        content.setSizeFull();
        setContent(content);
        setSizeFull();
        container.setSizeFull();
        content.setExpandRatio(container, 1.0f);
        navigator.navigateTo("AboutView");
    }
}