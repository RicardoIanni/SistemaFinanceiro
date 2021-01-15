package br.com.ricardoianni.inovacaoapp.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

	public static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
	
	public static NumberFormat newNumberFormat() {
		NumberFormat nf = NumberFormat.getNumberInstance(LOCALE_BRAZIL);
		
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
		
		return nf;
	}
	
	public static String formatNumber(BigDecimal number) {
		if (number == null) {
			return null;
		}
		
		return newNumberFormat().format(number);
		
	}
	
	public static String formatNumber(Double number) {
		if (number == null) {
			return null;
		}
		
		return newNumberFormat().format(number);
		
	}
	
}