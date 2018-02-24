package com.gmail.michzuerch.anouman.presentation.ui.backup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.gmail.michzuerch.anouman.backend.entity.*;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.BuchhaltungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.adressen.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.uploadreceiver.*;
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

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.IOException;
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
    TemplateBuchhaltungenXmlUploadReceiver templateXmlBuchhaltungenUploadReceiver;

    @Inject
    AdressenXmlUploadReceiver adressenXmlUploadReceiver;

    @Inject
    BuchhaltungenXmlUploadReceiver buchhaltungenXmlUploadReceiver;

    @Inject
    TemplateBuchhaltungenJSONUploadReceiver templateBuchhaltungenJSONUploadReceiver;

    @Inject
    AdressenJSONUploadReceiver adressenJSONUploadReceiver;

    @Inject
    BuchhaltungenJSONUploadReceiver buchhaltungenJSONUploadReceiver;

    private BackupAdressen getBackupAdressen() {
        BackupAdressen backupAdressen = new BackupAdressen();
        Instant now = Instant.now();
        backupAdressen.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));
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
        backupAdressen.setBackupdatum(LocalDateTime.ofInstant(now, ZoneOffset.UTC));

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


    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Row);
        layout.setAlignItems(AlignItems.FlexStart);
        layout.setJustifyContent(JustifyContent.Center);
        layout.setAlignContent(AlignContent.Center);
        layout.setFlexWrap(FlexWrap.Nowrap);

        RadioButtonGroup<String> fileFormatGroup = new RadioButtonGroup<>("Fileformat");
        fileFormatGroup.setItems("Json", "Xml");
        fileFormatGroup.setValue("Json");
        fileFormatGroup.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        //Adressen, Rechnungen
        Button downloaderAdressenJson = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupAdressen());
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).setFileName("AdressenRechnungenAnouman.json")
                .withCaption("Alle Adressen, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        Button downloaderAdressenXml = new DownloadButton(stream -> {
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(BackupAdressen.class, BackupAdresse.class, Rechnung.class,
                        Rechnungsposition.class, Aufwand.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);
                jaxbMarshaller.marshal(getBackupAdressen(), stream);
                stream.flush();
                stream.close();
            } catch (PropertyException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
            } catch (JAXBException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
            } catch (IOException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
            }
        }).setFileName("AdressenRechnungenAnouman.xml")
                .withCaption("Alle Adressen, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<Adresse> comboAdresseJson = new ComboBox<>();
        Button downloaderAdresseJson = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupAdresse(comboAdresseJson.getValue()));
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).setFileName("AdresseRechnungenAnouman.json")
                .withCaption("Eine Adresse, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<Adresse> comboAdresseXml = new ComboBox<>();
        Button downloaderAdresseXml = new DownloadButton(stream -> {
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(BackupAdressen.class, BackupAdresse.class, Rechnung.class,
                        Rechnungsposition.class, Aufwand.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);

                jaxbMarshaller.marshal(getBackupAdresse(comboAdresseXml.getValue()), stream);
                stream.flush();
                stream.close();
            } catch (PropertyException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
            } catch (JAXBException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
            } catch (IOException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
            }
        }).setFileName("AdresseRechnungenAnouman.xml")
                .withCaption("Eine Adresse, Rechnungen").withIcon(VaadinIcons.DOWNLOAD);

        //Buchhaltungen
        Button downloaderBuchhaltungenJson = new DownloadButton(stream -> {
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
        }).setFileName("BuchhaltungenAnouman.json")
                .withCaption("Alle Buchhaltung").withIcon(VaadinIcons.DOWNLOAD);

        Button downloaderBuchhaltungenXml = new DownloadButton(stream -> {
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(BackupBuchhaltungen.class, BackupBuchhaltung.class);
                logger.debug("Start");
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);

                jaxbMarshaller.marshal(getBackupBuchhaltungen(), stream);
                stream.flush();
                stream.close();
            } catch (PropertyException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            } catch (JAXBException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            }
        }).setFileName("BuchhaltungAnouman.xml")
                .withCaption("Alle Buchhaltungen").withIcon(VaadinIcons.DOWNLOAD);


        ComboBox<Buchhaltung> comboBuchhaltungJson = new ComboBox<>();
        Button downloaderBuchhaltungJson = new DownloadButton(stream -> {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(stream, getBackupBuchhaltung(comboBuchhaltungJson.getValue()));
                stream.flush();
                stream.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).setFileName("BuchhaltungAnouman.json")
                .withCaption("Datei mit Buchhaltung herunterladen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<Buchhaltung> comboBuchhaltungXml = new ComboBox<>();
        Button downloaderBuchhaltungXml = new DownloadButton(stream -> {
            JAXBContext jaxbContext = null;
            Buchhaltung buchhaltung = comboBuchhaltungXml.getValue();
            logger.debug("Gewählte Buchhaltung id:" + buchhaltung.getId());
            try {
                jaxbContext = JAXBContext.newInstance(BackupBuchhaltung.class);
                logger.debug("Start");
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);
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
                                backupKonto.setBezeichnung(konto.getBezeichnung());
                                backupKonto.setId(konto.getId());
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
                                //@todo Buchungen, Unterbuchungen
                            });
                        });
                    });
                });
                jaxbMarshaller.marshal(backupBuchhaltung, stream);
                stream.flush();
                stream.close();
            } catch (PropertyException ex) {
                logger.error(ex.getLocalizedMessage());
            } catch (JAXBException ex) {
                logger.error(ex.getLocalizedMessage());
            } catch (IOException ex) {
                logger.error(ex.getLocalizedMessage());
            }
        }).setFileName("BuchhaltungAnouman.xml")
                .withCaption("Datei mit Buchhaltung herunterladen").withIcon(VaadinIcons.DOWNLOAD);


        //Template Buchhaltungen
        Button downloaderTemplateBuchhaltungenJson = new DownloadButton(stream -> {
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
        }).setFileName("TemplateBuchhaltungenAnouman.json")
                .withCaption("Alle Template Buchhaltungen").withIcon(VaadinIcons.DOWNLOAD);

        Button downloaderTemplateBuchhaltungenXml = new DownloadButton(stream -> {
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);
                jaxbMarshaller.marshal(getBackupTemplateBuchhaltungen(), stream);
                stream.flush();
                stream.close();
            } catch (PropertyException ex) {
                //Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                logger.error("PropertyException" + ex.getLocalizedMessage());
                ex.printStackTrace();
            } catch (JAXBException ex) {
                //Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                logger.error("JAXBException" + ex.getLocalizedMessage());
                ex.printStackTrace();
            } catch (IOException ex) {
                //Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                logger.error("IOException" + ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }).setFileName("TemplateBuchhaltungenAnouman.xml")
                .withCaption("Alle Template Buchhaltungen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<TemplateBuchhaltung> comboTemplateBuchhaltungXml = new ComboBox<>();
        Button downloaderTemplateBuchhaltungXml = new DownloadButton(stream -> {
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);
                jaxbMarshaller.marshal(getBackupTemplateBuchhaltung(comboTemplateBuchhaltungXml.getValue()), stream);
                stream.flush();
                stream.close();
            } catch (PropertyException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            } catch (JAXBException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            }
        }).setFileName("TemplateBuchhaltungAnouman.xml")
                .withCaption("Datei mit Template Buchhaltung herunterladen").withIcon(VaadinIcons.DOWNLOAD);

        ComboBox<TemplateBuchhaltung> comboTemplateBuchhaltungJson = new ComboBox<>();
        Button downloaderTemplateBuchhaltungJson = new DownloadButton(stream -> {
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        true);
                jaxbMarshaller.marshal(getBackupTemplateBuchhaltung(comboTemplateBuchhaltungXml.getValue()), stream);
                stream.flush();
                stream.close();
            } catch (PropertyException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            } catch (JAXBException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                System.err.print(ex);
                ex.printStackTrace();
            }
        }).setFileName("TemplateBuchhaltungAnouman.json")
                .withCaption("Datei mit Template Buchhaltung herunterladen").withIcon(VaadinIcons.DOWNLOAD);


        fileFormatGroup.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue().equals("Xml")) {
                downloaderAdressenXml.setVisible(true);
                downloaderAdressenJson.setVisible(false);

                downloaderAdresseXml.setVisible(true);
                downloaderAdresseJson.setVisible(false);

                comboAdresseXml.setVisible(true);
                comboAdresseJson.setVisible(false);

                downloaderBuchhaltungenXml.setVisible(true);
                downloaderBuchhaltungenJson.setVisible(false);

                downloaderBuchhaltungXml.setVisible(true);
                downloaderBuchhaltungJson.setVisible(false);

                comboBuchhaltungXml.setVisible(true);
                comboBuchhaltungJson.setVisible(false);

                downloaderTemplateBuchhaltungenXml.setVisible(true);
                downloaderTemplateBuchhaltungenJson.setVisible(false);

                downloaderTemplateBuchhaltungXml.setVisible(true);
                downloaderTemplateBuchhaltungJson.setVisible(false);

                comboTemplateBuchhaltungXml.setVisible(true);
                comboTemplateBuchhaltungJson.setVisible(false);
            }
            if (valueChangeEvent.getValue().equals("Json")) {
                downloaderAdressenXml.setVisible(false);
                downloaderAdressenJson.setVisible(true);

                downloaderAdresseXml.setVisible(false);
                downloaderAdresseJson.setVisible(true);

                comboAdresseXml.setVisible(false);
                comboAdresseJson.setVisible(true);

                downloaderBuchhaltungenXml.setVisible(false);
                downloaderBuchhaltungenJson.setVisible(true);

                downloaderBuchhaltungXml.setVisible(false);
                downloaderBuchhaltungJson.setVisible(true);

                comboBuchhaltungXml.setVisible(false);
                comboBuchhaltungJson.setVisible(true);

                downloaderTemplateBuchhaltungenXml.setVisible(false);
                downloaderTemplateBuchhaltungenJson.setVisible(true);

                downloaderTemplateBuchhaltungXml.setVisible(false);
                downloaderTemplateBuchhaltungJson.setVisible(true);

                comboTemplateBuchhaltungXml.setVisible(false);
                comboTemplateBuchhaltungJson.setVisible(true);
            }
        });

        List<TemplateBuchhaltung> templateBuchhaltungen = templateBuchhaltungDeltaspikeFacade.findAll();
        if (templateBuchhaltungen.size() == 0) {
            comboTemplateBuchhaltungXml.setEnabled(false);
            downloaderTemplateBuchhaltungenXml.setEnabled(false);
            downloaderTemplateBuchhaltungXml.setEnabled(false);
            comboTemplateBuchhaltungJson.setEnabled(false);
            downloaderTemplateBuchhaltungenJson.setEnabled(false);
            downloaderTemplateBuchhaltungJson.setEnabled(false);
        } else {
            comboTemplateBuchhaltungXml.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
            comboTemplateBuchhaltungXml.setItemCaptionGenerator(templateBuchhaltung -> templateBuchhaltung.getBezeichnung());
            comboTemplateBuchhaltungXml.setEmptySelectionAllowed(false);
            comboTemplateBuchhaltungXml.setSelectedItem(templateBuchhaltungDeltaspikeFacade.findAll().get(0));
            comboTemplateBuchhaltungJson.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
            comboTemplateBuchhaltungJson.setItemCaptionGenerator(templateBuchhaltung -> templateBuchhaltung.getBezeichnung());
            comboTemplateBuchhaltungJson.setEmptySelectionAllowed(false);
            comboTemplateBuchhaltungJson.setSelectedItem(templateBuchhaltungDeltaspikeFacade.findAll().get(0));
        }
        comboTemplateBuchhaltungXml.setWidth(20, Unit.EM);
        comboTemplateBuchhaltungJson.setWidth(20, Unit.EM);

        List<Buchhaltung> buchhaltungen = buchhaltungDeltaspikeFacade.findAll();
        if (buchhaltungen.size() == 0) {
            comboBuchhaltungXml.setEnabled(false);
            downloaderBuchhaltungenXml.setEnabled(false);
            downloaderBuchhaltungXml.setEnabled(false);
            comboBuchhaltungJson.setEnabled(false);
            downloaderBuchhaltungenJson.setEnabled(false);
            downloaderBuchhaltungJson.setEnabled(false);
        } else {
            comboBuchhaltungXml.setItems(buchhaltungDeltaspikeFacade.findAll());
            comboBuchhaltungXml.setItemCaptionGenerator(buchhaltung -> buchhaltung.getBezeichnung() + " " + buchhaltung.getJahr());
            comboBuchhaltungXml.setEmptySelectionAllowed(false);
            comboBuchhaltungXml.setSelectedItem(buchhaltungDeltaspikeFacade.findAll().get(0));
            comboBuchhaltungJson.setItems(buchhaltungDeltaspikeFacade.findAll());
            comboBuchhaltungJson.setItemCaptionGenerator(buchhaltung -> buchhaltung.getBezeichnung() + " " + buchhaltung.getJahr());
            comboBuchhaltungJson.setEmptySelectionAllowed(false);
            comboBuchhaltungJson.setSelectedItem(buchhaltungDeltaspikeFacade.findAll().get(0));
        }
        comboBuchhaltungXml.setWidth(20, Unit.EM);
        comboBuchhaltungJson.setWidth(20, Unit.EM);

        List<Adresse> adressen = adresseDeltaspikeFacade.findAll();
        if (adressen.size() == 0) {
            comboAdresseXml.setEnabled(false);
            downloaderAdressenXml.setEnabled(false);
            downloaderAdresseXml.setEnabled(false);
            comboAdresseJson.setEnabled(false);
            downloaderAdressenJson.setEnabled(false);
            downloaderAdresseJson.setEnabled(false);
        } else {
            comboAdresseJson.setItems(adresseDeltaspikeFacade.findAll());
            comboAdresseJson.setItemCaptionGenerator(adresse -> adresse.getFirma() + " " + adresse.getNachname() + " " + adresse.getOrt());
            comboAdresseJson.setEmptySelectionAllowed(false);
            comboAdresseJson.setSelectedItem(adresseDeltaspikeFacade.findAll().get(0));
        }
        comboAdresseXml.setWidth(20, Unit.EM);
        comboAdresseJson.setWidth(20, Unit.EM);


        Panel panelBackup = new Panel("Backup");

        Panel panelBackupAdressen = new Panel("Adresse, Rechnung");
        Panel panelBackupBuchhaltungen = new Panel("Buchhaltungen");
        Panel panelBackupTemplateBuchhaltungen = new Panel("Template Buchhaltungen");

        panelBackupAdressen.setContent(new HorizontalLayout(
                downloaderAdressenJson, downloaderAdressenXml,
                downloaderAdresseJson, comboAdresseJson,
                downloaderAdresseXml, comboAdresseXml));


        panelBackupBuchhaltungen.setContent(new HorizontalLayout(
                downloaderBuchhaltungenJson, downloaderBuchhaltungenXml,
                downloaderBuchhaltungXml, comboBuchhaltungXml));

        panelBackupTemplateBuchhaltungen.setContent(new HorizontalLayout(
                downloaderTemplateBuchhaltungenXml,
                downloaderTemplateBuchhaltungXml, comboTemplateBuchhaltungXml));

        panelBackup.setContent(
                new VerticalLayout(fileFormatGroup, panelBackupAdressen, panelBackupBuchhaltungen, panelBackupTemplateBuchhaltungen));


        Panel panelRestore = new Panel("Restore");

        Panel panelRestoreXML = new Panel("XML");
        Panel panelRestoreJSON = new Panel("JSON");

        Upload uploadXmlAdressen = new Upload("Upload Adressen XML", adressenJSONUploadReceiver);
        Upload uploadXmlBuchhaltungen = new Upload("Upload Buchhaltungen XML", buchhaltungenJSONUploadReceiver);
        Upload uploadXmlTemplateBuchhaltungen = new Upload("Upload Template Buchhaltungen XML", templateBuchhaltungenJSONUploadReceiver);

        Upload uploadJSONAdressen = new Upload("Upload Adressen JSON", adressenJSONUploadReceiver);
        Upload uploadJSONBuchhaltungen = new Upload("Upload Buchhaltungen JSON", buchhaltungenJSONUploadReceiver);
        Upload uploadJSONTemplateBuchhaltungen = new Upload("Upload Template Buchhaltungen JSON", templateBuchhaltungenJSONUploadReceiver);

        uploadXmlAdressen.addSucceededListener(adressenJSONUploadReceiver);
        uploadXmlAdressen.setReceiver(adressenJSONUploadReceiver);

        uploadXmlBuchhaltungen.addSucceededListener(buchhaltungenJSONUploadReceiver);
        uploadXmlBuchhaltungen.setReceiver(buchhaltungenJSONUploadReceiver);

        uploadXmlTemplateBuchhaltungen.addSucceededListener(templateBuchhaltungenJSONUploadReceiver);
        uploadXmlTemplateBuchhaltungen.setReceiver(templateBuchhaltungenJSONUploadReceiver);

        uploadJSONAdressen.addSucceededListener(adressenJSONUploadReceiver);
        uploadJSONAdressen.setReceiver(adressenJSONUploadReceiver);

        uploadJSONBuchhaltungen.addSucceededListener(buchhaltungenJSONUploadReceiver);
        uploadJSONBuchhaltungen.setReceiver(buchhaltungenJSONUploadReceiver);

        uploadJSONTemplateBuchhaltungen.addSucceededListener(templateBuchhaltungenJSONUploadReceiver);
        uploadJSONTemplateBuchhaltungen.setReceiver(templateBuchhaltungenJSONUploadReceiver);

        panelRestoreXML.setContent(new VerticalLayout(uploadXmlAdressen, uploadXmlBuchhaltungen, uploadXmlTemplateBuchhaltungen));
        panelRestoreJSON.setContent(new VerticalLayout(uploadJSONAdressen, uploadJSONBuchhaltungen, uploadJSONTemplateBuchhaltungen));

        panelRestore.setContent(
                new VerticalLayout(panelRestoreJSON, panelRestoreXML));

        if (fileFormatGroup.getValue().equals("Xml")) {
            downloaderAdressenXml.setVisible(true);
            downloaderAdressenJson.setVisible(false);

            downloaderAdresseXml.setVisible(true);
            downloaderAdresseJson.setVisible(false);

            comboAdresseXml.setVisible(true);
            comboAdresseJson.setVisible(false);

            downloaderBuchhaltungenXml.setVisible(true);
            downloaderBuchhaltungenJson.setVisible(false);

            downloaderBuchhaltungXml.setVisible(true);
            downloaderBuchhaltungJson.setVisible(false);

            comboBuchhaltungXml.setVisible(true);
            comboBuchhaltungJson.setVisible(false);

            downloaderTemplateBuchhaltungenXml.setVisible(true);
            downloaderTemplateBuchhaltungenJson.setVisible(false);

            downloaderTemplateBuchhaltungXml.setVisible(true);
            downloaderTemplateBuchhaltungJson.setVisible(false);

            comboTemplateBuchhaltungXml.setVisible(true);
            comboTemplateBuchhaltungJson.setVisible(false);
        }
        if (fileFormatGroup.getValue().equals("Json")) {
            downloaderAdressenXml.setVisible(false);
            downloaderAdressenJson.setVisible(true);

            downloaderAdresseXml.setVisible(false);
            downloaderAdresseJson.setVisible(false);

            comboAdresseXml.setVisible(false);
            comboAdresseJson.setVisible(true);

            downloaderBuchhaltungenXml.setVisible(false);
            downloaderBuchhaltungenJson.setVisible(true);

            downloaderBuchhaltungXml.setVisible(false);
            downloaderBuchhaltungJson.setVisible(true);

            comboBuchhaltungXml.setVisible(false);
            comboBuchhaltungJson.setVisible(true);

            downloaderTemplateBuchhaltungenXml.setVisible(false);
            downloaderTemplateBuchhaltungenJson.setVisible(true);

            downloaderTemplateBuchhaltungXml.setVisible(false);
            downloaderTemplateBuchhaltungJson.setVisible(true);

            comboTemplateBuchhaltungXml.setVisible(false);
            comboTemplateBuchhaltungJson.setVisible(true);
        }

        layout.addComponents(panelBackup, panelRestore);
        layout.setSizeFull();
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(createContent());
        setSizeFull();
    }
}
