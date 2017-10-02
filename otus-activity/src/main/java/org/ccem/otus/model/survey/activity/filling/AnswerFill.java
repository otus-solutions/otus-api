package org.ccem.otus.model.survey.activity.filling;

import java.util.HashMap;
import java.util.Map;

public class AnswerFill {

	private String objectType;
	private String type;

	public String getObjectType() {
		return objectType;
	}

	public String getType() {
		return type;
	}

	public Map<Object, Object> getAnswerExtract(String questionID) {
		return new HashMap<Object, Object>();
	}

}
