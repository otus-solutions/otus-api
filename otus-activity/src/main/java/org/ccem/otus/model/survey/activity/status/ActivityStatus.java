package org.ccem.otus.model.survey.activity.status;

import java.util.Date;

import org.ccem.otus.model.survey.activity.User;

public class ActivityStatus {

	private String objectType;
	// TODO: Usar enum para os tipos de status, atributo name.
	private String name;
	private Date date;
	private User user;

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

}
