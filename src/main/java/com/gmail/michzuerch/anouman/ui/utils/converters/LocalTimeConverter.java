package com.gmail.michzuerch.anouman.ui.utils.converters;

import static com.gmail.michzuerch.anouman.ui.dataproviders.DataProviderUtil.convertIfNotNull;
import static com.gmail.michzuerch.anouman.ui.utils.FormattingUtils.HOUR_FORMATTER;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.gmail.michzuerch.anouman.ui.dataproviders.DataProviderUtil;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.templatemodel.ModelEncoder;

public class LocalTimeConverter implements ModelEncoder<LocalTime, String>, Converter<String, LocalTime> {

	private static final long serialVersionUID = 1L;

	@Override
	public String encode(LocalTime modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue, HOUR_FORMATTER::format);
	}

	@Override
	public LocalTime decode(String presentationValue) {
		return DataProviderUtil.convertIfNotNull(presentationValue, p -> LocalTime.parse(p, HOUR_FORMATTER));
	}

	@Override
	public Result<LocalTime> convertToModel(String value, ValueContext context) {
		try {
			return Result.ok(decode(value));
		} catch (DateTimeParseException e) {
			return Result.error("Invalid time");
		}
	}

	@Override
	public String convertToPresentation(LocalTime value, ValueContext context) {
		return encode(value);
	}

}
