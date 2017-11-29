package org.ccem.otus.model.survey.activity.status;

public class UserNotFoundException extends RuntimeException {
	private static String MESSAGE = "ActivityStatus without user";

	public UserNotFoundException() {
		super(MESSAGE);
	}

	public UserNotFoundException(Throwable e) {
		super(MESSAGE, e);
	}
}
