package com.company.cla.exception;

public class TicketMappedWithAudienceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TicketMappedWithAudienceException() {
		super();
	}

	public TicketMappedWithAudienceException(String message) {
		super(message);
	}
}
