package ch.internettechnik.anouman.presentation.ui.testdata;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;

@Theme("anouman")
@CDIView("TestDataCreate")
public class TestDataCreateView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TestDataCreateView.class.getName());

    @Inject
    Menu menu;

    @Inject
    RechnungFacade rechnungFacade;

    @Inject
    RechnungspositionFacade rechnungspositionFacade;

    @Inject
    AdresseFacade adresseFacade;

    @Inject
    AufwandFacade aufwandFacade;

    @Inject
    UzerFacade uzerFacade;

    @Inject
    UzerRoleFacade uzerRoleFacade;

    @Inject
    TemplateBuchhaltungFacade templateBuchhaltungFacade;

    @Inject
    TemplateKontoklasseFacade templateKontoklasseFacade;

    @Inject
    TemplateKontohauptgruppeFacade templateKontohauptgruppeFacade;

    @Inject
    TemplateKontogruppeFacade templateKontogruppeFacade;

    @Inject
    TemplateSammelkontoFacade templateSammelkontoFacade;

    @Inject
    TemplateKontoFacade templateKontoFacade;

    @Inject
    TemplateMehrwertsteuercodeFacade templateMehrwertsteuercodeFacade;

    private void createTestDataUzer() {
        UzerRole roleAdmin = new UzerRole();
        roleAdmin.setRole("admin");
        roleAdmin.setRoleGroup("group");

        UzerRole roleRechnung = new UzerRole();
        roleRechnung.setRole("rechnung");
        roleAdmin.setRoleGroup("group");

        UzerRole roleBuchhaltung = new UzerRole();
        roleBuchhaltung.setRole("buchhaltung");
        roleAdmin.setRoleGroup("group");

        uzerRoleFacade.save(roleAdmin);
        uzerRoleFacade.save(roleRechnung);
        uzerRoleFacade.save(roleBuchhaltung);

        Uzer uzerAdmin = new Uzer();
        uzerAdmin.setDescription("Administrator");
        uzerAdmin.setPrincipal("admin");
        uzerAdmin.setPrincipal("passpass");
        uzerAdmin.getRoles().add(roleAdmin);
        uzerAdmin.getRoles().add(roleRechnung);
        uzerAdmin.getRoles().add(roleBuchhaltung);
        uzerFacade.save(uzerAdmin);

        Uzer uzerKevin = new Uzer();
        uzerKevin.setDescription("Allerweltsuser");
        uzerKevin.setPrincipal("kevin");
        uzerKevin.setPrincipal("passpass");
        uzerKevin.getRoles().add(roleRechnung);
        uzerKevin.getRoles().add(roleBuchhaltung);
        uzerFacade.save(uzerKevin);

        Uzer uzerAnonymous = new Uzer();
        uzerAnonymous.setDescription("Keine Rollen");
        uzerAnonymous.setPrincipal("anoymous");
        uzerAnonymous.setPrincipal("passpass");
        uzerFacade.save(uzerAnonymous);

        Notification.show("Testdaten User erstellt", Notification.Type.TRAY_NOTIFICATION);

    }

    private void createTestDataRechnung() {
        Rechnung rechnung = new Rechnung();
        rechnung.setBezahlt(false);
        rechnung.setBezeichnung("Testrechnung");
        rechnung.setRechnungsdatum(new Date());
        rechnung.setFaelligInTagen(32);
        rechnung.setVerschickt(true);

        Adresse adresse = new Adresse();
        adresse.setAnrede("Herr");
        adresse.setFirma("internettechnik GmbH");
        adresse.setNachname("Zuercher");
        adresse.setVorname("Michael");
        adresse.setOrt("Ohne Wohnsitz");
        adresse.setPostleitzahl("0000");
        adresse.setStundensatz(130d);
        adresse.setStrasse("Keine Strasse");
        adresse = adresseFacade.save(adresse);

        rechnung.setAdresse(adresse);
        rechnung = rechnungFacade.save(rechnung);

        Rechnungsposition rechnungsposition = new Rechnungsposition();
        rechnungsposition.setBezeichnung("Laptop");
        rechnungsposition.setAnzahl(2d);
        rechnungsposition.setBezeichnunglang("Acer Aspire E5");
        rechnungsposition.setMengeneinheit("Stück");
        rechnungsposition.setStueckpreis(1235.45d);
        rechnungsposition.setRechnung(rechnung);
        rechnungsposition = rechnungspositionFacade.save(rechnungsposition);


        Aufwand aufwand = new Aufwand();
        aufwand.setBezeichnung("Entfernen Trojaner @Pigibot");
        aufwand.setTitel("Aufwand");

        //@todo Aufwand ist 0 Stunden wegen Enddatum
        aufwand.setStart(new Date());
        aufwand.setEnde(new Date());
        aufwand.setRechnung(rechnung);
        aufwand = aufwandFacade.save(aufwand);

        Notification.show("Testdaten Rechnung erstellt", Notification.Type.TRAY_NOTIFICATION);
    }

    private void createTestDataTemplateBuchhaltung() {
        TemplateBuchhaltung buchhaltung = new TemplateBuchhaltung();
        buchhaltung.setBezeichnung("Testdaten Template Buchhaltung");
        buchhaltung = templateBuchhaltungFacade.save(buchhaltung);
        TemplateKontoklasse templateKontoklasse1 = new TemplateKontoklasse("Aktiven", "1", buchhaltung);
        templateKontoklasse1 = templateKontoklasseFacade.save(templateKontoklasse1);
        TemplateKontoklasse templateKontoklasse2 = new TemplateKontoklasse("Passive", "2", buchhaltung);
        templateKontoklasse2 = templateKontoklasseFacade.save(templateKontoklasse2);
        TemplateKontoklasse templateKontoklasse3 = new TemplateKontoklasse("Aufwand", "3", buchhaltung);
        templateKontoklasse3 = templateKontoklasseFacade.save(templateKontoklasse3);
        TemplateKontoklasse templateKontoklasse4 = new TemplateKontoklasse("Ertrag", "4", buchhaltung);
        templateKontoklasse4 = templateKontoklasseFacade.save(templateKontoklasse4);

        TemplateKontohauptgruppe templateKontohauptgruppe1 = new TemplateKontohauptgruppe("Gruppe1", "1", templateKontoklasse1);
        templateKontohauptgruppe1 = templateKontohauptgruppeFacade.save(templateKontohauptgruppe1);
        TemplateKontohauptgruppe templateKontohauptgruppe2 = new TemplateKontohauptgruppe("Gruppe2", "2", templateKontoklasse1);
        templateKontohauptgruppe2 = templateKontohauptgruppeFacade.save(templateKontohauptgruppe2);
        TemplateKontohauptgruppe templateKontohauptgruppe3 = new TemplateKontohauptgruppe("Gruppe3", "3", templateKontoklasse2);
        templateKontohauptgruppe3 = templateKontohauptgruppeFacade.save(templateKontohauptgruppe3);
        TemplateKontohauptgruppe templateKontohauptgruppe4 = new TemplateKontohauptgruppe("Gruppe4", "4", templateKontoklasse2);
        templateKontohauptgruppe4 = templateKontohauptgruppeFacade.save(templateKontohauptgruppe4);
        TemplateKontohauptgruppe templateKontohauptgruppe5 = new TemplateKontohauptgruppe("Gruppe5", "5", templateKontoklasse3);
        templateKontohauptgruppe5 = templateKontohauptgruppeFacade.save(templateKontohauptgruppe5);
        TemplateKontohauptgruppe templateKontohauptgruppe6 = new TemplateKontohauptgruppe("Gruppe6", "6", templateKontoklasse4);
        templateKontohauptgruppe6 = templateKontohauptgruppeFacade.save(templateKontohauptgruppe6);

        TemplateKontogruppe templateKontogruppe1 = new TemplateKontogruppe("Art1", "1", templateKontohauptgruppe1);
        templateKontogruppe1 = templateKontogruppeFacade.save(templateKontogruppe1);
        TemplateKontogruppe templateKontogruppe2 = new TemplateKontogruppe("Art2", "2", templateKontohauptgruppe1);
        templateKontogruppe2 = templateKontogruppeFacade.save(templateKontogruppe2);
        TemplateKontogruppe templateKontogruppe3 = new TemplateKontogruppe("Art3", "3", templateKontohauptgruppe2);
        templateKontogruppe3 = templateKontogruppeFacade.save(templateKontogruppe3);
        TemplateKontogruppe templateKontogruppe4 = new TemplateKontogruppe("Art4", "4", templateKontohauptgruppe2);
        templateKontogruppe4 = templateKontogruppeFacade.save(templateKontogruppe4);
        TemplateKontogruppe templateKontogruppe5 = new TemplateKontogruppe("Art5", "5", templateKontohauptgruppe3);
        templateKontogruppe5 = templateKontogruppeFacade.save(templateKontogruppe5);
        TemplateKontogruppe templateKontogruppe6 = new TemplateKontogruppe("Art6", "6", templateKontohauptgruppe3);
        templateKontogruppe6 = templateKontogruppeFacade.save(templateKontogruppe6);
        TemplateKontogruppe templateKontogruppe7 = new TemplateKontogruppe("Art7", "7", templateKontohauptgruppe4);
        templateKontogruppe7 = templateKontogruppeFacade.save(templateKontogruppe7);
        TemplateKontogruppe templateKontogruppe8 = new TemplateKontogruppe("Art8", "8", templateKontohauptgruppe4);
        templateKontogruppe8 = templateKontogruppeFacade.save(templateKontogruppe8);
        TemplateKontogruppe templateKontogruppe9 = new TemplateKontogruppe("Art9", "9", templateKontohauptgruppe5);
        templateKontogruppe9 = templateKontogruppeFacade.save(templateKontogruppe9);
        TemplateKontogruppe templateKontogruppe10 = new TemplateKontogruppe("Art10", "10", templateKontohauptgruppe5);
        templateKontogruppe10 = templateKontogruppeFacade.save(templateKontogruppe10);
        TemplateKontogruppe templateKontogruppe11 = new TemplateKontogruppe("Art11", "11", templateKontohauptgruppe6);
        templateKontogruppe11 = templateKontogruppeFacade.save(templateKontogruppe11);
        TemplateKontogruppe templateKontogruppe12 = new TemplateKontogruppe("Art12", "12", templateKontohauptgruppe6);
        templateKontogruppe12 = templateKontogruppeFacade.save(templateKontogruppe12);


        TemplateKonto konto1 = new TemplateKonto("Konto1", "Konto1", "1", templateKontogruppe1);
        konto1 = templateKontoFacade.save(konto1);
        TemplateKonto konto2 = new TemplateKonto("Konto2", "Konto2", "2", templateKontogruppe1);
        konto2 = templateKontoFacade.save(konto2);
        TemplateKonto konto3 = new TemplateKonto("Konto3", "Konto3", "3", templateKontogruppe2);
        konto3 = templateKontoFacade.save(konto3);
        TemplateKonto konto4 = new TemplateKonto("Konto4", "Konto4", "4", templateKontogruppe2);
        konto4 = templateKontoFacade.save(konto4);
        TemplateKonto konto5 = new TemplateKonto("Konto5", "Konto5", "5", templateKontogruppe3);
        konto5 = templateKontoFacade.save(konto5);
        TemplateKonto konto6 = new TemplateKonto("Konto6", "Konto6", "6", templateKontogruppe3);
        konto6 = templateKontoFacade.save(konto6);
        TemplateKonto konto7 = new TemplateKonto("Konto7", "Konto7", "7", templateKontogruppe4);
        konto7 = templateKontoFacade.save(konto7);
        TemplateKonto konto8 = new TemplateKonto("Konto8", "Konto8", "8", templateKontogruppe4);
        konto8 = templateKontoFacade.save(konto8);
        TemplateKonto konto9 = new TemplateKonto("Konto9", "Konto9", "9", templateKontogruppe5);
        konto9 = templateKontoFacade.save(konto9);
        TemplateKonto konto10 = new TemplateKonto("Konto10", "Konto10", "10", templateKontogruppe5);
        konto10 = templateKontoFacade.save(konto10);
        TemplateKonto konto11 = new TemplateKonto("Konto11", "Konto11", "11", templateKontogruppe6);
        konto11 = templateKontoFacade.save(konto11);
        TemplateKonto konto12 = new TemplateKonto("Konto12", "Konto12", "12", templateKontogruppe6);
        konto12 = templateKontoFacade.save(konto12);
        TemplateKonto konto13 = new TemplateKonto("Konto13", "Konto13", "13", templateKontogruppe7);
        konto13 = templateKontoFacade.save(konto13);
        TemplateKonto konto14 = new TemplateKonto("Konto14", "Konto14", "14", templateKontogruppe7);
        konto14 = templateKontoFacade.save(konto14);
        TemplateKonto konto15 = new TemplateKonto("Konto15", "Konto15", "15", templateKontogruppe8);
        konto15 = templateKontoFacade.save(konto15);
        TemplateKonto konto16 = new TemplateKonto("Konto16", "Konto16", "16", templateKontogruppe8);
        konto16 = templateKontoFacade.save(konto16);
        TemplateKonto konto17 = new TemplateKonto("Konto17", "Konto17", "17", templateKontogruppe9);
        konto17 = templateKontoFacade.save(konto17);
        TemplateKonto konto18 = new TemplateKonto("Konto18", "Konto18", "18", templateKontogruppe9);
        konto18 = templateKontoFacade.save(konto18);
        TemplateKonto konto19 = new TemplateKonto("Konto19", "Konto19", "19", templateKontogruppe10);
        konto19 = templateKontoFacade.save(konto19);
        TemplateKonto konto20 = new TemplateKonto("Konto20", "Konto20", "20", templateKontogruppe10);
        konto20 = templateKontoFacade.save(konto20);
        TemplateKonto konto21 = new TemplateKonto("Konto21", "Konto21", "21", templateKontogruppe11);
        konto21 = templateKontoFacade.save(konto21);
        TemplateKonto konto22 = new TemplateKonto("Konto22", "Konto22", "22", templateKontogruppe11);
        konto22 = templateKontoFacade.save(konto22);
        TemplateKonto konto23 = new TemplateKonto("Konto23", "Konto23", "23", templateKontogruppe12);
        konto23 = templateKontoFacade.save(konto23);
        TemplateKonto konto24 = new TemplateKonto("Konto24", "Konto24", "24", templateKontogruppe12);
        konto24 = templateKontoFacade.save(konto24);

        TemplateMehrwertsteuercode mehrwertsteuercode = new TemplateMehrwertsteuercode();
        mehrwertsteuercode.setBezeichnung("12%");
        mehrwertsteuercode.setCode("V12");
        mehrwertsteuercode.setProzent(12.0f);
        mehrwertsteuercode.setVerkauf(true);
        mehrwertsteuercode.setTemplateMehrwertsteuerKonto(konto1);
        mehrwertsteuercode.setTemplateBuchhaltung(buchhaltung);

        mehrwertsteuercode = templateMehrwertsteuercodeFacade.save(mehrwertsteuercode);

        Notification.show("Testdaten Template Buchhaltung erstellt", Notification.Type.TRAY_NOTIFICATION);
    }

    @PostConstruct
    void init() {
        Button rechnungTestBtn = new Button("Create TestData Rechnung", clickEvent -> {
            createTestDataRechnung();
        });
        rechnungTestBtn.setIcon(VaadinIcons.ACADEMY_CAP);

        Button uzerTestBtn = new Button("Create TestData Uzer", clickEvent -> {
            createTestDataUzer();
        });
        uzerTestBtn.setIcon(VaadinIcons.CAMERA);

        Button testCreateTemplateBuchhaltung = new Button("Test Create Template Buchhaltung", clickEvent -> {
            createTestDataTemplateBuchhaltung();
        });
        testCreateTemplateBuchhaltung.setIcon(VaadinIcons.TOOTH);
        addComponents(menu, rechnungTestBtn, testCreateTemplateBuchhaltung);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
