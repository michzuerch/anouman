package com.gmail.michzuerch.anouman.frontend.page;

import com.gmail.michzuerch.TeachersAssistant.frontend.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class HomePage extends VerticalLayout {
    private static final long serialVersionUID = 1337339618913376736L;

    public HomePage() {
        add(new H2("Home"), new H5("Home view"));
    }
}