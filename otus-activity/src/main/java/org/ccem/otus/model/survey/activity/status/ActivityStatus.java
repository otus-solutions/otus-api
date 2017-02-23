package org.ccem.otus.model.survey.activity.status;

import java.time.LocalDateTime;

import org.ccem.otus.model.survey.activity.User;

public class ActivityStatus {

	private String objectType;
	// TODO: Usar enum para os tipos de status, atributo name.
	private String name;
	private LocalDateTime date;
	private User user;

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

}
