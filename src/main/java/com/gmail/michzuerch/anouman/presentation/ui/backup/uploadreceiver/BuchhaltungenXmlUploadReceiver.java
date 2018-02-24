package com.gmail.michzuerch.anouman.presentation.ui.backup.uploadreceiver;

import com.gmail.michzuerch.anouman.backend.entity.*;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.*;
import com.gmail.michzuerch.anouman.presentation.ui.backup.BackupView;
import com.gmail.michzuerch.anouman.presentation.ui.backup.dto.buchhaltungen.*;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class BuchhaltungenXmlUploadReceiver implements Serializable, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    File tempFile;

    @Inject
    BuchhaltungDeltaspikeFacade buchhaltungDeltaspikeFacade;

    @Inject
    KontoklasseDeltaspikeFacade kontoklasseDeltaspikeFacade;

    @Inject
    KontogruppeDeltaspikeFacade kontogruppeDeltaspikeFacade;

    @Inject
    KontohauptgruppeDeltaspikeFacade kontohauptgruppeDeltaspikeFacade;

    @Inject
    KontoDeltaspikeFacade kontoDeltaspikeFacade;

    @Inject
    MehrwertsteuercodeDeltaspikeFacade mehrwertsteuercodeDeltaspikeFacade;

    public BuchhaltungenXmlUploadReceiver() {
    }

    @Override
    public OutputStream receiveUpload(String s, String s1) {
        OutputStream outputStream = null;
        try {
            tempFile = File.createTempFile("upl-buchhaltungen", ".tmp");
            outputStream = new FileOutputStream(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    //@todo Problem mit Mehrwertsteuercode und Buchungen
    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupBuchhaltungen.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            BackupBuchhaltungen backupBuchhaltungen =
                    (BackupBuchhaltungen) unmarshaller.unmarshal(new FileInputStream(tempFile));

            for (BackupBuchhaltung backupBuchhaltung : backupBuchhaltungen.getBuchhaltungen()) {
                Buchhaltung buchhaltung = new Buchhaltung();
                String bezeichnung = backupBuchhaltung.getBezeichnung();
                buchhaltung.setBezeichnung(bezeichnung);
                buchhaltung = buchhaltungDeltaspikeFacade.save(buchhaltung);

                for (BackupKontoklasse backupKontoklasse : backupBuchhaltung.getKontoklasses()) {
                    Kontoklasse kontoklasse = new Kontoklasse();
                    kontoklasse.setBezeichnung(backupKontoklasse.getBezeichnung());
                    kontoklasse.setKontonummer(backupKontoklasse.getKontonummer());
                    kontoklasse.setBuchhaltung(buchhaltung);
                    kontoklasse = kontoklasseDeltaspikeFacade.save(kontoklasse);
                    buchhaltung.getKontoklasse().add(kontoklasse);
                    buchhaltung = buchhaltungDeltaspikeFacade.save(buchhaltung);

                    for (BackupKontohauptgruppe backupKontohauptgruppe : backupKontoklasse.getBackupKontohauptgruppes()) {
                        Kontohauptgruppe kontohauptgruppe = new Kontohauptgruppe();
                        kontohauptgruppe.setBezeichnung(backupKontohauptgruppe.getBezeichnung());
                        kontohauptgruppe.setKontonummer(backupKontohauptgruppe.getKontonummer());
                        kontohauptgruppe.setKontoklasse(kontoklasse);
                        kontohauptgruppe = kontohauptgruppeDeltaspikeFacade.save(kontohauptgruppe);
                        kontoklasse.getKontohauptgruppes().add(kontohauptgruppe);
                        kontoklasse = kontoklasseDeltaspikeFacade.save(kontoklasse);

                        for (BackupKontogruppe backupKontogruppe : backupKontohauptgruppe.getBackupKontogruppes()) {
                            Kontogruppe kontogruppe = new Kontogruppe();
                            kontogruppe.setBezeichnung(backupKontogruppe.getBezeichnung());
                            kontogruppe.setKontonummer(backupKontogruppe.getKontonummer());
                            kontogruppe.setKontohauptgruppe(kontohauptgruppe);
                            kontogruppe = kontogruppeDeltaspikeFacade.save(kontogruppe);
                            kontohauptgruppe.getKontogruppes().add(kontogruppe);
                            kontohauptgruppe = kontohauptgruppeDeltaspikeFacade.save(kontohauptgruppe);


                            for (BackupKonto backupKonto : backupKontogruppe.getKontos()) {
                                Konto konto = new Konto();
                                konto.setBezeichnung(backupKonto.getBezeichnung());
                                konto.setKontonummer(backupKonto.getKontonummer());
                                konto.setKontogruppe(kontogruppe);
                                konto = kontoDeltaspikeFacade.save(konto);
                                kontogruppe.getKontos().add(konto);
                                kontogruppe = kontogruppeDeltaspikeFacade.save(kontogruppe);
                            }
                        }
                    }
                }
                buchhaltung = buchhaltungDeltaspikeFacade.save(buchhaltung);
            }
            tempFile.deleteOnExit();
            Notification.show(backupBuchhaltungen.getBuchhaltungen().size() + " Buchhaltungen neu erstellt", Notification.Type.HUMANIZED_MESSAGE);
        } catch (JAXBException e) {
            Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
        }
        Notification.show("Buchhaltungen Upload succeeded:" + succeededEvent.getLength(), Notification.Type.TRAY_NOTIFICATION);
    }

    @PostConstruct
    private void init() {
    }
}

