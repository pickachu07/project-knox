package com.srs.knox.utils;

import org.springframework.http.HttpStatus;

public class OWException extends Exception {
	private HttpStatus status;
	private String message;
	
	public OWException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
