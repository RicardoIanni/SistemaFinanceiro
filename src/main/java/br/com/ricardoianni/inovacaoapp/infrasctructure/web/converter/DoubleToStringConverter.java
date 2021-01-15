package br.com.ricardoianni.inovacaoapp.infrasctructure.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.ricardoianni.inovacaoapp.utils.FormatUtils;

@Component
public class DoubleToStringConverter implements Converter<Double, String> {

	@Override
	public String convert(Double source) {
		return FormatUtils.formatNumber(source);
	}

}