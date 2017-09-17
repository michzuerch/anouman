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

        MenuBar.MenuItem administrationItem = menuBar.addItem("Systemverwaltung", null);
        administrationItem.setIcon(VaadinIcons.GAMEPAD);

        administrationItem.addItem("Benutzer", VaadinIcons.USER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Uzer");
            }
        });
        administrationItem.addItem("Benutzerrollen", VaadinIcons.DIPLOMA_SCROLL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("UzerRole");
            }
        });
        administrationItem.addItem("Benutzer zu Rollen zuordnen", VaadinIcons.DIPLOMA, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("UzerToUzerRoleMapping");
            }
        });
        administrationItem.addItem("Backup", VaadinIcons.DOWNLOAD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Backup");
            }
        });

        MenuBar.MenuItem rechnungenItem = menuBar.addItem("Rechnungen", null);
        rechnungenItem.setIcon(VaadinIcons.OFFICE);

        MenuBar.MenuItem buchhaltungenItem = menuBar.addItem("Buchhaltungen", null);
        buchhaltungenItem.setIcon(VaadinIcons.PUZZLE_PIECE);

        MenuBar.MenuItem toolsItem = menuBar.addItem("Tools", null);
        toolsItem.setIcon(VaadinIcons.CARET_DOWN);

        MenuBar.MenuItem testsItem = menuBar.addItem("Tests", null);
        testsItem.setIcon(VaadinIcons.TEETH);

        rechnungenItem.addItem("Adressen", VaadinIcons.CALENDAR_CLOCK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Adresse");
            }
        });
        rechnungenItem.addItem("Rechnungen", VaadinIcons.INVOICE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Rechnung");
            }
        });
        rechnungenItem.addItem("Rechnungspositionen", VaadinIcons.PACKAGE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Rechnungsposition");
            }
        });

        MenuBar.MenuItem aufwandItem = rechnungenItem.addItem("Aufwand", VaadinIcons.CAMERA, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Aufwand");
            }
        });

/*
        aufwandItem.addItem("Aufwand Woche", VaadinIcons.INFO, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("AufwandWeek");
            }
        });
        aufwandItem.addItem("Aufwand Monat", VaadinIcons.PACKAGE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("AufwandMonth");
            }
        });
        aufwandItem.addItem("Aufwand 60 Tage", VaadinIcons.MAGNET, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Aufwand60Days");
            }
        });
*/
        rechnungenItem.addItem("Report Vorlagen", VaadinIcons.GAVEL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ReportTemplate");
            }
        });
        buchhaltungenItem.addItem("Buchhaltung", VaadinIcons.LAPTOP, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Buchhaltung");
            }
        });

        buchhaltungenItem.addItem("Template Buchhaltung", VaadinIcons.ROAD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateBuchhaltung");
            }
        });

        buchhaltungenItem.addItem("Buchhaltung Tree", VaadinIcons.MAGNET, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("BuchhaltungTree");
            }
        });

        buchhaltungenItem.addItem("Template Buchhaltung Tree", VaadinIcons.AIRPLANE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateBuchhaltungTree");
            }
        });

        buchhaltungenItem.addItem("Template Mehrwertsteuercodes", VaadinIcons.CLOCK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TemplateMehrwertsteuercode");
            }
        });
/*
        buchhaltungenItem.addItem("Kontoplan Templates", VaadinIcons.ROAD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateKontoplan");
            }
        });
        buchhaltungenItem.addItem("Kontoplan Compact", VaadinIcons.VIMEO, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateKontoplanCompact");
            }
        });
        buchhaltungenItem.addItem("Templates Baumansicht", VaadinIcons.WRENCH, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        getUI().getNavigator().navigateTo("TemplateKontoplanTree");
                    }
                }
        );
        buchhaltungenItem.addItem("Buchhaltungen", VaadinIcons.BAR_CHART, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Buchhaltung");
            }
        });
        buchhaltungenItem.addItem("Kontoplan", VaadinIcons.BOOK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateKontoplan");

            }
        });
        buchhaltungenItem.addItem("Kontoklasse", VaadinIcons.VOLUME, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Kontoklasse");
            }
        });
        buchhaltungenItem.addItem("Kontogruppe", VaadinIcons.TASKS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Kontogruppe");
            }
        });
        buchhaltungenItem.addItem("Kontoart", VaadinIcons.STOPWATCH, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Kontoart");
            }
        });
        buchhaltungenItem.addItem("Konto", VaadinIcons.UMBRELLA, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Konto");
            }
        });
        buchhaltungenItem.addItem("Konto", VaadinIcons.AIRPLANE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Konto");
            }
        });
        buchhaltungenItem.addItem("Buchungen", VaadinIcons.BED, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Buchung");
            }
        });

        buchhaltungenItem.addItem("Buchungsmaske", VaadinIcons.TOOLBOX, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Buchungsmaske");
            }
        });
        toolsItem.addItem("Backup", VaadinIcons.HAMMER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Backup");
            }
        });
        toolsItem.addItem("Import", VaadinIcons.DATABASE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("Import");
            }
        });
*/

        testsItem.addItem("Testdaten", VaadinIcons.ABACUS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TestDataCreate");
            }
        });

        testsItem.addItem("EditorTest", VaadinIcons.EDIT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("EditorTest");
            }
        });

        testsItem.addItem("TestAdresse", VaadinIcons.EDIT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TestAdresse");
            }
        });

        testsItem.addItem("EditorTestGridEdit", VaadinIcons.YOUTUBE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("EditorTestGridEdit");
            }
        });

        layout.addComponent(menuBar);
        setCompositionRoot(layout);
    }
}
