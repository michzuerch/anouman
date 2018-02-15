package com.gmail.michzuerch.anouman.presentation.ui;


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
                getUI().getNavigator().navigateTo("AboutView");
            }
        });
        aboutItem.setIcon(resourceLogo);

        MenuBar.MenuItem administrationMenu = menuBar.addItem("Systemverwaltung", VaadinIcons.GAMEPAD, null);

        administrationMenu.addItem("Benutzer", VaadinIcons.USER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("UzerView");
            }
        });
        administrationMenu.addItem("Benutzerrollen", VaadinIcons.DIPLOMA_SCROLL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("UzerRoleView");
            }
        });
        administrationMenu.addItem("Benutzer zu Rollen zuordnen", VaadinIcons.DIPLOMA, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("UzerToUzerRoleMappingView");
            }
        });
        administrationMenu.addItem("Backup", VaadinIcons.DOWNLOAD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("BackupView");
            }
        });

        MenuBar.MenuItem reportingMenu = menuBar.addItem("Reporting", VaadinIcons.TAXI, null);
        MenuBar.MenuItem reportJasper = reportingMenu.addItem("Report Vorlagen (Jasperreports)", VaadinIcons.GAVEL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ReportJasperView");
            }
        });

        reportJasper.addItem("Report Vorlagen Jasper Bilder", VaadinIcons.BULLSEYE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("ReportJasperImageView");
            }
        });

        MenuBar.MenuItem reportCSS = reportingMenu.addItem("Report Vorlagen (CSS)", VaadinIcons.GAVEL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ReportCSSView");
            }
        });

        reportCSS.addItem("Report CSS Images", VaadinIcons.SAFE_LOCK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("ReportCSSImageView");
            }
        });


        MenuBar.MenuItem reportFOP = reportingMenu.addItem("Report Vorlagen (FOP)", VaadinIcons.GAVEL, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ReportFOPView");
            }
        });

        reportFOP.addItem("Report FOP Images", VaadinIcons.COPYRIGHT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("ReportFOPImageView");
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
                getUI().getNavigator().navigateTo("RechnungEditOpenerView");
            }
        });

        rechnungenMenu.addItem("Adressen", VaadinIcons.CALENDAR_CLOCK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("AdresseView");
            }
        });
        rechnungenMenu.addItem("Rechnungen", VaadinIcons.INVOICE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("RechnungView");
            }
        });
        rechnungenMenu.addItem("Rechnungspositionen", VaadinIcons.PACKAGE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("RechnungspositionView");
            }
        });

        MenuBar.MenuItem aufwandItem = rechnungenMenu.addItem("Aufwand", VaadinIcons.CAMERA, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("AufwandView");
            }
        });

        MenuBar.MenuItem aufwandCalendarItem = rechnungenMenu.addItem("Aufwand Calendar", VaadinIcons.CALENDAR, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("AufwandCalendarView");
            }
        });

        MenuBar.MenuItem buchhaltungbuchhaltungMenu = buchhaltungenMenu.addItem("Buchhaltung", VaadinIcons.NURSE, null);

        buchhaltungbuchhaltungMenu.addItem("Buchhaltung", VaadinIcons.LAPTOP, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("BuchhaltungView");
            }
        });

        buchhaltungbuchhaltungMenu.addItem("Buchhaltung Tree", VaadinIcons.MAGNET, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("BuchhaltungTreeView");
            }
        });

        buchhaltungbuchhaltungMenu.addItem("Buchhaltung erstellen", VaadinIcons.CONTROLLER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("BuchhaltungCreateView");
            }
        });

        buchhaltungbuchhaltungMenu.addItem("Buchungsmaske", VaadinIcons.ABACUS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("BuchungsmaskeView");
            }
        });

        MenuBar.MenuItem templateBuchhaltungMenu = buchhaltungenMenu.addItem("Kontoplan", VaadinIcons.MAGIC, null);

        templateBuchhaltungMenu.addItem("Template Buchhaltung", VaadinIcons.ROAD, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateBuchhaltungView");
            }
        });
        templateBuchhaltungMenu.addItem("Template Buchhaltung Tree", VaadinIcons.TREE_TABLE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("TemplateBuchhaltungTreeView");
            }
        });

        templateBuchhaltungMenu.addItem("Template Mehrwertsteuercodes", VaadinIcons.CLOCK, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TemplateMehrwertsteuercodeView");
            }
        });

        MenuBar.MenuItem artikelstammMenu = menuBar.addItem("Artikelstamm", null);
        artikelstammMenu.setIcon(VaadinIcons.HAMMER);

        artikelstammMenu.addItem("Artikelkategorie", VaadinIcons.SCREWDRIVER, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ArtikelkategorieView");
            }
        });

        artikelstammMenu.addItem("Artikel", VaadinIcons.MAGIC, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ArtikelView");
            }
        });

        artikelstammMenu.addItem("Artikelbild", VaadinIcons.DIAMOND, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                getUI().getNavigator().navigateTo("ArtikelbildView");
            }
        });


        MenuBar.MenuItem testsMenu = menuBar.addItem("Tests", null);
        testsMenu.setIcon(VaadinIcons.TEETH);

        MenuBar.MenuItem testsDatenMenu = testsMenu.addItem("Testdaten", VaadinIcons.SCREWDRIVER, null);

        testsDatenMenu.addItem("Testdaten", VaadinIcons.ABACUS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TestDataCreateView");
            }
        });

        MenuBar.MenuItem testsComponents = testsMenu.addItem("Komponenten testen", VaadinIcons.FACTORY, null);

        testsComponents.addItem("EditorTest", VaadinIcons.EDIT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("EditorTestView");
            }
        });

        testsComponents.addItem("Push Test", VaadinIcons.PUZZLE_PIECE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("PushTestView");
            }
        });

        testsComponents.addItem("TestAdresse", VaadinIcons.EDIT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("TestAdresseView");
            }
        });

        testsComponents.addItem("EditorTestGridEdit", VaadinIcons.YOUTUBE, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("EditorTestGridEditView");
            }
        });

        testsComponents.addItem("Image Test", VaadinIcons.INFO, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("ImageTestView");
            }
        });

        testsComponents.addItem("GridCrud Test", VaadinIcons.MAGIC, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("GridCrudTestView");
            }
        });

        testsComponents.addItem("EditGridCrud Test", VaadinIcons.MAGIC, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo("EditGridCrudTestView");
            }
        });

        layout.addComponent(menuBar);
        setCompositionRoot(layout);
    }
}
