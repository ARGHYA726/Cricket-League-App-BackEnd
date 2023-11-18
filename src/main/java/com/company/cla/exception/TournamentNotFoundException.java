/**
 * 
 */
package com.company.cla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TournamentNotFoundException extends RuntimeException {
	public TournamentNotFoundException(String message) {
		super(message);
	}

}


