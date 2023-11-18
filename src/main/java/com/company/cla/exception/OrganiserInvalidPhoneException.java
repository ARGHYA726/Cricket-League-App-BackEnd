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
public class OrganiserInvalidPhoneException extends RuntimeException {
	public OrganiserInvalidPhoneException(String message) {
		super(message);
	}

}
