package com.gmail.michzuerch.anouman.presentation.ui.util.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageValidator implements Validator<byte[]> {
    @Override
    public ValidationResult apply(byte[] value, ValueContext context) {
        try {
            if (ImageIO.read(new ByteArrayInputStream(value)) == null) {
                return ValidationResult.error("Kein gültiges Bild");
            } else {
                return ValidationResult.ok();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ValidationResult.error("Keine gültige Bilddatei, nur JPG oder PNG erlaubt");
    }
}
