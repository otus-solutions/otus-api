package org.ccem.otus.model.survey.activity.status;

import org.ccem.otus.model.survey.activity.User;

import java.time.LocalDateTime;

public class ActivityStatus {

	private String objectType;
	// TODO: Usar enum para os tipos de status, atributo name.
	//		CREATED
	//		INITIALIZED_OFFLINE
	//		INITIALIZED_ONLINE
	//		OPENED
	//		SAVED
	//		FINALIZED
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
