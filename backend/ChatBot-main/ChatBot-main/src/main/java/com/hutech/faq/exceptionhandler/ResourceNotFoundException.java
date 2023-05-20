package com.hutech.faq.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	private final int status;
	
	

	public ResourceNotFoundException(String message, int status) {
		super(message);
		this.status = status;
		
	}

	public int getStatus() {
		return status;
	}
	
	
	
	

}
