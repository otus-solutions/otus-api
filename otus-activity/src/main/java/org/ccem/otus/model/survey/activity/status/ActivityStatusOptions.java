package org.ccem.otus.model.survey.activity.status;

import com.google.gson.annotations.SerializedName;

public enum ActivityStatusOptions {
	// TODO: 04/10/17 test serialize 
	CREATED("CREATED"),
	INITIALIZED_OFFLINE("INITIALIZED_OFFLINE"),
	INITIALIZED_ONLINE("INITIALIZED_ONLINE"),
	OPENED("OPENED"),
	SAVED("SAVED"),
	FINALIZED("FINALIZED");

	private String name;

	public String getName() {
		return name;
	}

	ActivityStatusOptions(String name) {
		this.name = name;
	}
}





