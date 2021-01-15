package br.com.ricardoianni.inovacaoapp.infrasctructure.web.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;

@Component
public class BigDecimalToStringConverter implements Converter<BigDecimal, String> {

	@Override
	public String convert(BigDecimal source) {
		return FormatUtils.formatNumber(source);
	}

}