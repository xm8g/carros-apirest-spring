package com.carros.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler({
		EmptyResultDataAccessException.class,
		InvalidDataAccessResourceUsageException.class
	})
	public ResponseEntity errorNotFound(Exception e) {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler({
		IllegalArgumentException.class
	})
	public ResponseEntity errorBadRequest(Exception e) {
		return ResponseEntity.badRequest().build();
	}
	
	@ExceptionHandler({
		AccessDeniedException.class
	})
	public ResponseEntity accessDenied(Exception e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso Negado"));
	}
}

class Error {
	public String error;
	
	public Error(String error) {
		this.error = error;
	}
}
