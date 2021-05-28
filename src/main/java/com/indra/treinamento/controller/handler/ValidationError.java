package com.indra.treinamento.controller.handler;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, String error, String message, String path) {
		super(timestamp, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
