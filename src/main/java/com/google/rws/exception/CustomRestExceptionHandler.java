package com.google.rws.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({ LoginException.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "error occurred");
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
