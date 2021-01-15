package br.com.ricardoianni.inovacaoapp.utils;

import java.util.Collection;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}
		
		return str.trim().length() == 0;
	}
	
	public static String encrypt(String rawString) {
		if (isEmpty(rawString)) {
			return null;
		}
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder.encode(rawString);
	}
	
	public static String concatenate(Collection<String> strings) {
		if (strings == null || strings.size() == 0) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		String delimiter = ", ";
		boolean first = true;
		
		for (String string : strings) {
			if (!first) {
				sb.append(delimiter);
			}
			
			sb.append(string);
			first = false;
		}
		
		return sb.toString();
	}
	
	public static String replaceString(String string, Character target, Character replace) {
		if (string.isBlank() || string.isEmpty()) {
			return string;
		}
		
		if (replace != null) {
			return string.replace(target, replace);
		}
		
		while (string.indexOf(target) == 0)  {
			string = string.substring(1);
		}
		
		while (string.indexOf(target) > 0) {
			string = string.substring(0, string.indexOf(target)) + string.substring(string.indexOf(target)+1);
		}
		
		return string;
	}
}