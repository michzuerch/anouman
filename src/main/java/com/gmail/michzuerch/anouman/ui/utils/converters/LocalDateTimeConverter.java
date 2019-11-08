package com.gmail.michzuerch.anouman.ui.utils.converters;

import static com.gmail.michzuerch.anouman.ui.dataproviders.DataProviderUtil.convertIfNotNull;
import static com.gmail.michzuerch.anouman.ui.utils.FormattingUtils.FULL_DATE_FORMATTER;

import java.time.LocalDateTime;

import com.gmail.michzuerch.anouman.ui.dataproviders.DataProviderUtil;
import com.vaadin.flow.templatemodel.ModelEncoder;

public class LocalDateTimeConverter implements ModelEncoder<LocalDateTime, String> {


	private static final long serialVersionUID = 1L;
	private static final LocalTimeConverter TIME_FORMATTER = new LocalTimeConverter();

	@Override
	public String encode(LocalDateTime modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue,
				v -> FULL_DATE_FORMATTER.format(v) + " " + TIME_FORMATTER.encode(v.toLocalTime()));
	}

	@Override
	public LocalDateTime decode(String presentationValue) {
		throw new UnsupportedOperationException();
	}
}
