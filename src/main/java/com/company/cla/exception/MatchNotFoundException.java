package com.company.cla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Match Not Found")
public class MatchNotFoundException extends RuntimeException {

	public MatchNotFoundException(String message) {
		super(message);
	}
}