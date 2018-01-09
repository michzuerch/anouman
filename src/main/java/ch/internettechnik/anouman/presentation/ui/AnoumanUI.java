package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;

/**
 * Created by michzuerch on 09.05.17.
 */
@Theme("anouman")
@Title("Anouman")
@Push(PushMode.AUTOMATIC)
@SuppressWarnings("serial")
@SpringUI
@SpringViewDisplay
public class AnoumanUI extends UI {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AnoumanUI.class.getName());

    @Autowired
    SpringViewProvider viewProvider;

    protected void init(VaadinRequest vaadinRequest) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);
        setContent(new Label("Hello Spring**"));
        //navigator.setErrorView(new ErrorView()); // You can still create the error view yourself if you want to.
        navigator.navigateTo("AboutView");
    }

//    @Inject
//    private CDIViewProvider viewProvider;

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
}