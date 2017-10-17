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
    TemplateSammelkontoFacade templateSammelkontoFacade;

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
                TemplateBuchhaltung templateBuchhaltung = new TemplateBuchhaltung();
                String bezeichnung = backupTemplateBuchhaltung.getBezeichnung();
                templateBuchhaltung.setBezeichnung(bezeichnung);
                templateBuchhaltung = templateBuchhaltungFacade.save(templateBuchhaltung);

                for (BackupTemplateKontoklasse backupTemplateKontoklasse : backupTemplateBuchhaltung.getKontoklasses()) {
                    TemplateKontoklasse templateKontoklasse = new TemplateKontoklasse();
                    templateKontoklasse.setBezeichnung(backupTemplateKontoklasse.getBezeichnung());
                    templateKontoklasse.setKontonummer(backupTemplateKontoklasse.getKontonummer());
                    templateKontoklasse.setTemplateBuchhaltung(templateBuchhaltung);
                    templateKontoklasse = templateKontoklasseFacade.save(templateKontoklasse);
                    templateBuchhaltung.getTemplateKontoklasses().add(templateKontoklasse);
                    templateBuchhaltung = templateBuchhaltungFacade.save(templateBuchhaltung);

                    for (BackupTemplateKontogruppe backupTemplateKontogruppe : backupTemplateKontoklasse.getKontogruppen()) {
                        TemplateKontogruppe templateKontogruppe = new TemplateKontogruppe();
                        templateKontogruppe.setBezeichnung(backupTemplateKontogruppe.getBezeichnung());
                        templateKontogruppe.setKontonummer(backupTemplateKontogruppe.getKontonummer());
                        templateKontogruppe.setTemplateKontoklasse(templateKontoklasse);
                        templateKontogruppe = templateKontogruppeFacade.save(templateKontogruppe);
                        templateKontoklasse.getTemplateKontogruppes().add(templateKontogruppe);
                        templateKontoklasse = templateKontoklasseFacade.save(templateKontoklasse);

                        for (BackupTemplateKontoart backupTemplateKontoart : backupTemplateKontogruppe.getKontoarten()) {
                            TemplateKontoart templateKontoart = new TemplateKontoart();
                            templateKontoart.setBezeichnung(backupTemplateKontoart.getBezeichnung());
                            templateKontoart.setKontonummer(backupTemplateKontoart.getKontonummer());
                            templateKontoart.setTemplateKontogruppe(templateKontogruppe);
                            templateKontoart = templateKontoartFacade.save(templateKontoart);
                            templateKontogruppe.getTemplateKontoarts().add(templateKontoart);
                            templateKontogruppe = templateKontogruppeFacade.save(templateKontogruppe);

                            for (BackupTemplateSammelkonto backupTemplateSammelkonto : backupTemplateKontoart.getBackupTemplateSammelkontos()) {
                                TemplateSammelkonto templateSammelkonto = new TemplateSammelkonto();
                                templateSammelkonto.setBezeichnung(backupTemplateSammelkonto.getBezeichnung());
                                templateSammelkonto.setKontonummer(backupTemplateSammelkonto.getKontonummer());
                                templateSammelkonto.setTemplateKontoart(templateKontoart);
                                templateSammelkonto = templateSammelkontoFacade.save(templateSammelkonto);
                                templateKontoart.getTemplateSammelkontos().add(templateSammelkonto);
                                templateKontoart = templateKontoartFacade.save(templateKontoart);

                                for (BackupTemplateKonto backupTemplateKonto : backupTemplateSammelkonto.getKonti()) {
                                    TemplateKonto templateKonto = new TemplateKonto();
                                    templateKonto.setBezeichnung(backupTemplateKonto.getBezeichnung());
                                    templateKonto.setBemerkung(backupTemplateKonto.getBemerkung());
                                    templateKonto.setKontonummer(backupTemplateKonto.getKontonummer());
                                    templateKonto.setTemplateSammelkonto(templateSammelkonto);
                                    templateKonto = templateKontoFacade.save(templateKonto);
                                    templateSammelkonto.getTemplateKontos().add(templateKonto);
                                    templateSammelkonto = templateSammelkontoFacade.save(templateSammelkonto);


                                    for (BackupTemplateMehrwertsteuercode backupTemplateMehrwertsteuercode : backupTemplateKonto.getMehrwertsteuercodes()) {
                                        TemplateMehrwertsteuercode templateMehrwertsteuercode = new TemplateMehrwertsteuercode();
                                        templateMehrwertsteuercode.setCode(backupTemplateMehrwertsteuercode.getCode());
                                        templateMehrwertsteuercode.setBezeichnung(backupTemplateMehrwertsteuercode.getBezeichnung());
                                        templateMehrwertsteuercode.setProzent(backupTemplateMehrwertsteuercode.getProzent());
                                        templateMehrwertsteuercode.setVerkauf(backupTemplateMehrwertsteuercode.isVerkauf());
                                        templateMehrwertsteuercode.setTemplateBuchhaltung(templateBuchhaltung);
                                        templateMehrwertsteuercode = templateMehrwertsteuercodeFacade.save(templateMehrwertsteuercode);
                                        templateKonto.getTemplateMehrwertsteuercode().add(templateMehrwertsteuercode);
                                        templateKonto = templateKontoFacade.save(templateKonto);
                                    }
                                }
                            }
                        }
                    }
                }
                templateBuchhaltung = templateBuchhaltungFacade.save(templateBuchhaltung);
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

