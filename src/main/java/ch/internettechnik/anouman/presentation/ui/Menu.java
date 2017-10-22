package ch.internettechnik.anouman.presentation.ui;


import com.vaadin.cdi.ViewScoped;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by michzuerch on 26.10.15.
 */

@ViewScoped
public class Menu extends CustomComponent {
    private static Logger logger = LoggerFactory.getLogger(Menu.class.getName());
    CssLayout layout = new CssLayout();

    public Menu() {
        ThemeResource resourceLogo = new ThemeResource("img/pc-small.png");
        MenuBar menuBar = new MenuBar();
        MenuBar.MenuItem aboutItem = menuBar.addItem("About", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("About");
            }
        });
        aboutItem.setIcon(resourceLogo);

        MenuBar.MenuItem administrationMenu = menuBar.addItem("Systemverwaltung", VaadinIcons.GAMEPAD, null);

        administrationMenu.addItem("Benutzer", VaadinIcons.USER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Uzer");
            }
        });
        administrationMenu.addItem("Benutzerrollen", VaadinIcons.DIPLOMA_SCROLL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("UzerRole");
            }
        });
        administrationMenu.addItem("Benutzer zu Rollen zuordnen", VaadinIcons.DIPLOMA, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("UzerToUzerRoleMapping");
            }
        });
        administrationMenu.addItem("Backup", VaadinIcons.DOWNLOAD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Backup");
            }
        });

        MenuBar.MenuItem reportingMenu = menuBar.addItem("Reporting", VaadinIcons.TAXI, null);
        reportingMenu.addItem("Report Vorlagen (Jasperreports)", VaadinIcons.GAVEL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ReportTemplate");
            }
        });

        MenuBar.MenuItem rechnungenMenu = menuBar.addItem("Rechnungen", null);
        rechnungenMenu.setIcon(VaadinIcons.OFFICE);

        MenuBar.MenuItem buchhaltungenMenu = menuBar.addItem("Buchhaltungen", null);
        buchhaltungenMenu.setIcon(VaadinIcons.PUZZLE_PIECE);

        MenuBar.MenuItem toolsItem = menuBar.addItem("Tools", null);
        toolsItem.setIcon(VaadinIcons.CARET_DOWN);
        toolsItem.addItem("Editable Invoice", VaadinIcons.INBOX, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("RechnungEditOpener");
            }
        });

        rechnungenMenu.addItem("Adressen", VaadinIcons.CALENDAR_CLOCK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Adresse");
            }
        });
        rechnungenMenu.addItem("Rechnungen", VaadinIcons.INVOICE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Rechnung");
            }
        });
        rechnungenMenu.addItem("Rechnungspositionen", VaadinIcons.PACKAGE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Rechnungsposition");
            }
        });

        MenuBar.MenuItem aufwandItem = rechnungenMenu.addItem("Aufwand", VaadinIcons.CAMERA, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Aufwand");
            }
        });

        MenuBar.MenuItem buchhaltungbuchhaltungMenu = buchhaltungenMenu.addItem("Buchhaltung", VaadinIcons.NURSE, null);

        buchhaltungbuchhaltungMenu.addItem("Buchhaltung", VaadinIcons.LAPTOP, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Buchhaltung");
            }
        });

        buchhaltungbuchhaltungMenu.addItem("Buchhaltung Tree", VaadinIcons.MAGNET, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("BuchhaltungTree");
            }
        });

        buchhaltungbuchhaltungMenu.addItem("Buchhaltung erstellen", VaadinIcons.CONTROLLER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("BuchhaltungCreate");
            }
        });

        buchhaltungbuchhaltungMenu.addItem("Buchungsmaske", VaadinIcons.ABACUS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("Buchungsmaske");
            }
        });

        MenuBar.MenuItem templateBuchhaltungMenu = buchhaltungenMenu.addItem("Kontoplan", VaadinIcons.MAGIC, null);

        templateBuchhaltungMenu.addItem("Template Buchhaltung", VaadinIcons.ROAD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateBuchhaltung");
            }
        });
        templateBuchhaltungMenu.addItem("Template Buchhaltung Tree", VaadinIcons.TREE_TABLE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateBuchhaltungTree");
            }
        });

        templateBuchhaltungMenu.addItem("Template Mehrwertsteuercodes", VaadinIcons.CLOCK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TemplateMehrwertsteuercode");
            }
        });

        MenuBar.MenuItem testsMenu = menuBar.addItem("Tests", null);
        testsMenu.setIcon(VaadinIcons.TEETH);

        MenuBar.MenuItem testsDatenMenu = testsMenu.addItem("Testdaten", VaadinIcons.SCREWDRIVER, null);

        testsDatenMenu.addItem("Testdaten", VaadinIcons.ABACUS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TestDataCreate");
            }
        });

        MenuBar.MenuItem testsComponents = testsMenu.addItem("Komponenten testen", VaadinIcons.FACTORY, null);

        testsComponents.addItem("EditorTest", VaadinIcons.EDIT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("EditorTest");
            }
        });

        testsComponents.addItem("Push Test", VaadinIcons.PUZZLE_PIECE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("PushTest");
            }
        });

        testsComponents.addItem("TestAdresse", VaadinIcons.EDIT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TestAdresse");
            }
        });

        testsComponents.addItem("EditorTestGridEdit", VaadinIcons.YOUTUBE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("EditorTestGridEdit");
            }
        });

        layout.addComponent(menuBar);
        setCompositionRoot(layout);
    }
}
