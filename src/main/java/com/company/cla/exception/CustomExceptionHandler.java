package com.company.cla.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = PlayerNotFoundException.class)
	public ResponseEntity<Object> exception(PlayerNotFoundException ex) {
		log.warn(ex.getMessage());
		return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = SkillNotFoundException.class)
	public ResponseEntity<Object> exception(SkillNotFoundException ex) {
		log.warn(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
