package ch.internettechnik.anouman.servlet;

import ch.internettechnik.anouman.presentation.ui.AnoumanUI;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Constants;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.server.SpringVaadinServlet;

import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Created by michzuerch on 09.05.17.
 */

@WebServlet(asyncSupported = true, urlPatterns = {"/ui/*", "/VAADIN/*"}, name = "AnoumanServlet",
        initParams = {
                @WebInitParam(name = VaadinSession.UI_PARAMETER, value = "ch.internettechnik.anouman.presentation.ui.AnoumanUI"),
//                @WebInitParam(name = Constants.SERVLET_PARAMETER_UI_PROVIDER, value = "com.vaadin.spring.server.SpringUIProvider"),
                @WebInitParam(name = Constants.SERVLET_PARAMETER_PRODUCTION_MODE, value = "false"),
                @WebInitParam(name = Constants.SERVLET_PARAMETER_PUSH_MODE, value = "automatic")
        })

@VaadinServletConfiguration(productionMode = false,
        ui = AnoumanUI.class)

@PersistenceContext(name = "persistence/em", unitName = "AnoumanPU")
public class AnoumanServlet extends SpringVaadinServlet {
}
