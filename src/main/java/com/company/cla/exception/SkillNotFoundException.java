package com.company.cla.exception;

public class SkillNotFoundException extends RuntimeException {

	public SkillNotFoundException() {
		super();
	}

	public SkillNotFoundException(String message) {
		super(message);
	}
}
