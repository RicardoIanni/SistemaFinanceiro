package br.com.ricardoianni.inovacaoapp.infrasctructure.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.ricardoianni.inovacaoapp.utils.StringUtils;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {

	@Override
	public Double convert(String source) {
		
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		
		source = source.replace(",", ".").trim();
		return Double.valueOf(source);
	}

}