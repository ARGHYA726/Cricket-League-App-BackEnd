package com.company.cla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GroundNotFoundException extends RuntimeException {

	public GroundNotFoundException(String message) {
		super(message);
	}

}
