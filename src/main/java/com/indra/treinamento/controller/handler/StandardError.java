package com.indra.treinamento.controller.handler;

public class StandardError {

	private Long timestamp;
	private String error;
	private String message;
	private String path;
	
	public StandardError(Long timestamp, String error, String message, String path) {
		this.timestamp = timestamp;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}
}
