package com.company.cla.exception;

public class OwnerLowBudgetException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	public OwnerLowBudgetException() {
		super();
	}

	public OwnerLowBudgetException(String message) {
		super(message);
	}

}
