package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.UI;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by michzuerch on 09.05.17.
 */
@Theme("anouman")
@Title("Anouman")
@Push(PushMode.AUTOMATIC)
@SuppressWarnings("serial")
@CDIUI("")
public class AnoumanUI extends UI {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AnoumanUI.class.getName());

    @Inject
    private CDIViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.navigateTo("About");
    }
}

