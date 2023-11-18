package com.company.cla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.company.cla.exception.TeamNotFoundException;

public class TeamExceptionsController {
	@ExceptionHandler(value = TeamNotFoundException.class)
	public ResponseEntity<Object> exception(TeamNotFoundException ex) {
		return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);
	}
}
