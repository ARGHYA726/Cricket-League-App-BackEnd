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
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrganiserNotFoundException extends RuntimeException {
	public OrganiserNotFoundException(String message) {
		super(message);
	}

}
