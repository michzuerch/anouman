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


    private TemplateKontoklasse createTemplateKontoklasse(TemplateBuchhaltung templateBuchhaltung, String kontonummer, String bezeichnung) {
        TemplateKontoklasse val = new TemplateKontoklasse();
        val.setTemplateBuchhaltung(templateBuchhaltung);
        val.setKontonummer(kontonummer);
        val.setBezeichnung(bezeichnung);
        templateKontoklasseFacade.save(val);
        return val;
    }

    private TemplateKontogruppe createTemplateKontogruppe(TemplateKontoklasse templateKontoklasse, String kontonummer, String bezeichnung) {
        TemplateKontogruppe val = new TemplateKontogruppe();
        val.setTemplateKontoklasse(templateKontoklasse);
        val.setKontonummer(kontonummer);
        val.setBezeichnung(bezeichnung);
        templateKontogruppeFacade.save(val);
        return val;
    }

    private TemplateKontoart createTemplateKontoart(TemplateKontogruppe templateKontogruppe, String kontonummer, String bezeichnung) {
        TemplateKontoart val = new TemplateKontoart();
        val.setTemplateKontogruppe(templateKontogruppe);
        val.setKontonummer(kontonummer);
        val.setBezeichnung(bezeichnung);
        templateKontoartFacade.save(val);
        return val;
    }

    private TemplateKonto createTemplateKonto(TemplateKontoart templateKontoart, String kontonummer, String bezeichnung) {
        TemplateKonto val = new TemplateKonto();
        val.setTemplateKontoart(templateKontoart);
        val.setKontonummer(kontonummer);
        val.setBezeichnung(bezeichnung);
        templateKontoFacade.save(val);
        return val;
    }


    private void createTestDataTemplateBuchhaltung() {
        TemplateBuchhaltung templateBuchhaltung = new TemplateBuchhaltung();
        templateBuchhaltung.setBezeichnung("Testdaten Template Buchhaltung");
        templateBuchhaltung = templateBuchhaltungFacade.save(templateBuchhaltung);

        TemplateMehrwertsteuercode templateMehrwertsteuercode = new TemplateMehrwertsteuercode();
        templateMehrwertsteuercode.setBezeichnung("12%");
        templateMehrwertsteuercode.setCode("V12");
        templateMehrwertsteuercode.setProzent(12.0f);
        templateMehrwertsteuercode.setVerkauf(true);
        templateMehrwertsteuercode = templateMehrwertsteuercodeFacade.save(templateMehrwertsteuercode);
        templateBuchhaltung.getTemplateMehrwertsteuercodes().add(templateMehrwertsteuercode);

        TemplateKontoklasse templateKontoklasse1 = new TemplateKontoklasse("Aktiven","1", templateBuchhaltung);
        TemplateKontoklasse templateKontoklasse2 = new TemplateKontoklasse("Passive","2", templateBuchhaltung);
        TemplateKontoklasse templateKontoklasse3 = new TemplateKontoklasse("Aufwand","3", templateBuchhaltung);
        TemplateKontoklasse templateKontoklasse4 = new TemplateKontoklasse("Ertrag","4", templateBuchhaltung);
        templateBuchhaltung.getTemplateKontoklasses().add(templateKontoklasse1);
        templateBuchhaltung.getTemplateKontoklasses().add(templateKontoklasse2);
        templateBuchhaltung.getTemplateKontoklasses().add(templateKontoklasse3);
        templateBuchhaltung.getTemplateKontoklasses().add(templateKontoklasse4);

        /*
        TemplateKontogruppe kontogruppe1 = createTemplateKontogruppe(templateKontoklasse1, "1", "TestKontoGruppe");
        TemplateKontogruppe kontogruppe2 = createTemplateKontogruppe(templateKontoklasse1, "2", "TestKontoGruppe");
        TemplateKontogruppe kontogruppe3 = createTemplateKontogruppe(templateKontoklasse2, "3", "TestKontoGruppe");
        TemplateKontogruppe kontogruppe4 = createTemplateKontogruppe(templateKontoklasse2, "4", "TestKontoGruppe");
        TemplateKontogruppe kontogruppe5 = createTemplateKontogruppe(templateKontoklasse3, "5", "TestKontoGruppe");
        TemplateKontogruppe kontogruppe6 = createTemplateKontogruppe(templateKontoklasse4, "6", "TestKontoGruppe");

        TemplateKontoart kontoart1 = createTemplateKontoart(kontogruppe1, "5", "TestKontoArt");
        TemplateKontoart kontoart2 = createTemplateKontoart(kontogruppe1, "5", "TestKontoArt");
        TemplateKontoart kontoart3 = createTemplateKontoart(kontogruppe2, "5", "TestKontoArt");
        TemplateKontoart kontoart4 = createTemplateKontoart(kontogruppe3, "5", "TestKontoArt");
        TemplateKontoart kontoart5 = createTemplateKontoart(kontogruppe4, "5", "TestKontoArt");
        TemplateKontoart kontoart6 = createTemplateKontoart(kontogruppe5, "5", "TestKontoArt");
        TemplateKontoart kontoart7 = createTemplateKontoart(kontogruppe5, "5", "TestKontoArt");
        TemplateKontoart kontoart8 = createTemplateKontoart(kontogruppe5, "5", "TestKontoArt");
        TemplateKontoart kontoart9 = createTemplateKontoart(kontogruppe5, "5", "TestKontoArt");
        TemplateKontoart kontoart10 = createTemplateKontoart(kontogruppe6, "5", "TestKontoArt");
        TemplateKontoart kontoart11 = createTemplateKontoart(kontogruppe6, "5", "TestKontoArt");
        TemplateKontoart kontoart12 = createTemplateKontoart(kontogruppe6, "5", "TestKontoArt");


        TemplateKonto konto1 = createTemplateKonto(kontoart1, "2", "Testkonto");
        TemplateKonto konto2 = createTemplateKonto(kontoart1, "2", "Testkonto");
        TemplateKonto konto3 = createTemplateKonto(kontoart2, "2", "Testkonto");
        TemplateKonto konto4 = createTemplateKonto(kontoart3, "2", "Testkonto");
        TemplateKonto konto5 = createTemplateKonto(kontoart4, "2", "Testkonto");
        TemplateKonto konto6 = createTemplateKonto(kontoart5, "2", "Testkonto");
        TemplateKonto konto7 = createTemplateKonto(kontoart6, "2", "Testkonto");
        TemplateKonto konto8 = createTemplateKonto(kontoart7, "2", "Testkonto");
        TemplateKonto konto9 = createTemplateKonto(kontoart8, "2", "Testkonto");
        TemplateKonto konto10 = createTemplateKonto(kontoart9, "2", "Testkonto");
        TemplateKonto konto11 = createTemplateKonto(kontoart10, "2", "Testkonto");
        TemplateKonto konto12 = createTemplateKonto(kontoart11, "2", "Testkonto");
        TemplateKonto konto13 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto14 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto15 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto16 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto17 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto18 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto19 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto20 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto21 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto22 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto23 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto24 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto25 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto26 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto27 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto28 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto29 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto30 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto31 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto32 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto33 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto34 = createTemplateKonto(kontoart12, "2", "Testkonto");
        TemplateKonto konto35 = createTemplateKonto(kontoart1, "2", "Testkonto");
        TemplateKonto kontoMehrwertsteuer = createTemplateKonto(kontoart10, "3", "Mehrwertsteuer");
        templateMehrwertsteuercode.setMehrwertsteuerKonto(kontoMehrwertsteuer);
        templateMehrwertsteuercode = templateMehrwertsteuercodeFacade.save(templateMehrwertsteuercode);
        */

        templateBuchhaltung = templateBuchhaltungFacade.save(templateBuchhaltung);
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

        addComponents(menu, rechnungTestBtn, templateBuchhaltungTestBtn);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
