package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

public class ReportJasperValidator implements Validator<byte[]> {

    @Override
    public ValidationResult apply(byte[] bytes, ValueContext valueContext) {
        if (bytes.length >= 6) {
            return ValidationResult.ok();
        } else {
            return ValidationResult.error(
                    "Must be longer than six characters long");
        }
    }
}
