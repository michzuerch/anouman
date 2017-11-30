package ch.internettechnik.anouman.presentation.ui.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import java.nio.charset.StandardCharsets;

public class ByteToStringConverter implements Converter<String, byte[]> {
    @Override
    public Result<byte[]> convertToModel(String s, ValueContext valueContext) {
        return Result.ok(s.getBytes());
        //return Result.error("Please enter a number");
    }

    @Override
    public String convertToPresentation(byte[] bytes, ValueContext valueContext) {
        if (bytes != null) return new String(bytes, StandardCharsets.UTF_8);
        return "";
    }
}
