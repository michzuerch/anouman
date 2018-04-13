package com.gmail.michzuerch.anouman.presentation.ui.testviews;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ForkJoinPool;

@CDIView("PushTestView")
public class PushTestView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PushTestView.class.getName());

    private Component createContent() {
        HorizontalLayout layout = new HorizontalLayout();


        final Label labelTime = new Label("???");

        labelTime.addStyleName("h1");
        // now in a background thread we constantly update the time
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        ForkJoinPool.commonPool().submit(() -> {
            boolean keepGoing = true;
            while (keepGoing) {
                getUI().access(() -> labelTime.setValue(LocalTime.now().format(dateTimeFormatter)));
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    keepGoing = false;
                }
            }
        });
        layout.addComponents(labelTime);
        layout.setSizeFull();
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(createContent());
        setSizeFull();
    }
}
