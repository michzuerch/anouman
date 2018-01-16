package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractView extends HorizontalLayout implements View {
    public AbstractView() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        Label label = new Label("michzuerch modification < " + getViewName() + " >");
        label.addStyleNames(ValoTheme.LABEL_H2, ValoTheme.LABEL_NO_MARGIN);
        layout.addComponent(label);
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        Panel panel = new Panel(layout);
        panel.setSizeFull();
        addComponent(panel);
        setMargin(true);
        setSizeFull();
    }

    abstract String getViewName();
}