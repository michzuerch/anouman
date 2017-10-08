package ch.internettechnik.anouman.presentation.ui.reporttemplate;

import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import ch.internettechnik.anouman.presentation.ui.backup.uploadreceiver.TemplateBuchhaltungenUploadReceiver;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@ViewScoped
public class ReportTemplateForm extends AbstractForm<ReportTemplate> implements Upload.Receiver, Upload.SucceededListener {
    private static Logger logger = LoggerFactory.getLogger(ReportTemplateForm.class.getName());

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    TextField bezeichnung = new MTextField("Bezeichnung");
    TemplateTextField template = new TemplateTextField();
    TextField filename = new MTextField("Dateiname");
    Upload upload = new Upload("Report Template hochladen", new TemplateBuchhaltungenUploadReceiver());
    Button download = new DownloadButton(stream -> {
        try {
            stream.write(getBinder().getBean().getTemplate());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    public ReportTemplateForm() {
        super(ReportTemplate.class);
    }


    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Template");
        openInModalPopup.setWidth("800px");
        openInModalPopup.setHeight("800px");
        if (getBinder().getBean() != null) download.setEnabled(true);
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        upload.addSucceededListener(this);
        download.setIcon(VaadinIcons.DOWNLOAD);
        download.setCaption("Download");
        if (getBinder().getBean() == null) download.setEnabled(false);

        template.setCaption("Report Template");
        template.setWidth("600px");
        template.setSizeFull();
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new FormLayout(bezeichnung, upload, download, filename));
        layout.addComponentsAndExpand(template);
        layout.addComponent(getToolbar());

        return layout;
    }

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {
        filename.setValue(fileName);
        return outputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        template.setValue(outputStream.toByteArray());
        download.setEnabled(true);
        getEntity().setTemplate(outputStream.toByteArray());
    }
}
