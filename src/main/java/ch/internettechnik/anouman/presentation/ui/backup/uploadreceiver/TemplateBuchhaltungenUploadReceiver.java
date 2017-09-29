package ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver;

import ch.internettechnik.anouman.backend.entity.*;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.*;
import ch.internettechnik.anouman.presentation.ui.backup.BackupView;
import ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen.*;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@ViewScoped
public class TemplateBuchhaltungenUploadReceiver implements Serializable, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    File tempFile;

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

    public TemplateBuchhaltungenUploadReceiver() {
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
                TemplateBuchhaltung buchhaltung = new TemplateBuchhaltung();
                String bezeichnung = backupTemplateBuchhaltung.getBezeichnung();
                buchhaltung.setBezeichnung(bezeichnung);
                buchhaltung = templateBuchhaltungFacade.save(buchhaltung);

                for (BackupTemplateKontoklasse backupTemplateKontoklasse : backupTemplateBuchhaltung.getKontoklasses()) {
                    TemplateKontoklasse kontoklasse = new TemplateKontoklasse();
                    kontoklasse.setBezeichnung(backupTemplateKontoklasse.getBezeichnung());
                    kontoklasse.setKontonummer(backupTemplateKontoklasse.getKontonummer());
                    kontoklasse.setTemplateBuchhaltung(buchhaltung);
                    kontoklasse = templateKontoklasseFacade.save(kontoklasse);
                    buchhaltung.getTemplateKontoklasses().add(kontoklasse);
                    buchhaltung = templateBuchhaltungFacade.save(buchhaltung);

                    for (BackupTemplateKontogruppe backupTemplateKontogruppe : backupTemplateKontoklasse.getKontogruppen()) {
                        TemplateKontogruppe kontogruppe = new TemplateKontogruppe();
                        kontogruppe.setBezeichnung(backupTemplateKontogruppe.getBezeichnung());
                        kontogruppe.setKontonummer(backupTemplateKontogruppe.getKontonummer());
                        kontogruppe.setTemplateKontoklasse(kontoklasse);
                        kontogruppe = templateKontogruppeFacade.save(kontogruppe);
                        kontoklasse.getTemplateKontogruppes().add(kontogruppe);
                        kontoklasse = templateKontoklasseFacade.save(kontoklasse);

                        for (BackupTemplateKontoart backupTemplateKontoart : backupTemplateKontogruppe.getKontoarten()) {
                            TemplateKontoart kontoart = new TemplateKontoart();
                            kontoart.setBezeichnung(backupTemplateKontoart.getBezeichnung());
                            kontoart.setKontonummer(backupTemplateKontoart.getKontonummer());
                            kontoart.setTemplateKontogruppe(kontogruppe);
                            kontoart = templateKontoartFacade.save(kontoart);
                            kontogruppe.getTemplateKontoarts().add(kontoart);
                            kontogruppe = templateKontogruppeFacade.save(kontogruppe);

                            for (BackupTemplateKonto backupTemplateKonto : backupTemplateKontoart.getKonti()) {
                                TemplateKonto konto = new TemplateKonto();
                                konto.setBezeichnung(backupTemplateKonto.getBezeichnung());
                                konto.setKontonummer(backupTemplateKonto.getKontonummer());
                                konto.setTemplateKontoart(kontoart);
                                konto = templateKontoFacade.save(konto);
                                kontoart.getTemplateKontos().add(konto);
                                kontoart = templateKontoartFacade.save(kontoart);

                                //if (konto.getTemplateMehrwertsteuercode() != null)
                            }
                        }
                    }
                }
                buchhaltung = templateBuchhaltungFacade.save(buchhaltung);
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

