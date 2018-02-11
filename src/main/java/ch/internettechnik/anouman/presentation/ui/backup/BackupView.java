package ch.internettechnik.anouman.presentation.ui.backup;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.BuchhaltungDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver.*;
import ch.internettechnik.anouman.presentation.ui.backup.xml.adressen.*;
import ch.internettechnik.anouman.presentation.ui.backup.xml.buchhaltungen.*;
import ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by michzuerch on 25.07.15.
 */
@CDIView("BackupView")
public class BackupView extends VerticalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    @Inject
    Menu menu;

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

    Button downloadAdressenJson = new DownloadButton(stream -> {
        ObjectMapper mapper = new ObjectMapper();
        BackupAdressen adressen = new BackupAdressen();
        adressen.setBackupdatum(new Date());
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
            adressen.getAdressen().add(backupAdresse);
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
        try {
            mapper.writeValue(stream,adressen);
            stream.flush();
            stream.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }).setFileName("AdressenRechnungenAnouman.json")
            .withCaption("JSON-Datei mit Adressen, Rechnungen, Rechnungspositionen, Aufwand herunterladen").withIcon(VaadinIcons.DOWNLOAD);

    Button downloaderAdressenXml = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupAdressen.class, BackupAdresse.class, Rechnung.class,
                    Rechnungsposition.class, Aufwand.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupAdressen backupAdressen = new BackupAdressen();
            backupAdressen.setBackupdatum(new Date());

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
            });

            jaxbMarshaller.marshal(backupAdressen, stream);
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
            .withCaption("XML-Datei mit Adressen, Rechnungen, Rechnungspositionen, Aufwand herunterladen").withIcon(VaadinIcons.DOWNLOAD);

    Button downloaderXmlBuchhaltungen = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupBuchhaltungen.class, BackupBuchhaltung.class);
            logger.debug("Start");
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupBuchhaltungen backupBuchhaltungen = new BackupBuchhaltungen();
            backupBuchhaltungen.setDatum(new Date());

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
            jaxbMarshaller.marshal(backupBuchhaltungen, stream);
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
            .withCaption("XML-Datei mit allen Buchhaltungen herunterladen").withIcon(VaadinIcons.DOWNLOAD);

    Button downloaderJSONBuchhaltungen = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupBuchhaltungen.class, BackupBuchhaltung.class);
            logger.debug("Start");
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupBuchhaltungen backupBuchhaltungen = new BackupBuchhaltungen();
            backupBuchhaltungen.setDatum(new Date());

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
            jaxbMarshaller.marshal(backupBuchhaltungen, stream);
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
            .withCaption("JSON-Datei mit allen Buchhaltungen herunterladen").withIcon(VaadinIcons.DOWNLOAD);


    Button downloaderTemplateBuchhaltungen = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupTemplateBuchhaltungen backupBuchhaltungen = new BackupTemplateBuchhaltungen();
            backupBuchhaltungen.setDatum(new Date());
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
                backupBuchhaltungen.addBuchhaltung(backupBuchhaltung);
            });
            jaxbMarshaller.marshal(backupBuchhaltungen, stream);
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
            .withCaption("Datei mit allen Template Buchhaltungen herunterladen").withIcon(VaadinIcons.DOWNLOAD);


    private ComboBox<TemplateBuchhaltung> listTemplateBuchhaltungen = new ComboBox<>();
    Button downloaderTemplateBuchhaltung = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupTemplateBuchhaltungen backupBuchhaltungen = new BackupTemplateBuchhaltungen();
            backupBuchhaltungen.setDatum(new Date());

            BackupTemplateBuchhaltung backupBuchhaltung = new BackupTemplateBuchhaltung(listTemplateBuchhaltungen.getValue().getBezeichnung());

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
            listTemplateBuchhaltungen.getValue().getTemplateKontoklasses().stream().forEach(templateKontoklasse -> {
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
                backupBuchhaltungen.addBuchhaltung(backupBuchhaltung);
            });
            jaxbMarshaller.marshal(backupBuchhaltungen, stream);
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

    private ComboBox<Adresse> listAdressen = new ComboBox<>();
    Button downloaderAdresse = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupAdressen.class, BackupAdresse.class, Rechnung.class,
                    Rechnungsposition.class, Aufwand.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupAdressen backupAdressen = new BackupAdressen();
            backupAdressen.setBackupdatum(new Date());

            Adresse adresse = listAdressen.getValue();

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

            jaxbMarshaller.marshal(backupAdressen, stream);
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
            .withCaption("Datei mit Adresse, Rechnungen, Rechnungspositionen, Aufwand herunterladen").withIcon(VaadinIcons.DOWNLOAD);


    private ComboBox<Buchhaltung> listBuchhaltungen = new ComboBox<>();
    Button downloaderBuchhaltung = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        Buchhaltung buchhaltung = listBuchhaltungen.getValue();
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

    private Upload uploadXmlAdressen = new Upload("Upload Adressen XML", adressenJSONUploadReceiver);
    private Upload uploadXmlBuchhaltungen = new Upload("Upload Buchhaltungen XML", buchhaltungenJSONUploadReceiver);
    private Upload uploadXmlTemplateBuchhaltungen = new Upload("Upload Template Buchhaltungen XML", templateBuchhaltungenJSONUploadReceiver);

    private Upload uploadJSONAdressen = new Upload("Upload Adressen JSON", adressenJSONUploadReceiver);
    private Upload uploadJSONBuchhaltungen = new Upload("Upload Buchhaltungen JSON", buchhaltungenJSONUploadReceiver);
    private Upload uploadJSONTemplateBuchhaltungen = new Upload("Upload Template Buchhaltungen JSON", templateBuchhaltungenJSONUploadReceiver);




    @PostConstruct
    void init() {
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

        List<TemplateBuchhaltung> templateBuchhaltungen = templateBuchhaltungDeltaspikeFacade.findAll();
        if (templateBuchhaltungen.size() == 0) {
            listTemplateBuchhaltungen.setEnabled(false);
            downloaderTemplateBuchhaltungen.setEnabled(false);
            downloaderTemplateBuchhaltung.setEnabled(false);
        } else {
            listTemplateBuchhaltungen.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
            listTemplateBuchhaltungen.setItemCaptionGenerator(templateBuchhaltung -> templateBuchhaltung.getBezeichnung());
            listTemplateBuchhaltungen.setEmptySelectionAllowed(false);
            listTemplateBuchhaltungen.setSelectedItem(templateBuchhaltungDeltaspikeFacade.findAll().get(0));
        }
        listTemplateBuchhaltungen.setWidth(20, Unit.EM);

        List<Buchhaltung> buchhaltungen = buchhaltungDeltaspikeFacade.findAll();
        if (buchhaltungen.size() == 0) {
            listBuchhaltungen.setEnabled(false);
            downloaderXmlBuchhaltungen.setEnabled(false);
            downloaderBuchhaltung.setEnabled(false);
        } else {
            listBuchhaltungen.setItems(buchhaltungDeltaspikeFacade.findAll());
            listBuchhaltungen.setItemCaptionGenerator(buchhaltung -> buchhaltung.getBezeichnung() + " " + buchhaltung.getJahr());
            listBuchhaltungen.setEmptySelectionAllowed(false);
            listBuchhaltungen.setSelectedItem(buchhaltungDeltaspikeFacade.findAll().get(0));
        }
        listBuchhaltungen.setWidth(20, Unit.EM);

        List<Adresse> adressen = adresseDeltaspikeFacade.findAll();
        if (adressen.size() == 0) {
            listAdressen.setEnabled(false);
            downloaderAdressenXml.setEnabled(false);
            downloaderAdresse.setEnabled(false);
        } else {
            listAdressen.setItems(adresseDeltaspikeFacade.findAll());
            listAdressen.setItemCaptionGenerator(adresse -> adresse.getFirma() + " " + adresse.getNachname() + " " + adresse.getOrt());
            listAdressen.setEmptySelectionAllowed(false);
            listAdressen.setSelectedItem(adresseDeltaspikeFacade.findAll().get(0));
        }
        listAdressen.setWidth(20, Unit.EM);

        Panel panelBackup = new Panel("Backup");
        panelBackup.setContent(new MVerticalLayout(downloaderAdressenXml, downloadAdressenJson, downloaderXmlBuchhaltungen, downloaderJSONBuchhaltungen,
                downloaderTemplateBuchhaltungen,
                new HorizontalLayout(downloaderBuchhaltung, listBuchhaltungen),
                new HorizontalLayout(downloaderTemplateBuchhaltung, listTemplateBuchhaltungen),
                new HorizontalLayout(downloaderAdresse, listAdressen)
        ));

        Panel panelRestore = new Panel("Restore");

        Panel panelRestoreXML = new Panel("XML");
        Panel panelRestoreJSON = new Panel("JSON");

        panelRestoreXML.setContent(new VerticalLayout(uploadXmlAdressen,uploadXmlBuchhaltungen,uploadXmlTemplateBuchhaltungen));
        panelRestoreJSON.setContent(new VerticalLayout(uploadJSONAdressen,uploadJSONBuchhaltungen,uploadJSONTemplateBuchhaltungen));

        panelRestore.setContent(
                new VerticalLayout(panelRestoreJSON,panelRestoreXML));


        //Panel panelTemplateKontoplaene = new Panel("Templates Kontoplan");
        //panelTemplateKontoplaene.setContent(new MVerticalLayout(uploaderTemplateKontoplan,
        //        new MHorizontalLayout(templateKontoplanSelect, downloaderTemplateKontoplan)));

        setMargin(true);
        setSpacing(true);
        addComponent(new MHorizontalLayout(menu));
        addComponent(new MHorizontalLayout(panelBackup, panelRestore));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
