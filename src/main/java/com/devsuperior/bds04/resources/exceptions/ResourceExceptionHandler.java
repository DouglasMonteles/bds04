package com.devsuperior.bds04.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> methodArgumentNotValidException(
				MethodArgumentNotValidException e, HttpServletRequest request
			) {
		var error = new ValidationError();
		var status = HttpStatus.UNPROCESSABLE_ENTITY;
		
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Validation Exception");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		e.getBindingResult().getFieldErrors().forEach(err -> {
			error.addError(err.getField(), err.getDefaultMessage());
		});
		
		return ResponseEntity.status(status).body(error);
	}
	
}
