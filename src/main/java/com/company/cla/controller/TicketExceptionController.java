package com.company.cla.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.cla.exception.TicketMappedWithAudienceException;
import com.company.cla.exception.TicketNotFoundException;

@ControllerAdvice
public class TicketExceptionController {
	@ExceptionHandler(value = TicketNotFoundException.class)
	public ResponseEntity<Object> exception(TicketNotFoundException ex) {
		return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = TicketMappedWithAudienceException.class)
	public ResponseEntity<Object> exception(TicketMappedWithAudienceException ex) {
		return new ResponseEntity<>("Ticket has been Mapped With Audience", HttpStatus.METHOD_NOT_ALLOWED);
	}
}
