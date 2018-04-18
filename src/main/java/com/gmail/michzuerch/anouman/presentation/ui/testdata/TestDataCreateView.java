package com.gmail.michzuerch.anouman.presentation.ui.testdata;

import com.gmail.michzuerch.anouman.backend.entity.*;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.*;
import com.gmail.michzuerch.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.*;

@CDIView("TestDataCreateView")
public class TestDataCreateView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TestDataCreateView.class.getName());

    @Inject
    Menu menu;

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    RechnungspositionDeltaspikeFacade rechnungspositionDeltaspikeFacade;

    @Inject
    AdresseDeltaspikeFacade adresseDeltaspikeFacade;

    @Inject
    AufwandDeltaspikeFacade aufwandDeltaspikeFacade;

    @Inject
    UzerDeltaspikeFacade uzerDeltaspikeFacade;

    @Inject
    UzerRoleDeltaspikeFacade uzerRoleDeltaspikeFacade;

    @Inject
    TemplateBuchhaltungDeltaspikeFacade templateBuchhaltungDeltaspikeFacade;

    @Inject
    TemplateKontoklasseDeltaspikeFacade templateKontoklasseDeltaspikeFacade;

    @Inject
    TemplateKontohauptgruppeDeltaspikeFacade templateKontohauptgruppeDeltaspikeFacade;

    @Inject
    TemplateKontogruppeDeltaspikeFacade templateKontogruppeDeltaspikeFacade;

    @Inject
    TemplateKontoDeltaspikeFacade templateKontoDeltaspikeFacade;

    @Inject
    TemplateMehrwertsteuercodeDeltaspikeFacade templateMehrwertsteuercodeDeltaspikeFacade;

    @Inject
    ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;

    @Inject
    ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

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

        uzerRoleDeltaspikeFacade.save(roleAdmin);
        uzerRoleDeltaspikeFacade.save(roleRechnung);
        uzerRoleDeltaspikeFacade.save(roleBuchhaltung);

        Uzer uzerAdmin = new Uzer();
        uzerAdmin.setDescription("Administrator");
        uzerAdmin.setPrincipal("admin");
        uzerAdmin.setPrincipal("passpass");
        uzerAdmin.getRoles().add(roleAdmin);
        uzerAdmin.getRoles().add(roleRechnung);
        uzerAdmin.getRoles().add(roleBuchhaltung);
        uzerDeltaspikeFacade.save(uzerAdmin);

        Uzer uzerKevin = new Uzer();
        uzerKevin.setDescription("Allerweltsuser");
        uzerKevin.setPrincipal("kevin");
        uzerKevin.setPrincipal("passpass");
        uzerKevin.getRoles().add(roleRechnung);
        uzerKevin.getRoles().add(roleBuchhaltung);
        uzerDeltaspikeFacade.save(uzerKevin);

        Uzer uzerAnonymous = new Uzer();
        uzerAnonymous.setDescription("Keine Rollen");
        uzerAnonymous.setPrincipal("anoymous");
        uzerAnonymous.setPrincipal("passpass");
        uzerDeltaspikeFacade.save(uzerAnonymous);

        Notification.show("Testdaten User erstellt", Notification.Type.TRAY_NOTIFICATION);

    }

    private void createArtikelstamm() {
        Artikelkategorie artikelkategorie = new Artikelkategorie();
        artikelkategorie.setBezeichnung("Futtermittel");
        artikelkategorie = artikelkategorieDeltaspikeFacade.save(artikelkategorie);

        Artikel artikel = new Artikel();
        artikel.setArtikelkategorie(artikelkategorie);
        artikel.setAnzahl(120d);
        artikel.setBezeichnung("Notebook");
        artikel.setBezeichnungLang("Acer Aspire E15 E-5575G-56GU");
        artikel.setMengeneinheit("Stück");
        artikel.setStueckpreis(975d);

        artikel = artikelDeltaspikeFacade.save(artikel);
        artikelkategorie.getArtikels().add(artikel);
        artikelkategorie = artikelkategorieDeltaspikeFacade.save(artikelkategorie);

        Notification.show("Testdaten Artikelstamm und Artikel erstellt", Notification.Type.TRAY_NOTIFICATION);
    }

    private void createTestDataRechnung() {
        Rechnung rechnung = new Rechnung();
        rechnung.setBezahlt(false);
        rechnung.setBezeichnung("Testrechnung");
        rechnung.setRechnungsdatum(LocalDate.now());
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
        adresse = adresseDeltaspikeFacade.save(adresse);

        rechnung.setAdresse(adresse);
        rechnung = rechnungDeltaspikeFacade.save(rechnung);

        Rechnungsposition rechnungsposition = new Rechnungsposition();
        rechnungsposition.setBezeichnung("Laptop");
        rechnungsposition.setAnzahl(2d);
        rechnungsposition.setBezeichnunglang("Acer Aspire E5");
        rechnungsposition.setMengeneinheit("Stück");
        rechnungsposition.setStueckpreis(1235.45d);
        rechnungsposition.setRechnung(rechnung);
        rechnungsposition = rechnungspositionDeltaspikeFacade.save(rechnungsposition);


        Aufwand aufwand = new Aufwand();
        aufwand.setBezeichnung("Entfernen Trojaner @Pigibot");
        aufwand.setTitel("Aufwand");

        //@todo Aufwand ist 0 Stunden wegen Enddatum

        Instant now = Instant.now();
        aufwand.setStart(LocalDateTime.ofInstant(now, ZoneOffset.UTC));

        aufwand.setEnd(LocalDateTime.ofInstant(now.plus(Duration.ofHours(3)), ZoneOffset.UTC));

        aufwand.setRechnung(rechnung);

        aufwand.setMoveable(true);
        aufwand.setResizable(true);

        aufwand = aufwandDeltaspikeFacade.save(aufwand);

        Notification.show("Testdaten Rechnung erstellt", Notification.Type.TRAY_NOTIFICATION);
    }

    private void createTestDataTemplateBuchhaltung() {
        TemplateBuchhaltung buchhaltung = new TemplateBuchhaltung();
        buchhaltung.setBezeichnung("Testdaten Template Buchhaltung");
        buchhaltung = templateBuchhaltungDeltaspikeFacade.save(buchhaltung);
        TemplateKontoklasse templateKontoklasse1 = new TemplateKontoklasse("Aktiven", "1", buchhaltung);
        templateKontoklasse1 = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse1);
        TemplateKontoklasse templateKontoklasse2 = new TemplateKontoklasse("Passive", "2", buchhaltung);
        templateKontoklasse2 = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse2);
        TemplateKontoklasse templateKontoklasse3 = new TemplateKontoklasse("Aufwand", "3", buchhaltung);
        templateKontoklasse3 = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse3);
        TemplateKontoklasse templateKontoklasse4 = new TemplateKontoklasse("Ertrag", "4", buchhaltung);
        templateKontoklasse4 = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse4);

        TemplateKontohauptgruppe templateKontohauptgruppe1 = new TemplateKontohauptgruppe("Hauptgruppe 1", "1", templateKontoklasse1);
        templateKontohauptgruppe1 = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe1);
        TemplateKontohauptgruppe templateKontohauptgruppe2 = new TemplateKontohauptgruppe("Hauptgruppe 2", "2", templateKontoklasse1);
        templateKontohauptgruppe2 = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe2);
        TemplateKontohauptgruppe templateKontohauptgruppe3 = new TemplateKontohauptgruppe("Hauptgruppe 3", "3", templateKontoklasse2);
        templateKontohauptgruppe3 = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe3);
        TemplateKontohauptgruppe templateKontohauptgruppe4 = new TemplateKontohauptgruppe("Hauptgruppe 4", "4", templateKontoklasse2);
        templateKontohauptgruppe4 = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe4);
        TemplateKontohauptgruppe templateKontohauptgruppe5 = new TemplateKontohauptgruppe("Hauptgruppe 5", "5", templateKontoklasse3);
        templateKontohauptgruppe5 = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe5);
        TemplateKontohauptgruppe templateKontohauptgruppe6 = new TemplateKontohauptgruppe("Hauptgruppe 6", "6", templateKontoklasse4);
        templateKontohauptgruppe6 = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe6);

        TemplateKontogruppe templateKontogruppe1 = new TemplateKontogruppe("Kontogruppe 1", "1", templateKontohauptgruppe1);
        templateKontogruppe1 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe1);
        TemplateKontogruppe templateKontogruppe2 = new TemplateKontogruppe("Kontogruppe 2", "2", templateKontohauptgruppe1);
        templateKontogruppe2 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe2);
        TemplateKontogruppe templateKontogruppe3 = new TemplateKontogruppe("Kontogruppe 3", "3", templateKontohauptgruppe2);
        templateKontogruppe3 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe3);
        TemplateKontogruppe templateKontogruppe4 = new TemplateKontogruppe("Kontogruppe 4", "4", templateKontohauptgruppe2);
        templateKontogruppe4 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe4);
        TemplateKontogruppe templateKontogruppe5 = new TemplateKontogruppe("Kontogruppe 5", "5", templateKontohauptgruppe3);
        templateKontogruppe5 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe5);
        TemplateKontogruppe templateKontogruppe6 = new TemplateKontogruppe("Kontogruppe 6", "6", templateKontohauptgruppe3);
        templateKontogruppe6 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe6);
        TemplateKontogruppe templateKontogruppe7 = new TemplateKontogruppe("Kontogruppe 7", "7", templateKontohauptgruppe4);
        templateKontogruppe7 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe7);
        TemplateKontogruppe templateKontogruppe8 = new TemplateKontogruppe("Kontogruppe 8", "8", templateKontohauptgruppe4);
        templateKontogruppe8 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe8);
        TemplateKontogruppe templateKontogruppe9 = new TemplateKontogruppe("Kontogruppe 9", "9", templateKontohauptgruppe5);
        templateKontogruppe9 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe9);
        TemplateKontogruppe templateKontogruppe10 = new TemplateKontogruppe("Kontogruppe 10", "10", templateKontohauptgruppe5);
        templateKontogruppe10 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe10);
        TemplateKontogruppe templateKontogruppe11 = new TemplateKontogruppe("Kontogruppe 11", "11", templateKontohauptgruppe6);
        templateKontogruppe11 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe11);
        TemplateKontogruppe templateKontogruppe12 = new TemplateKontogruppe("Kontogruppe 12", "12", templateKontohauptgruppe6);
        templateKontogruppe12 = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe12);


        TemplateKonto konto1 = new TemplateKonto("Konto1", "Konto1", "1", templateKontogruppe1);
        konto1 = templateKontoDeltaspikeFacade.save(konto1);
        TemplateKonto konto2 = new TemplateKonto("Konto2", "Konto2", "2", templateKontogruppe1);
        konto2 = templateKontoDeltaspikeFacade.save(konto2);
        TemplateKonto konto3 = new TemplateKonto("Konto3", "Konto3", "3", templateKontogruppe2);
        konto3 = templateKontoDeltaspikeFacade.save(konto3);
        TemplateKonto konto4 = new TemplateKonto("Konto4", "Konto4", "4", templateKontogruppe2);
        konto4 = templateKontoDeltaspikeFacade.save(konto4);
        TemplateKonto konto5 = new TemplateKonto("Konto5", "Konto5", "5", templateKontogruppe3);
        konto5 = templateKontoDeltaspikeFacade.save(konto5);
        TemplateKonto konto6 = new TemplateKonto("Konto6", "Konto6", "6", templateKontogruppe3);
        konto6 = templateKontoDeltaspikeFacade.save(konto6);
        TemplateKonto konto7 = new TemplateKonto("Konto7", "Konto7", "7", templateKontogruppe4);
        konto7 = templateKontoDeltaspikeFacade.save(konto7);
        TemplateKonto konto8 = new TemplateKonto("Konto8", "Konto8", "8", templateKontogruppe4);
        konto8 = templateKontoDeltaspikeFacade.save(konto8);
        TemplateKonto konto9 = new TemplateKonto("Konto9", "Konto9", "9", templateKontogruppe5);
        konto9 = templateKontoDeltaspikeFacade.save(konto9);
        TemplateKonto konto10 = new TemplateKonto("Konto10", "Konto10", "10", templateKontogruppe5);
        konto10 = templateKontoDeltaspikeFacade.save(konto10);
        TemplateKonto konto11 = new TemplateKonto("Konto11", "Konto11", "11", templateKontogruppe6);
        konto11 = templateKontoDeltaspikeFacade.save(konto11);
        TemplateKonto konto12 = new TemplateKonto("Konto12", "Konto12", "12", templateKontogruppe6);
        konto12 = templateKontoDeltaspikeFacade.save(konto12);
        TemplateKonto konto13 = new TemplateKonto("Konto13", "Konto13", "13", templateKontogruppe7);
        konto13 = templateKontoDeltaspikeFacade.save(konto13);
        TemplateKonto konto14 = new TemplateKonto("Konto14", "Konto14", "14", templateKontogruppe7);
        konto14 = templateKontoDeltaspikeFacade.save(konto14);
        TemplateKonto konto15 = new TemplateKonto("Konto15", "Konto15", "15", templateKontogruppe8);
        konto15 = templateKontoDeltaspikeFacade.save(konto15);
        TemplateKonto konto16 = new TemplateKonto("Konto16", "Konto16", "16", templateKontogruppe8);
        konto16 = templateKontoDeltaspikeFacade.save(konto16);
        TemplateKonto konto17 = new TemplateKonto("Konto17", "Konto17", "17", templateKontogruppe9);
        konto17 = templateKontoDeltaspikeFacade.save(konto17);
        TemplateKonto konto18 = new TemplateKonto("Konto18", "Konto18", "18", templateKontogruppe9);
        konto18 = templateKontoDeltaspikeFacade.save(konto18);
        TemplateKonto konto19 = new TemplateKonto("Konto19", "Konto19", "19", templateKontogruppe10);
        konto19 = templateKontoDeltaspikeFacade.save(konto19);
        TemplateKonto konto20 = new TemplateKonto("Konto20", "Konto20", "20", templateKontogruppe10);
        konto20 = templateKontoDeltaspikeFacade.save(konto20);
        TemplateKonto konto21 = new TemplateKonto("Konto21", "Konto21", "21", templateKontogruppe11);
        konto21 = templateKontoDeltaspikeFacade.save(konto21);
        TemplateKonto konto22 = new TemplateKonto("Konto22", "Konto22", "22", templateKontogruppe11);
        konto22 = templateKontoDeltaspikeFacade.save(konto22);
        TemplateKonto konto23 = new TemplateKonto("Konto23", "Konto23", "23", templateKontogruppe12);
        konto23 = templateKontoDeltaspikeFacade.save(konto23);
        TemplateKonto konto24 = new TemplateKonto("Konto24", "Konto24", "24", templateKontogruppe12);
        konto24 = templateKontoDeltaspikeFacade.save(konto24);

        TemplateMehrwertsteuercode mehrwertsteuercode = new TemplateMehrwertsteuercode();
        mehrwertsteuercode.setBezeichnung("12%");
        mehrwertsteuercode.setCode("V12");
        mehrwertsteuercode.setProzent(12.0d);
        mehrwertsteuercode.setVerkauf(true);
        mehrwertsteuercode.setTemplateMehrwertsteuerKonto(konto1);
        mehrwertsteuercode.setTemplateBuchhaltung(buchhaltung);

        mehrwertsteuercode = templateMehrwertsteuercodeDeltaspikeFacade.save(mehrwertsteuercode);

        Notification.show("Testdaten Template Buchhaltung erstellt", Notification.Type.TRAY_NOTIFICATION);
    }

    private void createContent() {
        Button createRechnungTestBtn = new Button("Create TestData Rechnung", clickEvent -> {
            createTestDataRechnung();
        });
        createRechnungTestBtn.setIcon(VaadinIcons.ACADEMY_CAP);

        Button uzerTestBtn = new Button("Create TestData Uzer", clickEvent -> {
            createTestDataUzer();
        });
        uzerTestBtn.setIcon(VaadinIcons.CAMERA);

        Button createTemplateBuchhaltungBtn = new Button("Test Create Template Buchhaltung", clickEvent -> {
            createTestDataTemplateBuchhaltung();
        });
        createTemplateBuchhaltungBtn.setIcon(VaadinIcons.TOOTH);

        Button createArtikelstammBtn = new Button("Create Testdata Artikelstamm", clickEvent -> {
            createArtikelstamm();
        });
        createArtikelstammBtn.setIcon(VaadinIcons.EYE);

        setMargin(false);
        setSpacing(false);
        addComponents(createRechnungTestBtn, createTemplateBuchhaltungBtn, createArtikelstammBtn);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        createContent();
    }
}
