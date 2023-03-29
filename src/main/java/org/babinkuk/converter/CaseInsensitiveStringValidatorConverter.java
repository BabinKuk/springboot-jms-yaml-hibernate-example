package org.babinkuk.converter;

import org.babinkuk.validator.ValidatorType;
import org.springframework.core.convert.converter.Converter;

public class CaseInsensitiveStringValidatorConverter implements Converter<String, ValidatorType> {

	@Override
	public ValidatorType convert(String source) {
		try {
			return ValidatorType.valueOfIgnoreCase(source);
		} catch (Exception e) {
			return null;
		}
	}

}
