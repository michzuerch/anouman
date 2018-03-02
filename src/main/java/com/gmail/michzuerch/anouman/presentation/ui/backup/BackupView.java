package com.gmail.michzuerch.anouman.presentation.ui.backup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.gmail.michzuerch.anouman.backend.entity.Adresse;
import com.gmail.michzuerch.anouman.backend.entity.Buchhaltung;
import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.BuchhaltungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.adressen.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen.*;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.teemusa.flexlayout.*;
import org.vaadin.viritin.button.DownloadButton;
import server.droporchoose.UploadComponent;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private String filename = new String();

    @Inject
    AdresseDeltaspikeFacade adresseDeltaspikeFacade;

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    BuchhaltungDeltaspikeFacade buchhaltungDeltaspikeFacade;

    @Inject
    TemplateBuchhaltungDeltaspikeFacade templateBuchhaltungDeltaspikeFacade;

    private RadioButtonGroup<String> fileFormatDownloadGroup;
    private RadioButtonGroup<String> fileFormatUploadGroup;

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
                            konto.getMehrwertsteuercode().stream().forEach(mehrwertsteuercode -> {
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
                        konto.getMehrwertsteuercode().stream().forEach(mehrwertsteuercode -> {
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
        templateBuchhaltungDeltaspikeFacade.findAll().stream().forEach(buchhaltung -> {
            BackupTemplateBuchhaltung backupBuchhaltung = new BackupTemplateBuchhaltung(buchhaltung.getBezeichnung());
            backupBuchhaltung.getMehrwertsteuercodes().stream().forEach(mehrwertsteuercode -> {
                BackupTemplateMehrwertsteuercode backupMehrwertsteuercode = new BackupTemplateMehrwertsteuercode();
                backupMehrwertsteuercode.setId(mehrwertsteuercode.getId());
                backupMehrwertsteuercode.setBezeichnung(mehrwertsteuercode.getBezeichnung());
                backupMehrwertsteuercode.setCode(mehrwertsteuercode.getCode());
                backupMehrwertsteuercode.setProzent(mehrwertsteuercode.getProzent());
                backupMehrwertsteuercode.setVerkauf(mehrwertsteuercode.isVerkauf());
                backupMehrwertsteuercode.setKonto(mehrwertsteuercode.getKonto());
                backupBuchhaltung.getMehrwertsteuercodes().add(backupMehrwertsteuercode);
            });
            buchhaltung.getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
                BackupTemplateKontoklasse backupKontoklasse = new BackupTemplateKontoklasse(templateKontoklasse.getBezeichnung(), templateKontoklasse.getKontonummer());
                backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
                templateKontoklasse.getTemplateKontohauptgruppes().stream().forEach(templateKontohauptgruppe -> {
                    BackupTemplateKontohauptgruppe backupTemplateKontohauptgruppe = new BackupTemplateKontohauptgruppe(templateKontohauptgruppe.getBezeichnung(), templateKontohauptgruppe.getKontonummer());
                    backupKontoklasse.getKontohauptgruppen().add(backupTemplateKontohauptgruppe);
                    templateKontohauptgruppe.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                        BackupTemplateKontogruppe backupTemplateKontogruppe = new BackupTemplateKontogruppe(templateKontogruppe.getBezeichnung(), templateKontogruppe.getKontonummer());
                        backupTemplateKontohauptgruppe.getBackupTemplateKontogruppes().add(backupTemplateKontogruppe);
                        templateKontogruppe.getTemplateKontos().stream().forEach(templateKonto -> {
                            BackupTemplateKonto backupTemplateKonto = new BackupTemplateKonto();
                            backupTemplateKonto.setBezeichnung(templateKonto.getBezeichnung());
                            backupTemplateKonto.setKontonummer(templateKonto.getKontonummer());
                            backupTemplateKontogruppe.getBackupTemplateKontos().add(backupTemplateKonto);

                            templateKonto.getTemplateMehrwertsteuercode().stream().forEach(templateMehrwertsteuercode -> {
                                BackupTemplateMehrwertsteuercode backupTemplateMehrwertsteuercode = new BackupTemplateMehrwertsteuercode();
                                backupTemplateMehrwertsteuercode.setId(templateMehrwertsteuercode.getId());
                                backupTemplateMehrwertsteuercode.setBezeichnung(templateMehrwertsteuercode.getBezeichnung());
                                backupTemplateMehrwertsteuercode.setProzent(templateMehrwertsteuercode.getProzent());
                                backupTemplateMehrwertsteuercode.setCode(templateMehrwertsteuercode.getCode());
                                backupTemplateKonto.getBackupTemplateMehrwertsteuercodes().add(backupTemplateMehrwertsteuercode);
                            });
                        });
                    });
                });
            });
            backupTemplateBuchhaltungen.addBuchhaltung(backupBuchhaltung);
        });
        return backupTemplateBuchhaltungen;
    }

    private BackupTemplateBuchhaltungen getBackupTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        BackupTemplateBuchhaltungen backupTemplateBuchhaltungen = new BackupTemplateBuchhaltungen();
        Instant now = Instant.now();
        backupTemplateBuchhaltungen.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));

        BackupTemplateBuchhaltung backupBuchhaltung = new BackupTemplateBuchhaltung(templateBuchhaltung.getBezeichnung());

        templateBuchhaltung.getTemplateMehrwertsteuercodes().stream().forEach(templateMehrwertsteuercode -> {
            BackupTemplateMehrwertsteuercode backupMehrwertsteuercode = new BackupTemplateMehrwertsteuercode();
            backupMehrwertsteuercode.setId(templateMehrwertsteuercode.getId());
            backupMehrwertsteuercode.setBezeichnung(templateMehrwertsteuercode.getBezeichnung());
            backupMehrwertsteuercode.setCode(templateMehrwertsteuercode.getCode());
            backupMehrwertsteuercode.setProzent(templateMehrwertsteuercode.getProzent());
            backupMehrwertsteuercode.setVerkauf(templateMehrwertsteuercode.isVerkauf());
            //@todo Konto muss stimmen....!
            backupMehrwertsteuercode.setKonto(1L);
            backupBuchhaltung.getMehrwertsteuercodes().add(backupMehrwertsteuercode);
        });
        templateBuchhaltung.getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
            BackupTemplateKontoklasse backupKontoklasse = new BackupTemplateKontoklasse(templateKontoklasse.getBezeichnung(), templateKontoklasse.getKontonummer());
            backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
            templateKontoklasse.getTemplateKontohauptgruppes().stream().forEach(templateKontohauptgruppe -> {
                BackupTemplateKontohauptgruppe backupKontogruppe = new BackupTemplateKontohauptgruppe(templateKontohauptgruppe.getBezeichnung(), templateKontohauptgruppe.getKontonummer());
                backupKontoklasse.getKontohauptgruppen().add(backupKontogruppe);
                templateKontohauptgruppe.getTemplateKontogruppes().stream().forEach(templateKontogruppe -> {
                    BackupTemplateKontogruppe backupTemplateKontogruppe = new BackupTemplateKontogruppe(templateKontogruppe.getBezeichnung(), templateKontogruppe.getKontonummer());
                    backupKontogruppe.getBackupTemplateKontogruppes().add(backupTemplateKontogruppe);
                    templateKontogruppe.getTemplateKontos().stream().forEach(templateKonto1 -> {
                        BackupTemplateKonto backupTemplateKonto = new BackupTemplateKonto();
                        backupTemplateKonto.setBezeichnung(templateKonto1.getBezeichnung());
                        backupTemplateKonto.setKontonummer(templateKonto1.getKontonummer());
                        backupTemplateKontogruppe.getBackupTemplateKontos().add(backupTemplateKonto);

                        backupTemplateKonto.getBackupTemplateMehrwertsteuercodes().stream().forEach(templateMehrwertsteuercode -> {
                            BackupTemplateMehrwertsteuercode backupTemplateMehrwertsteuercode = new BackupTemplateMehrwertsteuercode();
                            backupTemplateMehrwertsteuercode.setId(templateMehrwertsteuercode.getId());
                            backupTemplateMehrwertsteuercode.setCode(templateMehrwertsteuercode.getCode());
                            backupTemplateMehrwertsteuercode.setBezeichnung(templateMehrwertsteuercode.getBezeichnung());
                            backupTemplateMehrwertsteuercode.setProzent(templateMehrwertsteuercode.getProzent());
                            backupTemplateMehrwertsteuercode.setVerkauf(templateMehrwertsteuercode.isVerkauf());
                            backupTemplateKonto.getBackupTemplateMehrwertsteuercodes().add(backupTemplateMehrwertsteuercode);
                        });
                    });
                });
            });
            backupTemplateBuchhaltungen.addBuchhaltung(backupBuchhaltung);
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

    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Row);
        layout.setAlignItems(AlignItems.FlexStart);
        layout.setJustifyContent(JustifyContent.Center);
        layout.setAlignContent(AlignContent.Center);
        layout.setFlexWrap(FlexWrap.Nowrap);

        fileFormatDownloadGroup = new RadioButtonGroup<>("Fileformat Download");
        fileFormatDownloadGroup.setItems("Json", "Xml");
        fileFormatDownloadGroup.setValue("Json");
        fileFormatDownloadGroup.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        fileFormatUploadGroup = new RadioButtonGroup<>("Fileformat Upload");
        fileFormatUploadGroup.setItems("Json", "Xml");
        fileFormatUploadGroup.setValue("Json");
        fileFormatUploadGroup.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        //Adressen, Rechnungen
        Button downloaderAdressen = new DownloadButton(stream -> {
            if (fileFormatDownloadGroup.getValue().equals("Json")) {
                ObjectMapper mapper = new ObjectMapper()
                        .registerModule(new ParameterNamesModule())
                        .registerModule(new Jdk8Module())
                        .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
                try {
                    mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupAdressen());
                    stream.flush();

                    mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(new File("test.json")), getBackupAdressen());
//Test
                    BackupAdressen backupAdressen = mapper.readValue(new FileInputStream(new File("test.json")), BackupAdressen.class);
                    logger.debug("backupAdressen:" + backupAdressen.getBackupdatum().toString());
                    System.err.println("backupAdressen:" + backupAdressen);


                    stream.close();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (fileFormatDownloadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                try {
                    mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupAdressen());
                    stream.flush();
                    stream.close();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).withCaption("Alle Adressen, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<Adresse> comboAdresse = new ComboBox<>();
        Button downloaderAdresse = new DownloadButton(stream -> {
            if (fileFormatDownloadGroup.getValue().equals("Json")) {
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
            }
            if (fileFormatDownloadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                try {
                    mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupAdresse(comboAdresse.getValue()));
                    stream.flush();
                    stream.close();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).withCaption("Adresse, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        //Buchhaltungen
        Button downloaderBuchhaltungen = new DownloadButton(stream -> {
            if (fileFormatDownloadGroup.getValue().equals("Json")) {
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
            }
            if (fileFormatDownloadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                try {
                    mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupBuchhaltungen());
                    stream.flush();
                    stream.close();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).withCaption("Alle Buchhaltungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<Buchhaltung> comboBuchhaltung = new ComboBox<>();
        Button downloaderBuchhaltung = new DownloadButton(stream -> {
            if (fileFormatDownloadGroup.getValue().equals("Json")) {
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
            }
            if (fileFormatDownloadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                try {
                    mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupBuchhaltung(comboBuchhaltung.getValue()));
                    stream.flush();
                    stream.close();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).withCaption("Buchhaltung").withIcon(VaadinIcons.DOWNLOAD);

        //Template Buchhaltungen
        Button downloaderTemplateBuchhaltungen = new DownloadButton(stream -> {
            if (fileFormatDownloadGroup.getValue().equals("Json")) {
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
            }
            if (fileFormatDownloadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                try {
                    mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupTemplateBuchhaltungen());
                    stream.flush();
                    stream.close();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).withCaption("Alle Template Buchhaltungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<TemplateBuchhaltung> comboTemplateBuchhaltung = new ComboBox<>();
        Button downloaderTemplateBuchhaltung = new DownloadButton(stream -> {
            if (fileFormatDownloadGroup.getValue().equals("Json")) {
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
            }
            if (fileFormatDownloadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                try {
                    mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupTemplateBuchhaltung(comboTemplateBuchhaltung.getValue()));
                    stream.flush();
                    stream.close();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).withCaption("Template Buchhaltung").withIcon(VaadinIcons.DOWNLOAD);


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

        fileFormatDownloadGroup.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue().equals("Json")) {
                System.err.println("Download Format Json");
                ((DownloadButton) downloaderAdresse).setFileName("AdresseAnouman.json");
                ((DownloadButton) downloaderAdressen).setFileName("AdressenAnouman.json");
                ((DownloadButton) downloaderBuchhaltung).setFileName("BuchhaltungAnouman.json");
                ((DownloadButton) downloaderBuchhaltungen).setFileName("BuchhaltungenAnouman.json");
                ((DownloadButton) downloaderTemplateBuchhaltung).setFileName("TemplateBuchhaltungAnouman.json");
                ((DownloadButton) downloaderTemplateBuchhaltungen).setFileName("TemplateBuchhaltungenAnouman.json");
            }
            if (valueChangeEvent.getValue().equals("Xml")) {
                System.err.println("Download Format Xml");
                ((DownloadButton) downloaderAdresse).setFileName("AdresseAnouman.xml");
                ((DownloadButton) downloaderAdressen).setFileName("AdressenAnouman.xml");
                ((DownloadButton) downloaderBuchhaltung).setFileName("BuchhaltungAnouman.xml");
                ((DownloadButton) downloaderBuchhaltungen).setFileName("BuchhaltungenAnouman.xml");
                ((DownloadButton) downloaderTemplateBuchhaltung).setFileName("TemplateBuchhaltungAnouman.xml");
                ((DownloadButton) downloaderTemplateBuchhaltungen).setFileName("TemplateBuchhaltungenAnouman.xml");
            }

        });

        //Setze Dateiname für Downloadbuttons

        Panel panelBackup = new Panel("Backup");
        Panel panelBackupAdressen = new Panel("Adressen, Rechnungen");
        Panel panelBackupBuchhaltungen = new Panel("Buchhaltungen");
        Panel panelBackupTemplateBuchhaltungen = new Panel("Template Buchhaltungen");
        panelBackupAdressen.setContent(new HorizontalLayout(
                downloaderAdressen,
                downloaderAdresse, comboAdresse));

        panelBackupBuchhaltungen.setContent(new HorizontalLayout(
                downloaderBuchhaltungen, downloaderBuchhaltung, comboBuchhaltung));

        panelBackupTemplateBuchhaltungen.setContent(new HorizontalLayout(
                downloaderTemplateBuchhaltungen, downloaderTemplateBuchhaltung, comboTemplateBuchhaltung));

        panelBackup.setContent(
                new VerticalLayout(fileFormatDownloadGroup, panelBackupAdressen, panelBackupBuchhaltungen, panelBackupTemplateBuchhaltungen));

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

        panelRestore.setContent(new VerticalLayout(fileFormatUploadGroup, uploadAdressen, uploadBuchhaltungen, uploadTemplateBuchhaltungen));
        layout.addComponents(panelBackup, panelRestore);
        layout.setSizeFull();
        return layout;
    }

    private void uploadReceivedAdressen(String s, Path path) {
        try {
            if (fileFormatUploadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                BackupAdressen backupAdressen = mapper.readValue(new FileInputStream(path.toFile()), BackupAdressen.class);
                logger.debug("XML:" + backupAdressen);

            }
            if (fileFormatUploadGroup.getValue().equals("Json")) {
                ObjectMapper mapper = new ObjectMapper();
                BackupAdressen backupAdressen = mapper.readValue(new FileInputStream(path.toFile()), BackupAdressen.class);
                System.err.println("Upload Adressen:" + backupAdressen);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void uploadReceivedBuchhaltungen(String s, Path path) {
        try {
            if (fileFormatUploadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                BackupBuchhaltungen backupBuchhaltungen = mapper.readValue(path.getFileName().toFile(), BackupBuchhaltungen.class);
                logger.debug("XML:" + backupBuchhaltungen);

            }
            if (fileFormatUploadGroup.getValue().equals("Json")) {
                ObjectMapper mapper = new ObjectMapper();
                BackupBuchhaltungen backupBuchhaltungen = mapper.readValue(path.getFileName().toFile(), BackupBuchhaltungen.class);
                logger.debug("Json:" + backupBuchhaltungen);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void uploadReceivedTemplateBuchhaltungen(String s, Path path) {
        try {
            if (fileFormatUploadGroup.getValue().equals("Xml")) {
                XmlMapper mapper = new XmlMapper();
                BackupTemplateBuchhaltungen backupTemplateBuchhaltungen = mapper.readValue(path.getFileName().toFile(), BackupTemplateBuchhaltungen.class);
                logger.debug("XML:" + backupTemplateBuchhaltungen);

            }
            if (fileFormatUploadGroup.getValue().equals("Json")) {
                ObjectMapper mapper = new ObjectMapper();
                BackupTemplateBuchhaltungen backupTemplateBuchhaltungen = mapper.readValue(path.getFileName().toFile(), BackupTemplateBuchhaltungen.class);
                logger.debug("Json:" + backupTemplateBuchhaltungen);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(createContent());
        setSizeFull();
    }
}
