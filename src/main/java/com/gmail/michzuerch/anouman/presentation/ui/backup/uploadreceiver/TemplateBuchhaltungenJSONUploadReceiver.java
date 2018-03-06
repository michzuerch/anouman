package com.gmail.michzuerch.anouman.presentation.ui.backup.uploadreceiver;

import com.gmail.michzuerch.anouman.backend.entity.*;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.BackupView;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.templatebuchhaltungen.*;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class TemplateBuchhaltungenJSONUploadReceiver implements Serializable, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    File tempFile;

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

    public TemplateBuchhaltungenJSONUploadReceiver() {
    }

    @Override
    public OutputStream receiveUpload(String s, String s1) {
        OutputStream outputStream = null;
        try {
            tempFile = File.createTempFile("upl-templatebuchhaltungen", ".tmp");
            outputStream = new FileOutputStream(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    //@todo Problem mit Mehrwertsteuercode
    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            BackupTemplateBuchhaltungen backupTemplateBuchhaltungen =
                    (BackupTemplateBuchhaltungen) unmarshaller.unmarshal(new FileInputStream(tempFile));

            for (BackupTemplateBuchhaltung backupTemplateBuchhaltung : backupTemplateBuchhaltungen.getBuchhaltungen()) {
                TemplateBuchhaltung templateBuchhaltung = new TemplateBuchhaltung();
                String bezeichnung = backupTemplateBuchhaltung.getBezeichnung();
                templateBuchhaltung.setBezeichnung(bezeichnung);
                templateBuchhaltung = templateBuchhaltungDeltaspikeFacade.save(templateBuchhaltung);

                for (BackupTemplateKontoklasse backupTemplateKontoklasse : backupTemplateBuchhaltung.getKontoklasses()) {
                    TemplateKontoklasse templateKontoklasse = new TemplateKontoklasse();
                    templateKontoklasse.setBezeichnung(backupTemplateKontoklasse.getBezeichnung());
                    templateKontoklasse.setKontonummer(backupTemplateKontoklasse.getKontonummer());
                    templateKontoklasse.setTemplateBuchhaltung(templateBuchhaltung);
                    templateKontoklasse = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse);
                    templateBuchhaltung.getTemplateKontoklasses().add(templateKontoklasse);
                    templateBuchhaltung = templateBuchhaltungDeltaspikeFacade.save(templateBuchhaltung);

                    for (BackupTemplateKontohauptgruppe backupTemplateKontohauptgruppe : backupTemplateKontoklasse.getKontohauptgruppen()) {
                        TemplateKontohauptgruppe templateKontohauptgruppe = new TemplateKontohauptgruppe();
                        templateKontohauptgruppe.setBezeichnung(backupTemplateKontohauptgruppe.getBezeichnung());
                        templateKontohauptgruppe.setKontonummer(backupTemplateKontohauptgruppe.getKontonummer());
                        templateKontohauptgruppe.setTemplateKontoklasse(templateKontoklasse);
                        templateKontohauptgruppe = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe);
                        templateKontoklasse.getTemplateKontohauptgruppes().add(templateKontohauptgruppe);
                        templateKontoklasse = templateKontoklasseDeltaspikeFacade.save(templateKontoklasse);

                        for (BackupTemplateKontogruppe backupTemplateKontogruppe : backupTemplateKontohauptgruppe.getBackupTemplateKontogruppes()) {
                            TemplateKontogruppe templateKontogruppe = new TemplateKontogruppe();
                            templateKontogruppe.setBezeichnung(backupTemplateKontogruppe.getBezeichnung());
                            templateKontogruppe.setKontonummer(backupTemplateKontogruppe.getKontonummer());
                            templateKontogruppe.setTemplateKontohauptgruppe(templateKontohauptgruppe);
                            templateKontogruppe = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe);
                            templateKontohauptgruppe.getTemplateKontogruppes().add(templateKontogruppe);
                            templateKontohauptgruppe = templateKontohauptgruppeDeltaspikeFacade.save(templateKontohauptgruppe);

                            for (BackupTemplateKonto backupTemplateKonto : backupTemplateKontogruppe.getBackupTemplateKontos()) {
                                TemplateKonto templateKonto = new TemplateKonto();
                                templateKonto.setBezeichnung(backupTemplateKonto.getBezeichnung());
                                templateKonto.setKontonummer(backupTemplateKonto.getKontonummer());
                                templateKonto.setTemplateKontogruppe(templateKontogruppe);
                                templateKonto = templateKontoDeltaspikeFacade.save(templateKonto);
                                templateKontogruppe.getTemplateKontos().add(templateKonto);
                                templateKontogruppe = templateKontogruppeDeltaspikeFacade.save(templateKontogruppe);

                                for (BackupTemplateMehrwertsteuercode backupTemplateMehrwertsteuercode : backupTemplateKonto.getBackupTemplateMehrwertsteuercodes()) {
                                    TemplateMehrwertsteuercode templateMehrwertsteuercode = new TemplateMehrwertsteuercode();
                                    templateMehrwertsteuercode.setCode(backupTemplateMehrwertsteuercode.getCode());
                                    templateMehrwertsteuercode.setBezeichnung(backupTemplateMehrwertsteuercode.getBezeichnung());
                                    templateMehrwertsteuercode.setProzent(backupTemplateMehrwertsteuercode.getProzent());
                                    templateMehrwertsteuercode.setVerkauf(backupTemplateMehrwertsteuercode.isVerkauf());
                                    templateMehrwertsteuercode.setTemplateBuchhaltung(templateBuchhaltung);
                                    templateMehrwertsteuercode = templateMehrwertsteuercodeDeltaspikeFacade.save(templateMehrwertsteuercode);
                                    templateKonto.getTemplateMehrwertsteuercodes().add(templateMehrwertsteuercode);
                                    templateKonto = templateKontoDeltaspikeFacade.save(templateKonto);
                                }
                            }
                        }
                    }
                }
                templateBuchhaltung = templateBuchhaltungDeltaspikeFacade.save(templateBuchhaltung);
            }
            tempFile.deleteOnExit();
            Notification.show(backupTemplateBuchhaltungen.getBuchhaltungen().size() + " Template Buchhaltungen neu erstellt", Notification.Type.HUMANIZED_MESSAGE);
        } catch (JAXBException e) {
            Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
        }
        Notification.show("Template Buchhaltungen Upload succeeded:" + succeededEvent.getLength(), Notification.Type.TRAY_NOTIFICATION);
    }

    @PostConstruct
    private void init() {
    }
}

