package ch.internettechnik.anouman.presentation.ui.testdata;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.backend.session.jpa.api.UzerRoleService;
import ch.internettechnik.anouman.backend.session.jpa.api.UzerService;
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
    UzerService uzerService;

    @Inject
    UzerRoleService uzerRoleService;

    @Inject
    TemplateBuchhaltungFacade templateBuchhaltungFacade;

    @Inject
    TemplateKontoklasseFacade templateKontoklasseFacade;

    @Inject
    TemplateKontogruppeFacade templateKontogruppeFacade;

    @Inject
    TemplateKontoartFacade templateKontoartFacade;

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

        uzerRoleService.saveOrPersist(roleAdmin);
        uzerRoleService.saveOrPersist(roleRechnung);
        uzerRoleService.saveOrPersist(roleBuchhaltung);

        Uzer uzerAdmin = new Uzer();
        uzerAdmin.setDescription("Administrator");
        uzerAdmin.setPrincipal("admin");
        uzerAdmin.setPrincipal("passpass");
        uzerAdmin.getRoles().add(roleAdmin);
        uzerAdmin.getRoles().add(roleRechnung);
        uzerAdmin.getRoles().add(roleBuchhaltung);
        uzerService.saveOrPersist(uzerAdmin);

        Uzer uzerKevin = new Uzer();
        uzerKevin.setDescription("Allerweltsuser");
        uzerKevin.setPrincipal("kevin");
        uzerKevin.setPrincipal("passpass");
        uzerKevin.getRoles().add(roleRechnung);
        uzerKevin.getRoles().add(roleBuchhaltung);
        uzerService.saveOrPersist(uzerKevin);

        Uzer uzerAnonymous = new Uzer();
        uzerAnonymous.setDescription("Keine Rollen");
        uzerAnonymous.setPrincipal("anoymous");
        uzerAnonymous.setPrincipal("passpass");
        uzerService.saveOrPersist(uzerAnonymous);

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
        TemplateKontoklasse kontoklasse1 = new TemplateKontoklasse("Aktiven", "1", buchhaltung);
        kontoklasse1 = templateKontoklasseFacade.save(kontoklasse1);

        TemplateKontoklasse kontoklasse2 = new TemplateKontoklasse("Passive", "2", buchhaltung);
        kontoklasse2 = templateKontoklasseFacade.save(kontoklasse2);

        TemplateKontoklasse kontoklasse3 = new TemplateKontoklasse("Aufwand", "3", buchhaltung);
        kontoklasse3 = templateKontoklasseFacade.save(kontoklasse3);

        TemplateKontoklasse kontoklasse4 = new TemplateKontoklasse("Ertrag", "4", buchhaltung);
        kontoklasse4 = templateKontoklasseFacade.save(kontoklasse4);

        buchhaltung.getTemplateKontoklasses().add(kontoklasse1);
        buchhaltung = templateBuchhaltungFacade.save(buchhaltung);

        buchhaltung.getTemplateKontoklasses().add(kontoklasse2);
        buchhaltung = templateBuchhaltungFacade.save(buchhaltung);

        buchhaltung.getTemplateKontoklasses().add(kontoklasse3);
        buchhaltung = templateBuchhaltungFacade.save(buchhaltung);

        buchhaltung.getTemplateKontoklasses().add(kontoklasse4);
        buchhaltung = templateBuchhaltungFacade.save(buchhaltung);


        TemplateKontogruppe kontogruppe1 = new TemplateKontogruppe("Gruppe1", "1", kontoklasse1);
        kontogruppe1 = templateKontogruppeFacade.save(kontogruppe1);
        kontoklasse1.getTemplateKontogruppes().add(kontogruppe1);
        kontoklasse1 = templateKontoklasseFacade.save(kontoklasse1);

        TemplateKontogruppe kontogruppe2 = new TemplateKontogruppe("Gruppe2", "2", kontoklasse1);
        kontogruppe2 = templateKontogruppeFacade.save(kontogruppe2);
        kontoklasse1.getTemplateKontogruppes().add(kontogruppe2);
        kontoklasse1 = templateKontoklasseFacade.save(kontoklasse1);

        TemplateKontogruppe kontogruppe3 = new TemplateKontogruppe("Gruppe3", "3", kontoklasse2);
        kontogruppe3 = templateKontogruppeFacade.save(kontogruppe3);
        kontoklasse2.getTemplateKontogruppes().add(kontogruppe3);
        kontoklasse2 = templateKontoklasseFacade.save(kontoklasse2);

        TemplateKontogruppe kontogruppe4 = new TemplateKontogruppe("Gruppe4", "4", kontoklasse2);
        kontogruppe4 = templateKontogruppeFacade.save(kontogruppe4);
        kontoklasse2.getTemplateKontogruppes().add(kontogruppe4);
        kontoklasse2 = templateKontoklasseFacade.save(kontoklasse2);

        TemplateKontogruppe kontogruppe5 = new TemplateKontogruppe("Gruppe5", "5", kontoklasse3);
        kontogruppe5 = templateKontogruppeFacade.save(kontogruppe5);
        kontoklasse3.getTemplateKontogruppes().add(kontogruppe5);
        kontoklasse3 = templateKontoklasseFacade.save(kontoklasse3);

        TemplateKontogruppe kontogruppe6 = new TemplateKontogruppe("Gruppe6", "6", kontoklasse4);
        kontogruppe6 = templateKontogruppeFacade.save(kontogruppe6);
        kontoklasse4.getTemplateKontogruppes().add(kontogruppe6);
        kontoklasse4 = templateKontoklasseFacade.save(kontoklasse4);

        TemplateKontoart kontoart1 = new TemplateKontoart("Art1", "1", kontogruppe1);
        kontoart1 = templateKontoartFacade.save(kontoart1);
        kontogruppe1.getTemplateKontoarts().add(kontoart1);
        kontogruppe1 = templateKontogruppeFacade.save(kontogruppe1);

        TemplateKontoart kontoart2 = new TemplateKontoart("Art2", "2", kontogruppe1);
        kontoart2 = templateKontoartFacade.save(kontoart2);
        kontogruppe1.getTemplateKontoarts().add(kontoart2);
        kontogruppe1 = templateKontogruppeFacade.save(kontogruppe1);

        TemplateKontoart kontoart3 = new TemplateKontoart("Art3", "3", kontogruppe2);
        kontoart3 = templateKontoartFacade.save(kontoart3);
        kontogruppe2.getTemplateKontoarts().add(kontoart3);
        kontogruppe2 = templateKontogruppeFacade.save(kontogruppe2);

        TemplateKontoart kontoart4 = new TemplateKontoart("Art4", "4", kontogruppe2);
        kontoart4 = templateKontoartFacade.save(kontoart4);
        kontogruppe2.getTemplateKontoarts().add(kontoart4);
        kontogruppe2 = templateKontogruppeFacade.save(kontogruppe2);

        TemplateKontoart kontoart5 = new TemplateKontoart("Art5", "5", kontogruppe3);
        kontoart5 = templateKontoartFacade.save(kontoart5);
        kontogruppe3.getTemplateKontoarts().add(kontoart5);
        kontogruppe3 = templateKontogruppeFacade.save(kontogruppe3);

        TemplateKontoart kontoart6 = new TemplateKontoart("Art6", "6", kontogruppe3);
        kontoart6 = templateKontoartFacade.save(kontoart6);
        kontogruppe3.getTemplateKontoarts().add(kontoart6);
        kontogruppe3 = templateKontogruppeFacade.save(kontogruppe3);

        TemplateKontoart kontoart7 = new TemplateKontoart("Art7", "7", kontogruppe4);
        kontoart7 = templateKontoartFacade.save(kontoart7);
        kontogruppe4.getTemplateKontoarts().add(kontoart7);
        kontogruppe4 = templateKontogruppeFacade.save(kontogruppe4);

        TemplateKontoart kontoart8 = new TemplateKontoart("Art8", "8", kontogruppe4);
        kontoart8 = templateKontoartFacade.save(kontoart8);
        kontogruppe4.getTemplateKontoarts().add(kontoart8);
        kontogruppe4 = templateKontogruppeFacade.save(kontogruppe4);

        TemplateKontoart kontoart9 = new TemplateKontoart("Art9", "9", kontogruppe5);
        kontoart9 = templateKontoartFacade.save(kontoart9);
        kontogruppe5.getTemplateKontoarts().add(kontoart9);
        kontogruppe5 = templateKontogruppeFacade.save(kontogruppe5);

        TemplateKontoart kontoart10 = new TemplateKontoart("Art10", "10", kontogruppe5);
        kontoart10 = templateKontoartFacade.save(kontoart10);
        kontogruppe5.getTemplateKontoarts().add(kontoart10);
        kontogruppe5 = templateKontogruppeFacade.save(kontogruppe5);

        TemplateKontoart kontoart11 = new TemplateKontoart("Art11", "11", kontogruppe6);
        kontoart11 = templateKontoartFacade.save(kontoart11);
        kontogruppe6.getTemplateKontoarts().add(kontoart11);
        kontogruppe6 = templateKontogruppeFacade.save(kontogruppe6);

        TemplateKontoart kontoart12 = new TemplateKontoart("Art12", "12", kontogruppe6);
        kontoart12 = templateKontoartFacade.save(kontoart12);
        kontogruppe6.getTemplateKontoarts().add(kontoart12);
        kontogruppe6 = templateKontogruppeFacade.save(kontogruppe6);

        TemplateKonto konto1 = new TemplateKonto("Konto1", "1", kontoart1);
        konto1 = templateKontoFacade.save(konto1);
        kontoart1.getTemplateKontos().add(konto1);
        kontoart1 = templateKontoartFacade.save(kontoart1);

        TemplateKonto konto2 = new TemplateKonto("Konto2", "2", kontoart1);
        konto2 = templateKontoFacade.save(konto2);
        kontoart1.getTemplateKontos().add(konto2);
        kontoart1 = templateKontoartFacade.save(kontoart1);

        TemplateKonto konto3 = new TemplateKonto("Konto3", "3", kontoart2);
        konto3 = templateKontoFacade.save(konto3);
        kontoart2.getTemplateKontos().add(konto3);
        kontoart2 = templateKontoartFacade.save(kontoart2);

        TemplateKonto konto4 = new TemplateKonto("Konto4", "4", kontoart2);
        konto4 = templateKontoFacade.save(konto4);
        kontoart2.getTemplateKontos().add(konto4);
        kontoart2 = templateKontoartFacade.save(kontoart2);

        TemplateKonto konto5 = new TemplateKonto("Konto5", "5", kontoart3);
        konto5 = templateKontoFacade.save(konto5);
        kontoart3.getTemplateKontos().add(konto5);
        kontoart3 = templateKontoartFacade.save(kontoart3);

        TemplateKonto konto6 = new TemplateKonto("Konto6", "6", kontoart3);
        konto6 = templateKontoFacade.save(konto6);
        kontoart3.getTemplateKontos().add(konto6);
        kontoart3 = templateKontoartFacade.save(kontoart3);

        TemplateKonto konto7 = new TemplateKonto("Konto7", "7", kontoart4);
        konto7 = templateKontoFacade.save(konto7);
        kontoart4.getTemplateKontos().add(konto7);
        kontoart4 = templateKontoartFacade.save(kontoart4);

        TemplateKonto konto8 = new TemplateKonto("Konto8", "8", kontoart4);
        konto8 = templateKontoFacade.save(konto8);
        kontoart4.getTemplateKontos().add(konto8);
        kontoart4 = templateKontoartFacade.save(kontoart4);

        TemplateKonto konto9 = new TemplateKonto("Konto9", "9", kontoart5);
        konto9 = templateKontoFacade.save(konto9);
        kontoart5.getTemplateKontos().add(konto9);
        kontoart5 = templateKontoartFacade.save(kontoart5);

        TemplateKonto konto10 = new TemplateKonto("Konto10", "10", kontoart5);
        konto10 = templateKontoFacade.save(konto10);
        kontoart5.getTemplateKontos().add(konto10);
        kontoart5 = templateKontoartFacade.save(kontoart5);

        TemplateKonto konto11 = new TemplateKonto("Konto11", "11", kontoart6);
        konto11 = templateKontoFacade.save(konto11);
        kontoart6.getTemplateKontos().add(konto11);
        kontoart6 = templateKontoartFacade.save(kontoart6);

        TemplateKonto konto12 = new TemplateKonto("Konto12", "12", kontoart6);
        konto12 = templateKontoFacade.save(konto12);
        kontoart6.getTemplateKontos().add(konto12);
        kontoart6 = templateKontoartFacade.save(kontoart6);

        TemplateKonto konto13 = new TemplateKonto("Konto13", "13", kontoart7);
        konto13 = templateKontoFacade.save(konto13);
        kontoart7.getTemplateKontos().add(konto13);
        kontoart7 = templateKontoartFacade.save(kontoart7);

        TemplateKonto konto14 = new TemplateKonto("Konto14", "14", kontoart7);
        konto14 = templateKontoFacade.save(konto14);
        kontoart7.getTemplateKontos().add(konto14);
        kontoart7 = templateKontoartFacade.save(kontoart7);

        TemplateKonto konto15 = new TemplateKonto("Konto15", "15", kontoart8);
        konto15 = templateKontoFacade.save(konto15);
        kontoart8.getTemplateKontos().add(konto15);
        kontoart8 = templateKontoartFacade.save(kontoart8);

        TemplateKonto konto16 = new TemplateKonto("Konto16", "16", kontoart8);
        konto16 = templateKontoFacade.save(konto16);
        kontoart8.getTemplateKontos().add(konto16);
        kontoart8 = templateKontoartFacade.save(kontoart8);

        TemplateKonto konto17 = new TemplateKonto("Konto17", "17", kontoart9);
        konto17 = templateKontoFacade.save(konto17);
        kontoart9.getTemplateKontos().add(konto17);
        kontoart9 = templateKontoartFacade.save(kontoart9);

        TemplateKonto konto18 = new TemplateKonto("Konto18", "18", kontoart9);
        konto18 = templateKontoFacade.save(konto18);
        kontoart9.getTemplateKontos().add(konto18);
        kontoart9 = templateKontoartFacade.save(kontoart9);

        TemplateKonto konto19 = new TemplateKonto("Konto19", "19", kontoart10);
        konto19 = templateKontoFacade.save(konto19);
        kontoart10.getTemplateKontos().add(konto19);
        kontoart10 = templateKontoartFacade.save(kontoart10);

        TemplateKonto konto20 = new TemplateKonto("Konto20", "20", kontoart10);
        konto20 = templateKontoFacade.save(konto20);
        kontoart10.getTemplateKontos().add(konto20);
        kontoart10 = templateKontoartFacade.save(kontoart10);

        TemplateKonto konto21 = new TemplateKonto("Konto21", "21", kontoart11);
        konto21 = templateKontoFacade.save(konto21);
        kontoart11.getTemplateKontos().add(konto21);
        kontoart11 = templateKontoartFacade.save(kontoart11);

        TemplateKonto konto22 = new TemplateKonto("Konto22", "22", kontoart11);
        konto22 = templateKontoFacade.save(konto22);
        kontoart11.getTemplateKontos().add(konto22);
        kontoart11 = templateKontoartFacade.save(kontoart11);

        TemplateKonto konto23 = new TemplateKonto("Konto23", "23", kontoart12);
        konto23 = templateKontoFacade.save(konto23);
        kontoart12.getTemplateKontos().add(konto23);
        kontoart12 = templateKontoartFacade.save(kontoart12);

        TemplateKonto konto24 = new TemplateKonto("Konto24", "24", kontoart12);
        konto24 = templateKontoFacade.save(konto24);
        kontoart12.getTemplateKontos().add(konto24);
        kontoart12 = templateKontoartFacade.save(kontoart12);

        TemplateMehrwertsteuercode mehrwertsteuercode = new TemplateMehrwertsteuercode();
        mehrwertsteuercode.setBezeichnung("12%");
        mehrwertsteuercode.setCode("V12");
        mehrwertsteuercode.setProzent(12.0f);
        mehrwertsteuercode.setVerkauf(true);
        mehrwertsteuercode.setMehrwertsteuerKonto(konto1);
        mehrwertsteuercode.setTemplateBuchhaltung(buchhaltung);
        mehrwertsteuercode = templateMehrwertsteuercodeFacade.save(mehrwertsteuercode);

        buchhaltung = templateBuchhaltungFacade.save(buchhaltung);
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

        Button templateBuchhaltungTestBtn = new Button("Create TestData Template Buchhaltung", clickEvent -> {
            createTestDataTemplateBuchhaltung();
        });
        templateBuchhaltungTestBtn.setIcon(VaadinIcons.ABACUS);

        Button testCreateTemplateBuchhaltung = new Button("Test Create Template Buchhaltung", clickEvent -> {
            TemplateBuchhaltung bh = new TemplateBuchhaltung();
            bh.setBezeichnung("TestBH");
            bh = templateBuchhaltungFacade.save(bh);
        });

        addComponents(menu, rechnungTestBtn, templateBuchhaltungTestBtn, testCreateTemplateBuchhaltung);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
