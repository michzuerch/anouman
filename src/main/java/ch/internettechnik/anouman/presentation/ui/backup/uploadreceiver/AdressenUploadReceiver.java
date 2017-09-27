package ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AdresseFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AufwandFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungspositionFacade;
import ch.internettechnik.anouman.presentation.ui.backup.BackupView;
import ch.internettechnik.anouman.presentation.ui.backup.xml.adressen.BackupAdressen;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Date;

public class AdressenUploadReceiver implements Serializable, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    File tempFile;

    @Inject
    AdresseFacade adresseFacade;

    @Inject
    RechnungFacade rechnungFacade;

    @Inject
    RechnungspositionFacade rechnungspositionFacade;

    @Inject
    AufwandFacade aufwandFacade;


    public AdressenUploadReceiver() {
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

            for (Adresse adresse : backupAdressen.getAdressen()) {
                adresse = adresseFacade.save(adresse);
                for (Rechnung rechnung : adresse.getRechnungen()) {
                    System.err.println("Rechnung:" + rechnung);
                    rechnung.setAdresse(adresse);
                    adresse = adresseFacade.save(adresse);

                    rechnung.setFaelligInTagen(10);
                    rechnung.setBezahlt(true);
                    rechnung.setVerschickt(true);
                    rechnung.setBezeichnung("Manuell Ausgefüllt");
                    rechnung.setRechnungsdatum(new Date());

                    rechnung = rechnungFacade.save(rechnung);

                    for (Rechnungsposition rechnungsposition : rechnung.getRechnungspositionen()) {
                        rechnungsposition.setRechnung(rechnung);
                        rechnung = rechnungFacade.save(rechnung);
                        rechnungsposition = rechnungspositionFacade.save(rechnungsposition);
                    }

                    for (Aufwand aufwand : rechnung.getAufwands()) {
                        aufwand.setRechnung(rechnung);
                        rechnung = rechnungFacade.save(rechnung);
                        aufwand = aufwandFacade.save(aufwand);
                    }

                }

            }

            Notification.show(backupAdressen.getAdressen().size() + " Adressen neu erstellt", Notification.Type.HUMANIZED_MESSAGE);
        } catch (JAXBException e) {
            Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Notification.show("Adressen Upload succeeded:" + succeededEvent.getLength(), Notification.Type.TRAY_NOTIFICATION);
    }

    @PostConstruct
    private void init() {
    }
}
