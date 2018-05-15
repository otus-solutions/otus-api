package org.ccem.otus.model.survey.activity.filling;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnswerFill {

	private String objectType;
	private String type;

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getAnswerExtract(String questionID) {
		return new LinkedHashMap<>();
	}

}
