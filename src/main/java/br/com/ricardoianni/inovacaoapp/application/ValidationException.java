package br.com.ricardoianni.inovacaoapp.application;

@SuppressWarnings("serial")
public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}
}
