package ch.internettechnik.anouman.presentation.ui.rechnung;

import ch.internettechnik.anouman.presentation.ui.AnoumanUI;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

@CDIView("RechnungEditOpenerView")
public class RechnungEditOpenerView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AnoumanUI.class.getName());

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        BrowserFrame browser = new BrowserFrame("Browser",
                new ExternalResource("/Anouman/EditableInvoice/index.html"));
        browser.setSizeFull();
        //browser.setWidth("600px");
        //browser.setHeight("400px");
        addComponentsAndExpand(browser);
    }
}
