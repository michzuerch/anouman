package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.label.RichText;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Theme("anouman")
@CDIView("About")
public class AboutView extends VerticalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(AboutView.class.getName());

    @Inject
    Menu menu;

    @PostConstruct
    void init() {
        logger.debug("AboutView init");
        RichText richText = new RichText().withMarkDownResource("/about.md");
        addComponents(menu);
        addComponentsAndExpand(richText);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        logger.debug("AboutView enter");
    }
}
