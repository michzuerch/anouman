package ch.internettechnik.anouman.presentation.ui.reporttemplate;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.TextArea;

import java.nio.charset.Charset;

// @todo Problem bei Update und Save-Button, vergleichen mit TextField, recherche im Internetz

public class TemplateTextField extends CustomField<byte[]> {
    private TextArea textArea = new TextArea("Template Code");

    @Override
    protected Component initContent() {
        textArea.setRows(18);
        textArea.setSizeFull();
        return textArea;
    }

    @Override
    protected void doSetValue(byte[] bytes) {
        if (bytes != null) {
            textArea.setValue(new String(bytes, Charset.forName("UTF-8")));
        } else {
            textArea.setValue(new String());
        }
    }

    @Override
    public byte[] getValue() {
        return textArea.getValue().getBytes();
    }

    @Override
    public void setValue(byte[] value) {
        doSetValue(value);
    }


}
