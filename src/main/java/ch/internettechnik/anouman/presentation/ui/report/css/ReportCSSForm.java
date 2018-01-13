package ch.internettechnik.anouman.presentation.ui.report.css;

import ch.internettechnik.anouman.backend.entity.report.css.ReportCSS;
import ch.internettechnik.anouman.presentation.ui.converter.ByteToStringConverter;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;
import server.droporchoose.UploadComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReportCSSForm extends AbstractForm<ReportCSS> {
    TextField bezeichnung = new TextField("Bezeichnung");
    UploadComponent uploadCSS = new UploadComponent();
    UploadComponent uploadHtml = new UploadComponent();
    TextArea css = new TextArea("CSS");
    TextArea html = new TextArea("HTML");

    private String filename;

    public ReportCSSForm() {
        super(ReportCSS.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report CSS");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        getBinder().forField(css).withConverter(new ByteToStringConverter()).bind("css");
        getBinder().forField(html).withConverter(new ByteToStringConverter()).bind("html");

        css.setWidth(700, Unit.PIXELS);
        css.setRows(10);

        html.setWidth(700, Unit.PIXELS);
        html.setRows(10);

        uploadCSS.setCaption("Upload CSS");
        uploadCSS.setReceivedCallback(this::uploadReceivedCSS);

        uploadHtml.setCaption("Upload Html");
        uploadHtml.setReceivedCallback(this::uploadReceivedHtml);

        return new VerticalLayout(new FormLayout(
                bezeichnung, css, uploadCSS, html, uploadHtml), getToolbar());
    }

    private void uploadReceivedCSS(String fileName, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            getEntity().setCss(data);
            css.setValue(new String(data, "UTF-8"));
        } catch (IOException e) {
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void uploadReceivedHtml(String fileName, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            getEntity().setHtml(data);
            html.setValue(new String(data, "UTF-8"));
        } catch (IOException e) {
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


}
