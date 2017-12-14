package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@Theme("anouman")
@CDIView("ImageTest")
public class ImageTestView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ImageTestView.class.getName());

    @Inject
    Menu menu;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        Image image = new Image();
        image.setSource(new ClassResource("/header.jpg"));
        image.setWidth(900, Unit.PIXELS);
        addComponent(menu);
        addComponent(image);
    }
}
