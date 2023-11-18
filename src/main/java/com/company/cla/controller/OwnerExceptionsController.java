package com.company.cla.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.cla.exception.OwnerLowBudgetException;
import com.company.cla.exception.OwnerNotFoundException;

@ControllerAdvice
public class OwnerExceptionsController {
	@ExceptionHandler(value = OwnerNotFoundException.class)
	public ResponseEntity<Object> exception(OwnerNotFoundException ex) {
		return new ResponseEntity<>("Owner not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = OwnerLowBudgetException.class)
	public ResponseEntity<Object> exception(OwnerLowBudgetException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

}