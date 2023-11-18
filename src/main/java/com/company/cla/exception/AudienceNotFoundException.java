package com.company.cla.exception;

public class AudienceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public AudienceNotFoundException() {
		super();
	}
	public AudienceNotFoundException(String message) {
		super(message);
	}
}
