package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.VerticalLayout;

public class TestTextField extends CustomField<String> {

    private com.vaadin.ui.TextField textField = new com.vaadin.ui.TextField();
    private String textValue = new String();

    public TestTextField(String caption) {
        super();
        setCaption(caption);
    }

    @Override
    protected Component initContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponents(textField);
        textField.addValueChangeListener(
                event -> {

                    fireEvent(new ValueChangeEvent<>(this,
                            event.getValue(), event.isUserOriginated()));
                    System.err.println("VcE:" + event.getValue());
//                   textValue=event.getValue();
                    this.textValue = event.getValue();
                }
        );
        return layout;
    }

//    @Override
//    public String getEmptyValue() {
//        System.err.println("getEmptyValue()");
//        return new String();
//    }

    @Override
    protected void doSetValue(String value) {
        System.err.println("doSetValue:" + value);
        this.textValue = value;
        textField.setValue(value);

        //@todo Event für Upload ReportJasper jrxml??
        //fireEvent(new ValueChangeEvent<> (textField, textField.getValue(),false));
        //textField.setValue(value);
        //textField.markAsDirty();
    }

    @Override
    public String getValue() {
        this.textValue = textField.getValue();
        System.err.println("getValue:" + this.textValue);
        return this.textValue;
    }
}
