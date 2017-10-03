package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class TextAnswer extends AnswerFill {

	private String value;

	public String getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getAnswerExtract(String questionID) {
		Map<String, Object> extraction = new LinkedHashMap<String, Object>();
		extraction.put(questionID, this.value);
		return extraction;
	}

}
