package com.gmail.michzuerch.anouman.servlet;

import com.gmail.michzuerch.anouman.presentation.ui.AnoumanUI;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.server.VaadinCDIServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "AnoumanServlet", asyncSupported = true, value = {"/ui/*", "/VAADIN/*"})
@VaadinServletConfiguration(ui = AnoumanUI.class, productionMode = false)
public class AnoumanServlet extends VaadinCDIServlet {
}
