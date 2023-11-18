package com.company.cla.exception;

public class OwnerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OwnerNotFoundException() {
		super();
	}

	public OwnerNotFoundException(String message) {
		super(message);
	}

}
