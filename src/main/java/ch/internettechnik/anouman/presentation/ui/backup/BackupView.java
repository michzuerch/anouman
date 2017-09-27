package ch.internettechnik.anouman.presentation.ui.backup;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AdresseFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.BuchhaltungFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver.AdressenUploadReceiver;
import ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver.BuchhaltungenUploadReceiver;
import ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver.TemplateBuchhaltungenUploadReceiver;
import ch.internettechnik.anouman.presentation.ui.backup.xml.adressen.BackupAdressen;
import ch.internettechnik.anouman.presentation.ui.backup.xml.buchhaltungen.*;
import ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen.*;
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
@CDIView(value = "Backup")
public class BackupView extends VerticalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    @Inject
    Menu menu;

    @Inject
    AdresseFacade adresseFacade;

    @Inject
    RechnungFacade rechnungFacade;

    @Inject
    BuchhaltungFacade buchhaltungFacade;

    @Inject
    TemplateBuchhaltungFacade templateBuchhaltungFacade;

    @Inject
    TemplateBuchhaltungenUploadReceiver templateBuchhaltungenUploadReceiver;

    @Inject
    AdressenUploadReceiver adressenUploadReceiver;


    Button downloaderAdressen = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupAdressen.class, Adresse.class, Rechnung.class,
                    Rechnungsposition.class, Aufwand.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupAdressen backupAdressen = new BackupAdressen();
            backupAdressen.setAdressen(adresseFacade.findAll());
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
            .withCaption("Datei mit Adressen, Rechnungen, Rechnungspositionen, Aufwand herunterladen").withIcon(VaadinIcons.DOWNLOAD);

    Button downloaderBuchhaltungen = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupBuchhaltungen.class, BackupBuchhaltung.class);
            logger.debug("Start");
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupBuchhaltungen backupBuchhaltungen = new BackupBuchhaltungen();
            backupBuchhaltungen.setDatum(new Date());

            buchhaltungFacade.findAll().stream().forEach(buchhaltung -> {
                BackupBuchhaltung backupBuchhaltung = new BackupBuchhaltung(buchhaltung.getBezeichnung(), buchhaltung.getJahr());

                buchhaltung.getMehrwertsteuercode().stream().forEach(mehrwertsteuercode -> {
                    BackupMehrwertsteuercode backupMehrwertsteuercode = new BackupMehrwertsteuercode();
                    backupMehrwertsteuercode.setId(mehrwertsteuercode.getId());
                    backupMehrwertsteuercode.setBezeichnung(mehrwertsteuercode.getBezeichnung());
                    backupMehrwertsteuercode.setCode(mehrwertsteuercode.getCode());
                    backupMehrwertsteuercode.setProzent(mehrwertsteuercode.getProzent());
                    backupMehrwertsteuercode.setVerkauf(mehrwertsteuercode.isVerkauf());
                    backupMehrwertsteuercode.setKonto(mehrwertsteuercode.getMehrwertsteuerKonto().getId());
                    backupBuchhaltung.getMehrwertsteuercodes().add(backupMehrwertsteuercode);
                });
                buchhaltung.getKontoklasse().stream().forEach(kontoklasse -> {
                    BackupKontoklasse backupKontoklasse = new BackupKontoklasse(kontoklasse.getBezeichnung(), kontoklasse.getKontonummer());
                    backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
                    kontoklasse.getKontogruppes().stream().forEach(kontogruppe -> {
                        BackupKontogruppe backupKontogruppe = new BackupKontogruppe(kontogruppe.getBezeichnung(), kontogruppe.getKontonummer());
                        backupKontoklasse.getKontogruppen().add(backupKontogruppe);
                        kontogruppe.getKontoarts().stream().forEach(kontoart -> {
                            BackupKontoart backupKontoart = new BackupKontoart(kontoart.getBezeichnung(), kontoart.getKontonummer());
                            backupKontogruppe.getKontoarten().add(backupKontoart);
                            kontoart.getKontos().stream().forEach(konto -> {
                                BackupKonto backupKonto = new BackupKonto(konto.getId(), konto.getBezeichnung(), konto.getKontonummer(), konto.getShowKontonummer(), konto.getAnfangsbestand());
                                backupKontoart.getKonti().add(backupKonto);
                                konto.getSoll().stream().forEach(buchung -> {
                                    BackupBuchung backupBuchung = new BackupBuchung();
                                    backupBuchung.setId(buchung.getId());
                                    backupBuchung.setBuchungstext(buchung.getBuchungstext());
                                    backupBuchung.setLaufnummer(buchung.getLaufnummer());
                                    backupBuchung.setBuchungsdatum(buchung.getBuchungsdatum());
                                    backupBuchung.setBetrag(buchung.getBetrag());
                                    backupBuchung.setKontoSoll(buchung.getKontoSoll().getId());
                                    backupBuchung.setKontoHaben(buchung.getKontoHaben().getId());
                                    //if (buchung.getMehrwertsteuercode() != null)
                                    //   backupBuchung.setMehrwertsteuercode(buchung.getMehrwertsteuercode().getId());
                                    //backupKonto.addBuchung(backupBuchung);
                                    //backupBuchhaltung.addBuchung(backupBuchung);
                                });
                                konto.getHaben().stream().forEach(buchung -> {
                                    BackupBuchung backupBuchung = new BackupBuchung();
                                    backupBuchung.setId(buchung.getId());
                                    backupBuchung.setBuchungstext(buchung.getBuchungstext());
                                    backupBuchung.setLaufnummer(buchung.getLaufnummer());
                                    backupBuchung.setBuchungsdatum(buchung.getBuchungsdatum());
                                    backupBuchung.setBetrag(buchung.getBetrag());
                                    backupBuchung.setKontoSoll(buchung.getKontoSoll().getId());
                                    backupBuchung.setKontoHaben(buchung.getKontoHaben().getId());
                                    //if (buchung.getMehrwertsteuercode() != null)
                                    //    backupBuchung.setMehrwertsteuercode(buchung.getMehrwertsteuercode().getId());
                                    //backupKonto.addBuchung(backupBuchung);
                                    //backupBuchhaltung.addBuchung(backupBuchung);
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
            .withCaption("Datei mit allen Buchhaltungen herunterladen").withIcon(VaadinIcons.DOWNLOAD);


    Button downloaderTemplateBuchhaltungen = new DownloadButton(stream -> {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupTemplateBuchhaltungen backupBuchhaltungen = new BackupTemplateBuchhaltungen();
            backupBuchhaltungen.setDatum(new Date());
            templateBuchhaltungFacade.findAll().stream().forEach(buchhaltung -> {
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
                buchhaltung.getTemplateKontoklasses().stream().forEach(kontoklasse -> {
                    BackupTemplateKontoklasse backupKontoklasse = new BackupTemplateKontoklasse(kontoklasse.getBezeichnung(), kontoklasse.getKontonummer());
                    backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
                    kontoklasse.getTemplateKontogruppes().stream().forEach(kontogruppe -> {
                        BackupTemplateKontogruppe backupKontogruppe = new BackupTemplateKontogruppe(kontogruppe.getBezeichnung(), kontogruppe.getKontonummer());
                        backupKontoklasse.getKontogruppen().add(backupKontogruppe);
                        kontogruppe.getTemplateKontoarts().stream().forEach(kontoart -> {
                            BackupTemplateKontoart backupKontoart = new BackupTemplateKontoart(kontoart.getBezeichnung(), kontoart.getKontonummer());
                            backupKontogruppe.getKontoarten().add(backupKontoart);
                            kontoart.getTemplateKontos().stream().forEach(konto -> {
                                BackupTemplateKonto backupKonto = new BackupTemplateKonto(konto.getId(), konto.getBezeichnung(), konto.getKontonummer(), konto.getShowKontonummer());
                                backupKontoart.getKonti().add(backupKonto);
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
            listTemplateBuchhaltungen.getValue().getTemplateKontoklasses().stream().forEach(kontoklasse -> {
                BackupTemplateKontoklasse backupKontoklasse = new BackupTemplateKontoklasse(kontoklasse.getBezeichnung(), kontoklasse.getKontonummer());
                backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
                kontoklasse.getTemplateKontogruppes().stream().forEach(kontogruppe -> {
                    BackupTemplateKontogruppe backupKontogruppe = new BackupTemplateKontogruppe(kontogruppe.getBezeichnung(), kontogruppe.getKontonummer());
                    backupKontoklasse.getKontogruppen().add(backupKontogruppe);
                    kontogruppe.getTemplateKontoarts().stream().forEach(kontoart -> {
                        BackupTemplateKontoart backupKontoart = new BackupTemplateKontoart(kontoart.getBezeichnung(), kontoart.getKontonummer());
                        backupKontogruppe.getKontoarten().add(backupKontoart);
                        kontoart.getTemplateKontos().stream().forEach(konto -> {
                            BackupTemplateKonto backupKonto = new BackupTemplateKonto(konto.getId(), konto.getBezeichnung(), konto.getKontonummer(), konto.getShowKontonummer());
                            backupKontoart.getKonti().add(backupKonto);
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
            jaxbContext = JAXBContext.newInstance(BackupAdressen.class, Adresse.class, Rechnung.class,
                    Rechnungsposition.class, Aufwand.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupAdressen backupAdressen = new BackupAdressen();
            Adresse adresse = adresseFacade.findBy(listAdressen.getValue().getId());
            backupAdressen.getAdressen().add(adresse);
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
        try {
            jaxbContext = JAXBContext.newInstance(BackupBuchhaltung.class);
            logger.debug("Start");
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            BackupBuchhaltung backupBuchhaltung = new BackupBuchhaltung(listBuchhaltungen.getValue().getBezeichnung(),
                    listBuchhaltungen.getValue().getJahr());


            backupBuchhaltung.getMehrwertsteuercodes().stream().forEach(mehrwertsteuercode -> {
                BackupMehrwertsteuercode backupMehrwertsteuercode = new BackupMehrwertsteuercode();
                backupMehrwertsteuercode.setId(mehrwertsteuercode.getId());
                backupMehrwertsteuercode.setBezeichnung(mehrwertsteuercode.getBezeichnung());
                backupMehrwertsteuercode.setCode(mehrwertsteuercode.getCode());
                backupMehrwertsteuercode.setProzent(mehrwertsteuercode.getProzent());
                backupMehrwertsteuercode.setVerkauf(mehrwertsteuercode.isVerkauf());
                backupMehrwertsteuercode.setKonto(mehrwertsteuercode.getKonto());
                backupBuchhaltung.getMehrwertsteuercodes().add(backupMehrwertsteuercode);
            });
            listBuchhaltungen.getValue().getKontoklasse().stream().forEach(kontoklasse -> {
                BackupKontoklasse backupKontoklasse = new BackupKontoklasse(kontoklasse.getBezeichnung(), kontoklasse.getKontonummer());
                backupBuchhaltung.getKontoklasses().add(backupKontoklasse);
                kontoklasse.getKontogruppes().stream().forEach(kontogruppe -> {
                    BackupKontogruppe backupKontogruppe = new BackupKontogruppe(kontogruppe.getBezeichnung(), kontogruppe.getKontonummer());
                    backupKontoklasse.getKontogruppen().add(backupKontogruppe);
                    kontogruppe.getKontoarts().stream().forEach(kontoart -> {
                        BackupKontoart backupKontoart = new BackupKontoart(kontoart.getBezeichnung(), kontoart.getKontonummer());
                        backupKontogruppe.getKontoarten().add(backupKontoart);
                        kontoart.getKontos().stream().forEach(konto -> {
                            BackupKonto backupKonto = new BackupKonto(konto.getId(), konto.getBezeichnung(), konto.getKontonummer(), konto.getShowKontonummer(), konto.getAnfangsbestand());
                            backupKontoart.getKonti().add(backupKonto);
                        });
                    });
                });
            });
            jaxbMarshaller.marshal(backupBuchhaltung, stream);
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
            .withCaption("Datei mit Buchhaltung herunterladen").withIcon(VaadinIcons.DOWNLOAD);
    private Upload uploadAdressen = new Upload("Upload Adressen", adressenUploadReceiver);
    private BuchhaltungenUploadReceiver buchhaltungenUploadReceiver = new BuchhaltungenUploadReceiver();
    private Upload uploadBuchhaltungen = new Upload("Upload Buchhaltungen", buchhaltungenUploadReceiver);
    //private TemplateBuchhaltungenUploadReceiver templateBuchhaltungenUploadReceiver = new TemplateBuchhaltungenUploadReceiver();
    private Upload uploadTemplateBuchhaltungen = new Upload("Upload Template Buchhaltungen", templateBuchhaltungenUploadReceiver);

    @PostConstruct
    void init() {
        uploadAdressen.addSucceededListener(adressenUploadReceiver);
        uploadAdressen.setReceiver(adressenUploadReceiver);

        uploadBuchhaltungen.addSucceededListener(buchhaltungenUploadReceiver);

        uploadTemplateBuchhaltungen.addSucceededListener(templateBuchhaltungenUploadReceiver);
        uploadTemplateBuchhaltungen.setReceiver(templateBuchhaltungenUploadReceiver);

        List<TemplateBuchhaltung> templateBuchhaltungen = templateBuchhaltungFacade.findAll();
        if (templateBuchhaltungen.size() == 0) {
            listTemplateBuchhaltungen.setEnabled(false);
            downloaderTemplateBuchhaltungen.setEnabled(false);
            downloaderTemplateBuchhaltung.setEnabled(false);
        } else {
            listTemplateBuchhaltungen.setItems(templateBuchhaltungFacade.findAll());
            listTemplateBuchhaltungen.setItemCaptionGenerator(templateBuchhaltung -> templateBuchhaltung.getBezeichnung());
            listTemplateBuchhaltungen.setEmptySelectionAllowed(false);
            listTemplateBuchhaltungen.setSelectedItem(templateBuchhaltungFacade.findAll().get(0));
        }
        listTemplateBuchhaltungen.setWidth(20, Unit.EM);

        List<Buchhaltung> buchhaltungen = buchhaltungFacade.findAll();
        if (buchhaltungen.size() == 0) {
            listBuchhaltungen.setEnabled(false);
            downloaderBuchhaltungen.setEnabled(false);
            downloaderBuchhaltung.setEnabled(false);
        } else {
            listBuchhaltungen.setItems(buchhaltungFacade.findAll());
            listBuchhaltungen.setItemCaptionGenerator(buchhaltung -> buchhaltung.getBezeichnung() + " " + buchhaltung.getJahr());
            listBuchhaltungen.setEmptySelectionAllowed(false);
            listBuchhaltungen.setSelectedItem(buchhaltungFacade.findAll().get(0));
        }
        listBuchhaltungen.setWidth(20, Unit.EM);

        List<Adresse> adressen = adresseFacade.findAll();
        if (adressen.size() == 0) {
            listAdressen.setEnabled(false);
            downloaderAdressen.setEnabled(false);
            downloaderAdresse.setEnabled(false);
        } else {
            listAdressen.setItems(adresseFacade.findAll());
            listAdressen.setItemCaptionGenerator(adresse -> adresse.getFirma() + " " + adresse.getNachname() + " " + adresse.getOrt());
            listAdressen.setEmptySelectionAllowed(false);
            listAdressen.setSelectedItem(adresseFacade.findAll().get(0));
        }
        listAdressen.setWidth(20, Unit.EM);

        Panel panelBackup = new Panel("Backup");
        panelBackup.setContent(new MVerticalLayout(downloaderAdressen, downloaderBuchhaltungen, downloaderTemplateBuchhaltungen,
                new HorizontalLayout(downloaderBuchhaltung, listBuchhaltungen),
                new HorizontalLayout(downloaderTemplateBuchhaltung, listTemplateBuchhaltungen),
                new HorizontalLayout(downloaderAdresse, listAdressen)
        ));

        Panel panelRestore = new Panel("Restore");
        panelRestore.setContent(new MVerticalLayout(uploadAdressen, uploadBuchhaltungen, uploadTemplateBuchhaltungen));

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
