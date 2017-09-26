package ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.session.jpa.api.AdresseService;
import ch.internettechnik.anouman.backend.session.jpa.api.AufwandService;
import ch.internettechnik.anouman.backend.session.jpa.api.RechnungService;
import ch.internettechnik.anouman.backend.session.jpa.api.RechnungspositionService;
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

public class AdressenUploadReceiver implements Serializable, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    File tempFile;

    @Inject
    AdresseService adresseService;

    @Inject
    RechnungService rechnungService;

    @Inject
    RechnungspositionService rechnungspositionService;

    @Inject
    AufwandService aufwandService;


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
            jaxbContext = JAXBContext.newInstance(BackupAdressen.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            BackupAdressen backupAdressen =
                    (BackupAdressen) unmarshaller.unmarshal(new FileInputStream(tempFile));

            for (Adresse adresse : backupAdressen.getAdressen()) {

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
