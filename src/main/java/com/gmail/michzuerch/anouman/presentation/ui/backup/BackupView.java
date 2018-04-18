package com.gmail.michzuerch.anouman.presentation.ui.backup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.gmail.michzuerch.anouman.backend.entity.*;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.adressen.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel.BackupArtikel;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel.BackupArtikelbild;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel.BackupArtikelkategorie;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel.BackupArtikelkategories;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.button.DownloadButton;
import server.droporchoose.UploadComponent;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Created by michzuerch on 25.07.15.
 */
@CDIView("BackupView")
public class BackupView extends VerticalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(BackupView.class.getName());
    @Inject
    AdresseDeltaspikeFacade adresseDeltaspikeFacade;
    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;
    @Inject
    BuchhaltungDeltaspikeFacade buchhaltungDeltaspikeFacade;
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
    KontoklasseDeltaspikeFacade kontoklasseDeltaspikeFacade;
    @Inject
    KontohauptgruppeDeltaspikeFacade kontohauptgruppeDeltaspikeFacade;
    @Inject
    KontogruppeDeltaspikeFacade kontogruppeDeltaspikeFacade;
    @Inject
    KontoDeltaspikeFacade kontoDeltaspikeFacade;
    @Inject
    BuchungDeltaspikeFacade buchungDeltaspikeFacade;
    @Inject
    ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;
    @Inject
    ArtikelDeltaspikeFacade artikelDeltaspikeFacade;
    @Inject
    ArtikelbildDeltaspikeFacade artikelbildDeltaspikeFacade;

    private BackupBuchhaltungen getBackupBuchhaltungen() {
        BackupBuchhaltungen backupBuchhaltungen = new BackupBuchhaltungen();
        Instant now = Instant.now();
        backupBuchhaltungen.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));

        buchhaltungDeltaspikeFacade.findAll().stream().forEach(buchhaltung -> {
            BackupBuchhaltung backupBuchhaltung = new BackupBuchhaltung(buchhaltung.getBezeichnung(), buchhaltung.getJahr());

            buchhaltung.getKontoklasse().stream().forEach(kontoklasse -> {
                BackupKontoklasse backupKontoklasse = new BackupKontoklasse(kontoklasse.getBezeichnung(), kontoklasse.getKontonummer());
                backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
                kontoklasse.getKontohauptgruppes().stream().forEach(kontohauptgruppe -> {
                    BackupKontohauptgruppe backupKontohauptgruppe = new BackupKontohauptgruppe(kontohauptgruppe.getBezeichnung(), kontohauptgruppe.getKontonummer());
                    backupKontoklasse.getBackupKontohauptgruppes().add(backupKontohauptgruppe);
                    kontohauptgruppe.getKontogruppes().stream().forEach(kontogruppe -> {
                        BackupKontogruppe backupKontogruppe = new BackupKontogruppe(kontogruppe.getBezeichnung(), kontogruppe.getKontonummer());
                        backupKontohauptgruppe.getBackupKontogruppes().add(backupKontogruppe);
                        kontogruppe.getKontos().stream().forEach(konto -> {
                            BackupKonto backupKonto = new BackupKonto();
                            backupKonto.setId(konto.getId());
                            backupKonto.setBezeichnung(konto.getBezeichnung());
                            backupKonto.setKontonummer(konto.getKontonummer());
                            backupKontogruppe.getKontos().add(backupKonto);
                            konto.getMehrwertsteuercodes().stream().forEach(mehrwertsteuercode -> {
                                BackupMehrwertsteuercode backupMehrwertsteuercode = new BackupMehrwertsteuercode();
                                backupMehrwertsteuercode.setId(mehrwertsteuercode.getId());
                                backupMehrwertsteuercode.setBezeichnung(mehrwertsteuercode.getBezeichnung());
                                backupMehrwertsteuercode.setCode(mehrwertsteuercode.getCode());
                                backupMehrwertsteuercode.setProzent(mehrwertsteuercode.getProzent());
                                backupMehrwertsteuercode.setVerkauf(mehrwertsteuercode.isVerkauf());
                                backupKonto.getMehrwertsteuercodes().add(backupMehrwertsteuercode);
                            });
                        });
                    });
                });
            });
            backupBuchhaltungen.addBuchhaltung(backupBuchhaltung);
        });
        return backupBuchhaltungen;
    }

    private BackupArtikelkategories getBackupArtikelkategories() {
        BackupArtikelkategories backupArtikelkategories = new BackupArtikelkategories();
        Instant now = Instant.now();
        backupArtikelkategories.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));

        artikelkategorieDeltaspikeFacade.findAll().stream().forEach(artikelkategorie -> {
            BackupArtikelkategorie backupArtikelkategorie = new BackupArtikelkategorie();
            backupArtikelkategorie.setBezeichnung(artikelkategorie.getBezeichnung());
            backupArtikelkategories.getBackupArtikelkategories().add(backupArtikelkategorie);

            artikelkategorie.getArtikels().stream().forEach(artikel -> {
                BackupArtikel backupArtikel = new BackupArtikel();
                backupArtikel.setAnzahl(artikel.getAnzahl());
                backupArtikel.setBezeichnung(artikel.getBezeichnung());
                backupArtikel.setBezeichnungLang(artikel.getBezeichnungLang());
                backupArtikel.setMengeneinheit(artikel.getMengeneinheit());
                backupArtikel.setStueckpreis(artikel.getStueckpreis());
                backupArtikelkategorie.getBackupArtikels().add(backupArtikel);

                artikel.getArtikelbilds().stream().forEach(artikelbild -> {
                    BackupArtikelbild backupArtikelbild = new BackupArtikelbild();
                    backupArtikelbild.setTitel(artikelbild.getTitel());
                    backupArtikelbild.setMimetype(artikelbild.getMimetype());
                    backupArtikelbild.setBild(artikelbild.getImage());
                    backupArtikel.getBackupArtikelbilds().add(backupArtikelbild);
                });
            });
        });
        return backupArtikelkategories;
    }

    private BackupBuchhaltungen getBackupBuchhaltung(Buchhaltung buchhaltung) {
        BackupBuchhaltungen backupBuchhaltungen = new BackupBuchhaltungen();
        Instant now = Instant.now();
        backupBuchhaltungen.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));
        BackupBuchhaltung backupBuchhaltung = new BackupBuchhaltung(buchhaltung.getBezeichnung(), buchhaltung.getJahr());

        buchhaltung.getKontoklasse().stream().forEach(kontoklasse -> {
            BackupKontoklasse backupKontoklasse = new BackupKontoklasse(kontoklasse.getBezeichnung(), kontoklasse.getKontonummer());
            backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
            kontoklasse.getKontohauptgruppes().stream().forEach(kontohauptgruppe -> {
                BackupKontohauptgruppe backupKontohauptgruppe = new BackupKontohauptgruppe(kontohauptgruppe.getBezeichnung(), kontohauptgruppe.getKontonummer());
                backupKontoklasse.getBackupKontohauptgruppes().add(backupKontohauptgruppe);
                kontohauptgruppe.getKontogruppes().stream().forEach(kontogruppe -> {
                    BackupKontogruppe backupKontogruppe = new BackupKontogruppe(kontogruppe.getBezeichnung(), kontogruppe.getKontonummer());
                    backupKontohauptgruppe.getBackupKontogruppes().add(backupKontogruppe);
                    kontogruppe.getKontos().stream().forEach(konto -> {
                        BackupKonto backupKonto = new BackupKonto();
                        backupKonto.setId(konto.getId());
                        backupKonto.setBezeichnung(konto.getBezeichnung());
                        backupKonto.setKontonummer(konto.getKontonummer());
                        backupKontogruppe.getKontos().add(backupKonto);
                        konto.getMehrwertsteuercodes().stream().forEach(mehrwertsteuercode -> {
                            BackupMehrwertsteuercode backupMehrwertsteuercode = new BackupMehrwertsteuercode();
                            backupMehrwertsteuercode.setId(mehrwertsteuercode.getId());
                            backupMehrwertsteuercode.setBezeichnung(mehrwertsteuercode.getBezeichnung());
                            backupMehrwertsteuercode.setCode(mehrwertsteuercode.getCode());
                            backupMehrwertsteuercode.setProzent(mehrwertsteuercode.getProzent());
                            backupMehrwertsteuercode.setVerkauf(mehrwertsteuercode.isVerkauf());
                            backupKonto.getMehrwertsteuercodes().add(backupMehrwertsteuercode);
                        });
                    });
                });
            });
            backupBuchhaltungen.addBuchhaltung(backupBuchhaltung);
        });

        return backupBuchhaltungen;
    }

    private BackupTemplateBuchhaltungen getBackupTemplateBuchhaltungen() {
        BackupTemplateBuchhaltungen backupTemplateBuchhaltungen = new BackupTemplateBuchhaltungen();
        Instant now = Instant.now();
        backupTemplateBuchhaltungen.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));
        templateBuchhaltungDeltaspikeFacade.findAll().stream().forEach(templateBuchhaltung -> {
            BackupTemplateBuchhaltung backupBuchhaltung = new BackupTemplateBuchhaltung();
            backupBuchhaltung.setBezeichnung(templateBuchhaltung.getBezeichnung());
            backupBuchhaltung.setId(templateBuchhaltung.getId());
            templateBuchhaltung.getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
                BackupTemplateKontoklasse backupKontoklasse = new BackupTemplateKontoklasse();
                backupKontoklasse.setBezeichnung(templateKontoklasse.getBezeichnung());
                backupKontoklasse.setKontonummer(templateKontoklasse.getKontonummer());
                backupKontoklasse.setId(templateKontoklasse.getId());
                backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
                templateKontoklasse.getTemplateKontohauptgruppes().stream().forEach(templateKontohauptgruppe -> {
                    BackupTemplateKontohauptgruppe backupTemplateKontohauptgruppe = new BackupTemplateKontohauptgruppe();
                    backupTemplateKontohauptgruppe.setBezeichnung(templateKontohauptgruppe.getBezeichnung());
                    backupTemplateKontohauptgruppe.setKontonummer(templateKontohauptgruppe.getKontonummer());
                    backupTemplateKontohauptgruppe.setId(templateKontohauptgruppe.getId());
                    backupKontoklasse.getBackupTemplateKontohauptgruppes().add(backupTemplateKontohauptgruppe);
                    templateKontohauptgruppe.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                        BackupTemplateKontogruppe backupTemplateKontogruppe = new BackupTemplateKontogruppe();
                        backupTemplateKontogruppe.setBezeichnung(templateKontogruppe.getBezeichnung());
                        backupTemplateKontogruppe.setKontonummer(templateKontogruppe.getKontonummer());
                        backupTemplateKontogruppe.setId(templateKontogruppe.getId());
                        backupTemplateKontohauptgruppe.getBackupTemplateKontogruppes().add(backupTemplateKontogruppe);
                        templateKontogruppe.getTemplateKontos().stream().forEach(templateKonto -> {
                            BackupTemplateKonto backupTemplateKonto = new BackupTemplateKonto();
                            backupTemplateKonto.setBezeichnung(templateKonto.getBezeichnung());
                            backupTemplateKonto.setKontonummer(templateKonto.getKontonummer());
                            backupTemplateKonto.setBemerkung(templateKonto.getBemerkung());
                            backupTemplateKonto.setShowKontonummer(templateKonto.getShowKontonummer());
                            backupTemplateKonto.setId(templateKonto.getId());
                            backupTemplateKontogruppe.getBackupTemplateKontos().add(backupTemplateKonto);
                            templateKonto.getTemplateMehrwertsteuercodes().stream().forEach(templateMehrwertsteuercode -> {
                                BackupTemplateMehrwertsteuercode backupTemplateMehrwertsteuercode = new BackupTemplateMehrwertsteuercode();
                                backupTemplateMehrwertsteuercode.setId(templateMehrwertsteuercode.getId());
                                backupTemplateMehrwertsteuercode.setCode(templateMehrwertsteuercode.getCode());
                                backupTemplateMehrwertsteuercode.setBezeichnung(templateMehrwertsteuercode.getBezeichnung());
                                backupTemplateMehrwertsteuercode.setProzent(templateMehrwertsteuercode.getProzent());
                                backupTemplateMehrwertsteuercode.setVerkauf(templateMehrwertsteuercode.isVerkauf());
                                backupTemplateMehrwertsteuercode.setKonto(templateKonto.getId());
                                backupTemplateKonto.getBackupTemplateMehrwertsteuercodes().add(backupTemplateMehrwertsteuercode);
                            });
                        });
                    });
                });
                backupTemplateBuchhaltungen.addBuchhaltung(backupBuchhaltung);
            });
        });
        return backupTemplateBuchhaltungen;
    }

    private BackupTemplateBuchhaltungen getBackupTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        BackupTemplateBuchhaltungen backupTemplateBuchhaltungen = new BackupTemplateBuchhaltungen();
        Instant now = Instant.now();
        backupTemplateBuchhaltungen.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));

        BackupTemplateBuchhaltung backupBuchhaltung = new BackupTemplateBuchhaltung();
        backupBuchhaltung.setBezeichnung(templateBuchhaltung.getBezeichnung());
        backupBuchhaltung.setId(templateBuchhaltung.getId());
        templateBuchhaltung.getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
            BackupTemplateKontoklasse backupKontoklasse = new BackupTemplateKontoklasse();
            backupKontoklasse.setBezeichnung(templateKontoklasse.getBezeichnung());
            backupKontoklasse.setKontonummer(templateKontoklasse.getKontonummer());
            backupKontoklasse.setId(templateKontoklasse.getId());
            backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
            templateKontoklasse.getTemplateKontohauptgruppes().stream().forEach(templateKontohauptgruppe -> {
                BackupTemplateKontohauptgruppe backupTemplateKontohauptgruppe = new BackupTemplateKontohauptgruppe();
                backupTemplateKontohauptgruppe.setBezeichnung(templateKontohauptgruppe.getBezeichnung());
                backupTemplateKontohauptgruppe.setKontonummer(templateKontohauptgruppe.getKontonummer());
                backupTemplateKontohauptgruppe.setId(templateKontohauptgruppe.getId());
                backupKontoklasse.getBackupTemplateKontohauptgruppes().add(backupTemplateKontohauptgruppe);
                templateKontohauptgruppe.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                    BackupTemplateKontogruppe backupTemplateKontogruppe = new BackupTemplateKontogruppe();
                    backupTemplateKontogruppe.setBezeichnung(templateKontogruppe.getBezeichnung());
                    backupTemplateKontogruppe.setKontonummer(templateKontogruppe.getKontonummer());
                    backupTemplateKontogruppe.setId(templateKontogruppe.getId());
                    backupTemplateKontohauptgruppe.getBackupTemplateKontogruppes().add(backupTemplateKontogruppe);
                    templateKontogruppe.getTemplateKontos().stream().forEach(templateKonto -> {
                        BackupTemplateKonto backupTemplateKonto = new BackupTemplateKonto();
                        backupTemplateKonto.setBezeichnung(templateKonto.getBezeichnung());
                        backupTemplateKonto.setKontonummer(templateKonto.getKontonummer());
                        backupTemplateKonto.setBemerkung(templateKonto.getBemerkung());
                        backupTemplateKonto.setShowKontonummer(templateKonto.getShowKontonummer());
                        backupTemplateKonto.setId(templateKonto.getId());
                        backupTemplateKontogruppe.getBackupTemplateKontos().add(backupTemplateKonto);
                        templateKonto.getTemplateMehrwertsteuercodes().stream().forEach(templateMehrwertsteuercode -> {
                            BackupTemplateMehrwertsteuercode backupTemplateMehrwertsteuercode = new BackupTemplateMehrwertsteuercode();
                            backupTemplateMehrwertsteuercode.setId(templateMehrwertsteuercode.getId());
                            backupTemplateMehrwertsteuercode.setCode(templateMehrwertsteuercode.getCode());
                            backupTemplateMehrwertsteuercode.setBezeichnung(templateMehrwertsteuercode.getBezeichnung());
                            backupTemplateMehrwertsteuercode.setProzent(templateMehrwertsteuercode.getProzent());
                            backupTemplateMehrwertsteuercode.setVerkauf(templateMehrwertsteuercode.isVerkauf());
                            backupTemplateMehrwertsteuercode.setKonto(templateKonto.getId());
                            backupTemplateKonto.getBackupTemplateMehrwertsteuercodes().add(backupTemplateMehrwertsteuercode);
                        });
                    });
                });
                backupTemplateBuchhaltungen.addBuchhaltung(backupBuchhaltung);
            });
        });
        return backupTemplateBuchhaltungen;
    }

    private BackupAdressen getBackupAdressen() {
        BackupAdressen backupAdressen = new BackupAdressen();
        Instant now = Instant.now();
        backupAdressen.setBackupdatum(LocalDateTime.now());
        adresseDeltaspikeFacade.findAll().stream().forEach(adresse -> {
            BackupAdresse backupAdresse = new BackupAdresse();
            backupAdresse.setAnrede(adresse.getAnrede());
            backupAdresse.setFirma(adresse.getFirma());
            backupAdresse.setNachname(adresse.getNachname());
            backupAdresse.setOrt(adresse.getOrt());
            backupAdresse.setPostleitzahl(adresse.getPostleitzahl());
            backupAdresse.setStrasse(adresse.getStrasse());
            backupAdresse.setStundensatz(adresse.getStundensatz());
            backupAdresse.setVorname(adresse.getVorname());
            backupAdressen.getAdressen().add(backupAdresse);
            adresse.getRechnungen().stream().forEach(rechnung -> {
                BackupRechnung backupRechnung = new BackupRechnung();
                backupRechnung.setBezeichnung(rechnung.getBezeichnung());
                backupRechnung.setFaelligInTagen(rechnung.getFaelligInTagen());
                backupRechnung.setBezahlt(rechnung.isBezahlt());
                backupRechnung.setVerschickt(rechnung.isVerschickt());
                backupRechnung.setRechnungsdatum(rechnung.getRechnungsdatum());

                rechnung.getRechnungspositionen().stream().forEach(rechnungsposition -> {
                    BackupRechnungsposition backupRechnungsposition = new BackupRechnungsposition();
                    backupRechnungsposition.setBezeichnung(rechnungsposition.getBezeichnung());
                    backupRechnungsposition.setBezeichnunglang(rechnungsposition.getBezeichnunglang());
                    backupRechnungsposition.setAnzahl(rechnungsposition.getAnzahl());
                    backupRechnungsposition.setMengeneinheit(rechnungsposition.getMengeneinheit());
                    backupRechnungsposition.setStueckpreis(rechnungsposition.getStueckpreis());
                    backupRechnung.getRechnungspositions().add(backupRechnungsposition);
                });

                rechnung.getAufwands().stream().forEach(aufwand -> {
                    BackupAufwand backupAufwand = new BackupAufwand();
                    backupAufwand.setBezeichnung(aufwand.getBezeichnung());
                    backupAufwand.setTitel(aufwand.getTitel());
                    backupAufwand.setStart(aufwand.getStart());
                    backupAufwand.setEnd(aufwand.getEnd());
                    backupRechnung.getAufwands().add(backupAufwand);
                });
                backupAdresse.getRechnungen().add(backupRechnung);
            });
        });
        return backupAdressen;
    }

    private BackupAdressen getBackupAdresse(Adresse adresse) {
        BackupAdressen backupAdressen = new BackupAdressen();
        Instant now = Instant.now();
        backupAdressen.setBackupdatum(LocalDateTime.now());

        BackupAdresse backupAdresse = new BackupAdresse();
        backupAdresse.setAnrede(adresse.getAnrede());
        backupAdresse.setFirma(adresse.getFirma());
        backupAdresse.setNachname(adresse.getNachname());
        backupAdresse.setOrt(adresse.getOrt());
        backupAdresse.setPostleitzahl(adresse.getPostleitzahl());
        backupAdresse.setStrasse(adresse.getStrasse());
        backupAdresse.setStundensatz(adresse.getStundensatz());
        backupAdresse.setVorname(adresse.getVorname());
        backupAdressen.getAdressen().add(backupAdresse);

        adresse.getRechnungen().stream().forEach(rechnung -> {
            BackupRechnung backupRechnung = new BackupRechnung();
            backupRechnung.setBezahlt(rechnung.isBezahlt());
            backupRechnung.setVerschickt(rechnung.isVerschickt());
            backupRechnung.setBezeichnung(rechnung.getBezeichnung());
            backupRechnung.setFaelligInTagen(rechnung.getFaelligInTagen());
            backupRechnung.setRechnungsdatum(rechnung.getRechnungsdatum());
            backupAdresse.getRechnungen().add(backupRechnung);

            rechnung.getRechnungspositionen().stream().forEach(rechnungsposition -> {
                BackupRechnungsposition backupRechnungsposition = new BackupRechnungsposition();
                backupRechnungsposition.setAnzahl(rechnungsposition.getAnzahl());
                backupRechnungsposition.setBezeichnung(rechnungsposition.getBezeichnung());
                backupRechnungsposition.setBezeichnunglang(rechnungsposition.getBezeichnunglang());
                backupRechnungsposition.setMengeneinheit(rechnungsposition.getMengeneinheit());
                backupRechnungsposition.setStueckpreis(rechnungsposition.getStueckpreis());
                backupRechnung.getRechnungspositions().add(backupRechnungsposition);
            });

            rechnung.getAufwands().stream().forEach(aufwand -> {
                BackupAufwand backupAufwand = new BackupAufwand();
                backupAufwand.setTitel(aufwand.getTitel());
                backupAufwand.setBezeichnung(aufwand.getBezeichnung());
                backupAufwand.setStart(aufwand.getStart());
                backupAufwand.setEnd(aufwand.getEnd());
                backupRechnung.getAufwands().add(backupAufwand);
            });
        });

        return backupAdressen;
    }

    private void createContent() {

        //Adressen, Rechnungen
        Button downloaderAdressen = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupAdressen());
                stream.flush();

                mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(new File("test.json")), getBackupAdressen());
                //BackupAdressen backupAdressen = mapper.readValue(new FileInputStream(new File("test.json")), BackupAdressen.class);
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).withCaption("Alle Adressen, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<Adresse> comboAdresse = new ComboBox<>();
        Button downloaderAdresse = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupAdresse(comboAdresse.getValue()));
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).withCaption("Adresse, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        //Buchhaltungen
        Button downloaderBuchhaltungen = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupBuchhaltungen());
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).withCaption("Alle Buchhaltungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<Buchhaltung> comboBuchhaltung = new ComboBox<>();
        Button downloaderBuchhaltung = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupBuchhaltung(comboBuchhaltung.getValue()));
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).withCaption("Buchhaltung").withIcon(VaadinIcons.DOWNLOAD);

        //Template Buchhaltungen
        Button downloaderTemplateBuchhaltungen = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupTemplateBuchhaltungen());
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).withCaption("Alle Template Buchhaltungen").withIcon(VaadinIcons.DOWNLOAD);

        //Artikels
        Button downloaderArtikel = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupArtikelkategories());
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).withCaption("Alle Artikel").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<TemplateBuchhaltung> comboTemplateBuchhaltung = new ComboBox<>();
        Button downloaderTemplateBuchhaltung = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupTemplateBuchhaltung(comboTemplateBuchhaltung.getValue()));
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).withCaption("Template Buchhaltung").withIcon(VaadinIcons.DOWNLOAD);
        ((DownloadButton) downloaderAdresse).setFileName("AdresseAnouman.json");
        ((DownloadButton) downloaderAdressen).setFileName("AdressenAnouman.json");
        ((DownloadButton) downloaderBuchhaltung).setFileName("BuchhaltungAnouman.json");
        ((DownloadButton) downloaderBuchhaltungen).setFileName("BuchhaltungenAnouman.json");
        ((DownloadButton) downloaderTemplateBuchhaltung).setFileName("TemplateBuchhaltungAnouman.json");
        ((DownloadButton) downloaderTemplateBuchhaltungen).setFileName("TemplateBuchhaltungenAnouman.json");
        ((DownloadButton) downloaderArtikel).setFileName("ArtikelAnouman.json");


        List<TemplateBuchhaltung> templateBuchhaltungen = templateBuchhaltungDeltaspikeFacade.findAll();
        if (templateBuchhaltungen.size() == 0) {
            comboTemplateBuchhaltung.setEnabled(false);
            downloaderTemplateBuchhaltungen.setEnabled(false);
            downloaderTemplateBuchhaltung.setEnabled(false);
        } else {
            comboTemplateBuchhaltung.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
            comboTemplateBuchhaltung.setItemCaptionGenerator(templateBuchhaltung -> templateBuchhaltung.getBezeichnung());
            comboTemplateBuchhaltung.setEmptySelectionAllowed(false);
            comboTemplateBuchhaltung.setSelectedItem(templateBuchhaltungDeltaspikeFacade.findAll().get(0));
        }
        comboTemplateBuchhaltung.setWidth(20, Unit.EM);

        List<Buchhaltung> buchhaltungen = buchhaltungDeltaspikeFacade.findAll();
        if (buchhaltungen.size() == 0) {
            comboBuchhaltung.setEnabled(false);
            downloaderBuchhaltungen.setEnabled(false);
            downloaderBuchhaltung.setEnabled(false);
        } else {
            comboBuchhaltung.setItems(buchhaltungDeltaspikeFacade.findAll());
            comboBuchhaltung.setItemCaptionGenerator(buchhaltung -> buchhaltung.getBezeichnung() + " " + buchhaltung.getJahr());
            comboBuchhaltung.setEmptySelectionAllowed(false);
            comboBuchhaltung.setSelectedItem(buchhaltungDeltaspikeFacade.findAll().get(0));
        }
        comboBuchhaltung.setWidth(20, Unit.EM);

        List<Adresse> adressen = adresseDeltaspikeFacade.findAll();
        if (adressen.size() == 0) {
            comboAdresse.setEnabled(false);
            downloaderAdressen.setEnabled(false);
            downloaderAdresse.setEnabled(false);
        } else {
            comboAdresse.setItems(adresseDeltaspikeFacade.findAll());
            comboAdresse.setItemCaptionGenerator(adresse -> adresse.getFirma() + " " + adresse.getNachname() + " " + adresse.getOrt());
            comboAdresse.setEmptySelectionAllowed(false);
            comboAdresse.setSelectedItem(adresseDeltaspikeFacade.findAll().get(0));
        }
        comboAdresse.setWidth(20, Unit.EM);

        ((DownloadButton) downloaderAdresse).setFileName("AdresseAnouman.json");
        ((DownloadButton) downloaderAdressen).setFileName("AdressenAnouman.json");
        ((DownloadButton) downloaderBuchhaltung).setFileName("BuchhaltungAnouman.json");
        ((DownloadButton) downloaderBuchhaltungen).setFileName("BuchhaltungenAnouman.json");
        ((DownloadButton) downloaderTemplateBuchhaltung).setFileName("TemplateBuchhaltungAnouman.json");
        ((DownloadButton) downloaderTemplateBuchhaltungen).setFileName("TemplateBuchhaltungenAnouman.json");

        //Setze Dateiname für Downloadbuttons

        Panel panelBackup = new Panel("Backup");
        Panel panelBackupAdressen = new Panel("Adressen, Rechnungen");
        Panel panelBackupBuchhaltungen = new Panel("Buchhaltungen");
        Panel panelBackupTemplateBuchhaltungen = new Panel("Template Buchhaltungen");
        Panel panelBackupArtikel = new Panel("Artikel");
        panelBackupAdressen.setContent(new HorizontalLayout(
                downloaderAdressen,
                downloaderAdresse, comboAdresse));

        panelBackupBuchhaltungen.setContent(new HorizontalLayout(
                downloaderBuchhaltungen, downloaderBuchhaltung, comboBuchhaltung));

        panelBackupTemplateBuchhaltungen.setContent(new HorizontalLayout(
                downloaderTemplateBuchhaltungen, downloaderTemplateBuchhaltung, comboTemplateBuchhaltung));

        panelBackupArtikel.setContent(new HorizontalLayout(downloaderArtikel));

        panelBackup.setContent(
                new VerticalLayout(panelBackupAdressen, panelBackupBuchhaltungen, panelBackupTemplateBuchhaltungen, panelBackupArtikel));

        Panel panelRestore = new Panel("Restore");

        UploadComponent uploadAdressen = new UploadComponent();
        uploadAdressen.setCaption("Adressen");
        uploadAdressen.setReceivedCallback(this::uploadReceivedAdressen);

        UploadComponent uploadBuchhaltungen = new UploadComponent();
        uploadBuchhaltungen.setCaption("Buchhaltungen");
        uploadBuchhaltungen.setReceivedCallback(this::uploadReceivedBuchhaltungen);

        UploadComponent uploadTemplateBuchhaltungen = new UploadComponent();
        uploadTemplateBuchhaltungen.setCaption("Template Buchhaltungen");
        uploadTemplateBuchhaltungen.setReceivedCallback(this::uploadReceivedTemplateBuchhaltungen);

        UploadComponent uploadArtikel = new UploadComponent();
        uploadArtikel.setCaption("Artikel");
        uploadArtikel.setReceivedCallback(this::uploadReceivedArtikel);

        panelRestore.setContent(new VerticalLayout(uploadAdressen, uploadBuchhaltungen, uploadTemplateBuchhaltungen, uploadArtikel));

        setMargin(false);
        setSpacing(false);
        addComponents(panelBackup, panelRestore);
        setSizeFull();

    }

    private void uploadReceivedAdressen(String s, Path path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
            ObjectMapper mapper = new ObjectMapper();
            BackupAdressen backupAdressen = mapper.readValue(new FileInputStream(path.toFile()), BackupAdressen.class);
            backupAdressen.getAdressen().stream().forEach(backupAdresse -> {
                Adresse adresse = new Adresse();
                adresse.setAnrede(backupAdresse.getAnrede());
                adresse.setFirma(backupAdresse.getFirma());
                adresse.setNachname(backupAdresse.getNachname());
                adresse.setOrt(backupAdresse.getOrt());
                adresse.setPostleitzahl(backupAdresse.getPostleitzahl());
                adresse.setStrasse(backupAdresse.getStrasse());
                adresse.setVorname(backupAdresse.getVorname());
                adresse.setStundensatz(backupAdresse.getStundensatz());

                backupAdresse.getRechnungen().stream().forEach(backupRechnung -> {
                    Rechnung rechnung = new Rechnung();
                    rechnung.setBezeichnung(backupRechnung.getBezeichnung());
                    rechnung.setFaelligInTagen(backupRechnung.getFaelligInTagen());
                    rechnung.setRechnungsdatum(backupRechnung.getRechnungsdatum());
                    rechnung.setVerschickt(backupRechnung.isVerschickt());
                    rechnung.setBezahlt(backupRechnung.isBezahlt());
                    rechnung.setAdresse(adresse);
                    adresse.getRechnungen().add(rechnung);

                    backupRechnung.getRechnungspositions().stream().forEach(backupRechnungsposition -> {
                        Rechnungsposition rechnungsposition = new Rechnungsposition();
                        rechnungsposition.setAnzahl(backupRechnungsposition.getAnzahl());
                        rechnungsposition.setStueckpreis(backupRechnungsposition.getStueckpreis());
                        rechnungsposition.setBezeichnung(backupRechnungsposition.getBezeichnung());
                        rechnungsposition.setBezeichnunglang(backupRechnungsposition.getBezeichnunglang());
                        rechnungsposition.setMengeneinheit(backupRechnungsposition.getMengeneinheit());
                        rechnungsposition.setRechnung(rechnung);
                        rechnung.getRechnungspositionen().add(rechnungsposition);
                    });

                    backupRechnung.getAufwands().stream().forEach(backupAufwand -> {
                        Aufwand aufwand = new Aufwand();
                        aufwand.setBezeichnung(backupAufwand.getBezeichnung());
                        aufwand.setTitel(backupAufwand.getTitel());
                        aufwand.setStart(backupAufwand.getStart());
                        aufwand.setEnd(backupAufwand.getEnd());
                        aufwand.setRechnung(rechnung);
                        rechnung.getAufwands().add(aufwand);
                    });
                });
                adresseDeltaspikeFacade.save(adresse);
            });
            Notification.show("Anzahl Adressen wiederhergestellt: " + backupAdressen.getAdressen().size(), Notification.Type.TRAY_NOTIFICATION);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void uploadReceivedBuchhaltungen(String s, Path path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            BackupBuchhaltungen backupBuchhaltungen = mapper.readValue(new FileInputStream(path.toFile()), BackupBuchhaltungen.class);
            for (BackupBuchhaltung backupBuchhaltung : backupBuchhaltungen.getBuchhaltungen()) {
                Buchhaltung buchhaltung = new Buchhaltung();
                buchhaltung.setBezeichnung(backupBuchhaltung.getBezeichnung());
                buchhaltung.setJahr(backupBuchhaltung.getJahr());
                buchhaltung = buchhaltungDeltaspikeFacade.save(buchhaltung);
                for (BackupKontoklasse backupKontoklasse : backupBuchhaltung.getKontoklasses()) {
                    Kontoklasse kontoklasse = new Kontoklasse();
                    kontoklasse.setBezeichnung(backupKontoklasse.getBezeichnung());
                    kontoklasse.setKontonummer(backupKontoklasse.getKontonummer());
                    kontoklasse.setBuchhaltung(buchhaltung);
                    buchhaltung.getKontoklasse().add(kontoklasse);
                    kontoklasse = kontoklasseDeltaspikeFacade.save(kontoklasse);
                    buchhaltung = buchhaltungDeltaspikeFacade.save(buchhaltung);

                    for (BackupKontohauptgruppe backupKontohauptgruppe : backupKontoklasse.getBackupKontohauptgruppes()) {
                        Kontohauptgruppe kontohauptgruppe = new Kontohauptgruppe();
                        kontohauptgruppe.setBezeichnung(backupKontohauptgruppe.getBezeichnung());
                        kontohauptgruppe.setKontonummer(backupKontohauptgruppe.getKontonummer());
                        kontohauptgruppe.setKontoklasse(kontoklasse);
                        kontoklasse.getKontohauptgruppes().add(kontohauptgruppe);
                        kontohauptgruppe = kontohauptgruppeDeltaspikeFacade.save(kontohauptgruppe);
                        kontoklasse = kontoklasseDeltaspikeFacade.save(kontoklasse);

                        for (BackupKontogruppe backupKontogruppe : backupKontohauptgruppe.getBackupKontogruppes()) {
                            Kontogruppe kontogruppe = new Kontogruppe();
                            kontogruppe.setBezeichnung(backupKontogruppe.getBezeichnung());
                            kontogruppe.setKontonummer(backupKontogruppe.getKontonummer());
                            kontogruppe.setKontohauptgruppe(kontohauptgruppe);
                            kontohauptgruppe.getKontogruppes().add(kontogruppe);
                            kontogruppe = kontogruppeDeltaspikeFacade.save(kontogruppe);
                            kontohauptgruppe = kontohauptgruppeDeltaspikeFacade.save(kontohauptgruppe);

                            for (BackupKonto backupKonto : backupKontogruppe.getKontos()) {
                                Konto konto = new Konto();
                                konto.setBezeichnung(backupKonto.getBezeichnung());
                                konto.setBemerkung(backupKonto.getBemerkung());
                                konto.setAnfangsbestand(backupKonto.getAnfangsbestand());
                                konto.setKontonummer(backupKonto.getKontonummer());
                                konto.setKontogruppe(kontogruppe);
                                kontogruppe.getKontos().add(konto);
                                konto = kontoDeltaspikeFacade.save(konto);
                                kontogruppe = kontogruppeDeltaspikeFacade.save(kontogruppe);
                            }
                        }
                    }
                }
                Notification.show("Anzahl Buchhaltungen wiederhergestellt: " + backupBuchhaltungen.getBuchhaltungen().size(),
                        Notification.Type.TRAY_NOTIFICATION);
            }
        } catch (IOException ex) {
            Notification.show("Fehler: " + ex.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            System.err.println(ex.getLocalizedMessage());
        }
    }

    private void uploadReceivedTemplateBuchhaltungen(String s, Path path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            BackupTemplateBuchhaltungen backupTemplateBuchhaltungen = mapper.readValue(new FileInputStream(path.toFile()), BackupTemplateBuchhaltungen.class);
            for (BackupTemplateBuchhaltung backupTemplateBuchhaltung : backupTemplateBuchhaltungen.getBuchhaltungen()) {
                TemplateBuchhaltung templateBuchhaltung = new TemplateBuchhaltung();
                templateBuchhaltung.setBezeichnung(backupTemplateBuchhaltung.getBezeichnung());
                templateBuchhaltung = templateBuchhaltungDeltaspikeFacade.save(templateBuchhaltung);
                for (BackupTemplateKontoklasse backupTemplateKontoklasse : backupTemplateBuchhaltung.getKontoklasses()) {
                    TemplateKontoklasse templateKontoklasse = new TemplateKontoklasse();
                    templateKontoklasse.setBezeichnung(backupTemplateKontoklasse.getBezeichnung());
                    templateKontoklasse.setKontonummer(backupTemplateKontoklasse.getKontonummer());
                    templateKontoklasse.setTemplateBuchhaltung(templateBuchhaltung);
                    templateBuchhaltung.getTemplateKontoklasses().add(templateKontoklasse);
                    templateKontoklasse = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse);

                    for (BackupTemplateKontohauptgruppe backupTemplateKontohauptgruppe : backupTemplateKontoklasse.getBackupTemplateKontohauptgruppes()) {
                        TemplateKontohauptgruppe templateKontohauptgruppe = new TemplateKontohauptgruppe();
                        templateKontohauptgruppe.setBezeichnung(backupTemplateKontohauptgruppe.getBezeichnung());
                        templateKontohauptgruppe.setKontonummer(backupTemplateKontohauptgruppe.getKontonummer());
                        templateKontohauptgruppe.setTemplateKontoklasse(templateKontoklasse);
                        templateKontoklasse.getTemplateKontohauptgruppes().add(templateKontohauptgruppe);
                        templateKontohauptgruppe = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe);

                        for (BackupTemplateKontogruppe backupTemplateKontogruppe : backupTemplateKontohauptgruppe.getBackupTemplateKontogruppes()) {
                            TemplateKontogruppe templateKontogruppe = new TemplateKontogruppe();
                            templateKontogruppe.setBezeichnung(backupTemplateKontogruppe.getBezeichnung());
                            templateKontogruppe.setKontonummer(backupTemplateKontogruppe.getKontonummer());
                            templateKontogruppe.setTemplateKontohauptgruppe(templateKontohauptgruppe);
                            templateKontohauptgruppe.getTemplateKontogruppes().add(templateKontogruppe);
                            templateKontogruppe = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe);

                            for (BackupTemplateKonto backupTemplateKonto : backupTemplateKontogruppe.getBackupTemplateKontos()) {
                                TemplateKonto templateKonto = new TemplateKonto();
                                templateKonto.setBemerkung(backupTemplateKonto.getBemerkung());
                                templateKonto.setBezeichnung(backupTemplateKonto.getBezeichnung());
                                templateKonto.setKontonummer(backupTemplateKonto.getKontonummer());
                                templateKonto.setTemplateKontogruppe(templateKontogruppe);
                                templateKontogruppe.getTemplateKontos().add(templateKonto);
                                templateKonto = templateKontoDeltaspikeFacade.save(templateKonto);

                                for (BackupTemplateMehrwertsteuercode backupTemplateMehrwertsteuercode : backupTemplateKonto.getBackupTemplateMehrwertsteuercodes()) {
                                    TemplateMehrwertsteuercode templateMehrwertsteuercode = new TemplateMehrwertsteuercode();
                                    templateMehrwertsteuercode.setBezeichnung(backupTemplateMehrwertsteuercode.getBezeichnung());
                                    templateMehrwertsteuercode.setCode(backupTemplateMehrwertsteuercode.getCode());
                                    templateMehrwertsteuercode.setProzent(backupTemplateMehrwertsteuercode.getProzent());
                                    templateMehrwertsteuercode.setVerkauf(backupTemplateMehrwertsteuercode.isVerkauf());
                                    templateMehrwertsteuercode.setTemplateMehrwertsteuerKonto(templateKonto);
                                    templateMehrwertsteuercode.setTemplateBuchhaltung(templateBuchhaltung);
                                    templateMehrwertsteuercode = templateMehrwertsteuercodeDeltaspikeFacade.save(templateMehrwertsteuercode);
                                }
                            }
                        }
                    }
                }
                Notification.show("Anzahl erstellter Template Buchhaltungen: " + backupTemplateBuchhaltungen.getBuchhaltungen().size(),
                        Notification.Type.TRAY_NOTIFICATION);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private void uploadReceivedArtikel(String s, Path path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            BackupArtikelkategories backupArtikelkategories = mapper.readValue(new FileInputStream(path.toFile()), BackupArtikelkategories.class);
            for (BackupArtikelkategorie backupArtikelkategorie : backupArtikelkategories.getBackupArtikelkategories()) {
                Artikelkategorie artikelkategorie = new Artikelkategorie();
                artikelkategorie.setBezeichnung(backupArtikelkategorie.getBezeichnung());
                artikelkategorie = artikelkategorieDeltaspikeFacade.save(artikelkategorie);
                for (BackupArtikel backupArtikel : backupArtikelkategorie.getBackupArtikels()) {
                    Artikel artikel = new Artikel();
                    artikel.setAnzahl(backupArtikel.getAnzahl());
                    artikel.setBezeichnung(backupArtikel.getBezeichnung());
                    artikel.setBezeichnungLang(backupArtikel.getBezeichnungLang());
                    artikel.setMengeneinheit(backupArtikel.getMengeneinheit());
                    artikel.setStueckpreis(backupArtikel.getStueckpreis());
                    artikel.setArtikelkategorie(artikelkategorie);
                    artikel = artikelDeltaspikeFacade.save(artikel);

                    for (BackupArtikelbild backupArtikelbild : backupArtikel.getBackupArtikelbilds()) {
                        Artikelbild artikelbild = new Artikelbild();
                        artikelbild.setTitel(backupArtikelbild.getTitel());
                        artikelbild.setMimetype(backupArtikelbild.getMimetype());
                        artikelbild.setImage(backupArtikelbild.getBild());
                        artikelbild.setArtikel(artikel);
                        artikelbild = artikelbildDeltaspikeFacade.save(artikelbild);

                    }
                }
                Notification.show("Anzahl erstellter Artikelkategorien: " + backupArtikelkategories.getBackupArtikelkategories().size(),
                        Notification.Type.TRAY_NOTIFICATION);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        createContent();
    }
}

