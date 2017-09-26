package ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver;

import ch.internettechnik.anouman.backend.session.jpa.api.AdresseService;
import ch.internettechnik.anouman.backend.session.jpa.api.AufwandService;
import ch.internettechnik.anouman.backend.session.jpa.api.RechnungService;
import ch.internettechnik.anouman.backend.session.jpa.api.RechnungspositionService;
import ch.internettechnik.anouman.presentation.ui.backup.BackupView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class BuchhaltungenUploadReceiver implements Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BackupView.class.getName());

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @Inject
    AdresseService adresseService;

    @Inject
    RechnungService rechnungService;

    @Inject
    RechnungspositionService rechnungspositionService;

    @Inject
    AufwandService aufwandService;

    @Override
    public OutputStream receiveUpload(String s, String s1) {
        return outputStream;
    }

    //@todo Problem mit outputStream in unmarshal
    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        JAXBContext jaxbContext = null;
            /*
            try {
                jaxbContext = JAXBContext.newInstance(BackupAdressen.class, Adresse.class, Rechnung.class,
                        Rechnungsposition.class, Aufwand.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                BackupAdressen backupAdressen = (BackupAdressen) unmarshaller.unmarshal(IOUtils.toInputStream(outputStream.toByteArray()));
                for (Adresse a : backupAdressen.getAdressen()) {
                    adresseFacade.saveOrPersist(a);
                    for (Rechnung r : a.getRechnungen()) {
                        rechnungFacade.saveOrPersist(r);
                        for (Rechnungsposition rp : r.getRechnungspositionen()) {
                            rechnungspositionFacade.saveOrPersist(rp);
                        }
                        for (Aufwand aw : r.getAufwands()) {
                            aufwandFacade.saveOrPersist(aw);
                        }
                    }
                }
                Notification.show(backupAdressen.getAdressen().size() + " Adressen neu erstellt", Notification.Type.HUMANIZED_MESSAGE);
            } catch (JAXBException e) {
                Notification.show("Fehler:" + e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            }
            */
        Notification.show("Buchhaltungen Upload succeeded:" + succeededEvent.getLength(), Notification.Type.TRAY_NOTIFICATION);
    }
}

