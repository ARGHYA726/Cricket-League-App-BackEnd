package com.company.cla.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.cla.exception.AudienceNotFoundException;
import com.company.cla.exception.TicketNotFoundException;

@ControllerAdvice
public class AudienceExceptionController {
	@ExceptionHandler(value = AudienceNotFoundException.class)
	public ResponseEntity<Object> exception(AudienceNotFoundException ex) {
		return new ResponseEntity<>("Audience not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = TicketNotFoundException.class)
	public ResponseEntity<Object> exception(TicketNotFoundException ex) {
		return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
	}
}
