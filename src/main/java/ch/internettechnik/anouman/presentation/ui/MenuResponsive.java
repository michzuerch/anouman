package ch.internettechnik.anouman.presentation.ui;

import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.builder.design.AppBarDesign;
import com.github.appreciated.app.layout.builder.elements.SubmenuBuilder;
import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.github.appreciated.app.layout.component.NavigationBadgeButton;
import com.github.appreciated.app.layout.component.NavigationButton;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import static com.github.appreciated.app.layout.builder.AppLayoutBuilder.Position.HEADER;

public class MenuResponsive extends CustomComponent {
    DefaultBadgeHolder badge = new DefaultBadgeHolder();
    private VerticalLayout layout;

    public MenuResponsive() {
        layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSizeFull();

        AppLayout drawer = AppLayoutBuilder.get()
                .withBehaviour(Behaviour.LEFT)
                .withTitle("Anouman")
                //.addToAppBar()
                //.withViewNameInterceptor(new DefaultViewNameInterceptor())
                //.withDefaultNavigationView(AboutView.class)
                .withDesign(AppBarDesign.MATERIAL)
                .add(new MenuHeader("Anouman", "Version 1.0", new ThemeResource("img/pc-medium.png")), HEADER)
                .add(new NavigationBadgeButton("Home", VaadinIcons.HOME, badge))
                .add(new NavigationButton("About", VaadinIcons.HOME, clickEvent -> {
                    getUI().getNavigator().navigateTo("About");
                }))
                .add(
                        SubmenuBuilder.get("Systemmenü", VaadinIcons.HAMMER)
                                .add(new NavigationButton("Benutzer", VaadinIcons.CONNECT))
                                .add(new NavigationButton("Benutzerrollen", VaadinIcons.PLUS))
                                .add(new NavigationButton("Benutzer zu Rollen zuordnen", VaadinIcons.MENU))
                                .add(new NavigationButton("Backup", VaadinIcons.MENU, clickEvent -> getUI().getNavigator().navigateTo("Backup")))
                                .build())
                .add(
                        SubmenuBuilder.get("Reporting", VaadinIcons.RETWEET)
                                .add(new NavigationButton("Report Vorlagen (Jasperreports)", VaadinIcons.REPLY))
                                .build())
                .add(
                        SubmenuBuilder.get("Rechnungen", VaadinIcons.RETWEET)
                                .add(new NavigationButton("Report Vorlagen (Jasperreports)", VaadinIcons.REPLY))
                                .build())
                .add(
                        SubmenuBuilder.get("Buchhaltungen", VaadinIcons.RETWEET)
                                .add(new NavigationButton("Report Vorlagen (Jasperreports)", VaadinIcons.REPLY))
                                .build())
                .add(
                        SubmenuBuilder.get("Tools", VaadinIcons.RETWEET)
                                .add(new NavigationButton("Report Vorlagen (Jasperreports)", VaadinIcons.REPLY))
                                .build())
                .add(
                        SubmenuBuilder.get("Tests", VaadinIcons.RETWEET)
                                .add(new NavigationButton("Report Vorlagen (Jasperreports)", VaadinIcons.REPLY))
                                .build())

                .build();
        layout.addComponent(drawer);
        setCompositionRoot(layout);
    }
}
