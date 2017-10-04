package org.ccem.otus.model.survey.activity.status;

import com.google.gson.annotations.SerializedName;

public enum ActivityStatusOptions {
	@SerializedName("CREATED")
	CREATED,
	@SerializedName("INITIALIZED_OFFLINE")
	INITIALIZED_OFFLINE,
	@SerializedName("INITIALIZED_ONLINE")
	INITIALIZED_ONLINE,
	@SerializedName("OPENED")
	OPENED,
	@SerializedName("SAVED")
	SAVED,
	@SerializedName("FINALIZED")
	FINALIZED;

	private String name;

	// TODO: 04/10/17 does this work?
	public String getName() {
		return name;
	}
}





