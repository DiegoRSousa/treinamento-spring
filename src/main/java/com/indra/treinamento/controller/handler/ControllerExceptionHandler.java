package com.indra.treinamento.controller.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<StandardError> objectNotFound(ResponseStatusException e, HttpServletRequest request) {
		var error = new StandardError(System.currentTimeMillis(), "Not found", 
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		var error = new ValidationError(System.currentTimeMillis(), "Validation Error", 
				e.getMessage(), request.getRequestURI());
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			error.addError(f.getField(), f.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
