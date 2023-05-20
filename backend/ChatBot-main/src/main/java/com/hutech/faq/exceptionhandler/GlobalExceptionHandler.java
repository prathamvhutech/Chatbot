package com.hutech.faq.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException tenantNameException,
			WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(new Date(), tenantNameException.getMessage(),
				request.getDescription(false), tenantNameException.getStatus());
		return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
	}
   
}