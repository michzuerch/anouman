package ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver;

import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungFacade;
import ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen.BackupTemplateBuchhaltung;
import ch.internettechnik.anouman.presentation.ui.backup.xml.templatebuchhaltungen.BackupTemplateBuchhaltungen;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class TemplateBuchhaltungenUploadReceiver implements Upload.Receiver, Upload.SucceededListener {

    private static final Logger LOGGER = Logger.getLogger(TemplateBuchhaltungenUploadReceiver.class);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @Inject
    TemplateBuchhaltungFacade templateBuchhaltungFacade;

    @Override
    public OutputStream receiveUpload(String s, String s1) {
        return outputStream;
    }

    //@todo Problem mit outputStream in unmarshal
    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(BackupTemplateBuchhaltungen.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                BackupTemplateBuchhaltungen backupTemplateBuchhaltungen =
                        (BackupTemplateBuchhaltungen) unmarshaller.unmarshal(new ByteArrayInputStream(outputStream.toByteArray()));

                for (BackupTemplateBuchhaltung backupTemplateBuchhaltung : backupTemplateBuchhaltungen.getBuchhaltungen()) {
                    /*
                    templateBuchhaltungFacade.save(backupTemplateBuchhaltung);
                    for (Rechnung r : a.getRechnungen()) {
                        rechnungFacade.save(r);
                        for (Rechnungsposition rp : r.getRechnungspositionen()) {
                            rechnungspositionFacade.save(rp);
                        }
                        for (Aufwand aw : r.getAufwands()) {
                            aufwandFacade.save(aw);
                        }
                    }
                    */
                }
                Notification.show(backupTemplateBuchhaltungen.getBuchhaltungen().size() + " Template Buchhaltungen neu erstellt", Notification.Type.HUMANIZED_MESSAGE);
            } catch (JAXBException e) {
                Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            }
        Notification.show("Template Buchhaltungen Upload succeeded:" + succeededEvent.getLength(), Notification.Type.TRAY_NOTIFICATION);
    }
}

