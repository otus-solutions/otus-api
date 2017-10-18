package org.ccem.otus.model.survey.activity.status;

import org.ccem.otus.model.survey.activity.User;

import java.time.LocalDateTime;

public class ActivityStatus {

	private String objectType;
	private ActivityStatusOptions name;
	private LocalDateTime date;
	private User user;

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name.name();
	}

	public LocalDateTime getDate() {
		return date;
	}

	public User getUser() {
		if (user != null) return user;
		throw new UserNotFoundException();
	}
}
