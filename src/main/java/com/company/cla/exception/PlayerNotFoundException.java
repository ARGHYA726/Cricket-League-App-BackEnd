package com.company.cla.exception;

public class PlayerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException() {
		super();
	}

	public PlayerNotFoundException(String message) {
		super(message);
	}

	public PlayerNotFoundException(Exception e) {
		super(e);
	}

}
