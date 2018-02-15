package com.gmail.michzuerch.anouman.presentation.ui.backup.uploadreceiver;

import com.gmail.michzuerch.anouman.backend.entity.Adresse;
import com.gmail.michzuerch.anouman.backend.entity.Aufwand;
import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.entity.Rechnungsposition;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AufwandDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungspositionDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.backup.BackupView;
import com.gmail.michzuerch.anouman.presentation.ui.backup.xml.adressen.*;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class AdressenXmlUploadReceiver implements Serializable, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    File tempFile;

    @Inject
    AdresseDeltaspikeFacade adresseDeltaspikeFacade;

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    RechnungspositionDeltaspikeFacade rechnungspositionDeltaspikeFacade;

    @Inject
    AufwandDeltaspikeFacade aufwandDeltaspikeFacade;


    public AdressenXmlUploadReceiver() {
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

    //@todo Problem mit outputStream in unmarshal
    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(BackupAdressen.class, Adresse.class, Rechnung.class, Rechnungsposition.class, Aufwand.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            BackupAdressen backupAdressen =
                    (BackupAdressen) unmarshaller.unmarshal(new FileInputStream(tempFile));

            for (BackupAdresse backupAdresse : backupAdressen.getAdressen()) {
                Adresse adresse = new Adresse();
                adresse.setAnrede(backupAdresse.getAnrede());
                adresse.setFirma(backupAdresse.getFirma());
                adresse.setNachname(backupAdresse.getNachname());
                adresse.setVorname(backupAdresse.getVorname());
                adresse.setOrt(backupAdresse.getOrt());
                adresse.setNachname(backupAdresse.getNachname());
                adresse.setPostleitzahl(backupAdresse.getPostleitzahl());
                adresse.setStrasse(backupAdresse.getStrasse());
                adresse.setStundensatz(backupAdresse.getStundensatz());
                adresse = adresseDeltaspikeFacade.save(adresse);
                for (BackupRechnung backupRechnung : backupAdresse.getRechnungen()) {
                    Rechnung rechnung = new Rechnung();
                    rechnung.setRechnungsdatum(backupRechnung.getRechnungsdatum());
                    rechnung.setBezeichnung(backupRechnung.getBezeichnung());
                    rechnung.setVerschickt(backupRechnung.isVerschickt());
                    rechnung.setBezahlt(backupRechnung.isBezahlt());
                    rechnung.setFaelligInTagen(backupRechnung.getFaelligInTagen());
                    rechnung.setAdresse(adresse);
                    adresse = adresseDeltaspikeFacade.save(adresse);
                    rechnung = rechnungDeltaspikeFacade.save(rechnung);

                    for (BackupRechnungsposition backupRechnungsposition : backupRechnung.getRechnungspositions()) {
                        Rechnungsposition rechnungsposition = new Rechnungsposition();
                        rechnungsposition.setAnzahl(backupRechnungsposition.getAnzahl());
                        rechnungsposition.setBezeichnung(backupRechnungsposition.getBezeichnung());
                        rechnungsposition.setBezeichnunglang(backupRechnungsposition.getBezeichnunglang());
                        rechnungsposition.setMengeneinheit(backupRechnungsposition.getMengeneinheit());
                        rechnungsposition.setStueckpreis(backupRechnungsposition.getStueckpreis());
                        rechnungsposition.setRechnung(rechnung);
                        rechnung = rechnungDeltaspikeFacade.save(rechnung);
                        rechnungsposition = rechnungspositionDeltaspikeFacade.save(rechnungsposition);
                    }

                    for (BackupAufwand backupAufwand : backupRechnung.getAufwands()) {
                        Aufwand aufwand = new Aufwand();
                        aufwand.setBezeichnung(backupAufwand.getBezeichnung());
                        aufwand.setTitel(backupAufwand.getTitel());
                        aufwand.setStart(backupAufwand.getStart());
                        aufwand.setEnd(backupAufwand.getEnd());
                        aufwand.setRechnung(rechnung);
                        rechnung = rechnungDeltaspikeFacade.save(rechnung);
                        aufwand = aufwandDeltaspikeFacade.save(aufwand);
                    }

                }

            }

            Notification.show(backupAdressen.getAdressen().size() + " Adressen neu erstellt", Notification.Type.HUMANIZED_MESSAGE);
        } catch (JAXBException e) {
            Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Notification.show("Adressen Upload succeeded:" + succeededEvent.getLength(), Notification.Type.TRAY_NOTIFICATION);
    }

    @PostConstruct
    private void init() {
    }
}
