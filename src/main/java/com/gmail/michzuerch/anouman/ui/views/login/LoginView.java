package com.gmail.michzuerch.anouman.ui.views.login;

import com.gmail.michzuerch.anouman.app.HasLogger;
import com.gmail.michzuerch.anouman.app.security.SecurityUtils;
import com.gmail.michzuerch.anouman.ui.i18n.I18nConst;
import com.gmail.michzuerch.anouman.ui.views.address.AddressView;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.*;

@Route
@PageTitle("Anouman")
@JsModule("./styles/shared-styles.js")
@Viewport(I18nConst.VIEWPORT)
public class LoginView extends LoginOverlay
        implements AfterNavigationObserver, BeforeEnterObserver, HasLogger {

    private static final long serialVersionUID = 1L;

    public LoginView() {
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Anouman");
        i18n.getHeader().setDescription(
                "admin@michzuerch.gmail.com + admin\n");
        i18n.setAdditionalInformation(null);
        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setSubmit("Sign in");
        i18n.getForm().setTitle("Sign in");
        i18n.getForm().setUsername("Email");
        i18n.getForm().setPassword("Password");
        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        setAction("login");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecurityUtils.isUserLoggedIn()) {
            getLogger().debug("Logged in User:" + SecurityUtils.getUsername());
            event.forwardTo(AddressView.class);
        } else {
            setOpened(true);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        setError(
                event.getLocation().getQueryParameters().getParameters().containsKey(
                        "error"));
    }

}
