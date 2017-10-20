package ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.backup.BackupView;
import ch.internettechnik.anouman.presentation.ui.backup.xml.buchhaltungen.*;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class BuchhaltungenUploadReceiver implements Serializable, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    File tempFile;

    @Inject
    BuchhaltungFacade buchhaltungFacade;

    @Inject
    KontoklasseFacade kontoklasseFacade;

    @Inject
    KontogruppeFacade kontogruppeFacade;

    @Inject
    KontohauptgruppeFacade kontohauptgruppeFacade;

    @Inject
    KontoFacade kontoFacade;

    @Inject
    MehrwertsteuercodeFacade mehrwertsteuercodeFacade;

    public BuchhaltungenUploadReceiver() {
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
                buchhaltung = buchhaltungFacade.save(buchhaltung);

                for (BackupKontoklasse backupKontoklasse : backupBuchhaltung.getKontoklasses()) {
                    Kontoklasse kontoklasse = new Kontoklasse();
                    kontoklasse.setBezeichnung(backupKontoklasse.getBezeichnung());
                    kontoklasse.setKontonummer(backupKontoklasse.getKontonummer());
                    kontoklasse.setBuchhaltung(buchhaltung);
                    kontoklasse = kontoklasseFacade.save(kontoklasse);
                    buchhaltung.getKontoklasse().add(kontoklasse);
                    buchhaltung = buchhaltungFacade.save(buchhaltung);

                    for (BackupKontohauptgruppe backupKontohauptgruppe : backupKontoklasse.getBackupKontohauptgruppes()) {
                        Kontohauptgruppe kontohauptgruppe = new Kontohauptgruppe();
                        kontohauptgruppe.setBezeichnung(backupKontohauptgruppe.getBezeichnung());
                        kontohauptgruppe.setKontonummer(backupKontohauptgruppe.getKontonummer());
                        kontohauptgruppe.setKontoklasse(kontoklasse);
                        kontohauptgruppe = kontohauptgruppeFacade.save(kontohauptgruppe);
                        kontoklasse.getKontohauptgruppes().add(kontohauptgruppe);
                        kontoklasse = kontoklasseFacade.save(kontoklasse);

                        for (BackupKontogruppe backupKontogruppe : backupKontohauptgruppe.getBackupKontogruppes()) {
                            Kontogruppe kontogruppe = new Kontogruppe();
                            kontogruppe.setBezeichnung(backupKontogruppe.getBezeichnung());
                            kontogruppe.setKontonummer(backupKontogruppe.getKontonummer());
                            kontogruppe.setKontohauptgruppe(kontohauptgruppe);
                            kontogruppe = kontogruppeFacade.save(kontogruppe);
                            kontohauptgruppe.getKontogruppes().add(kontogruppe);
                            kontohauptgruppe = kontohauptgruppeFacade.save(kontohauptgruppe);


                            for (BackupKonto backupKonto : backupKontogruppe.getKontos()) {
                                Konto konto = new Konto();
                                konto.setBezeichnung(backupKonto.getBezeichnung());
                                konto.setKontonummer(backupKonto.getKontonummer());
                                konto.setKontogruppe(kontogruppe);
                                konto = kontoFacade.save(konto);
                                kontogruppe.getKontos().add(konto);
                                kontogruppe = kontogruppeFacade.save(kontogruppe);
                            }
                        }
                    }
                }
                buchhaltung = buchhaltungFacade.save(buchhaltung);
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

