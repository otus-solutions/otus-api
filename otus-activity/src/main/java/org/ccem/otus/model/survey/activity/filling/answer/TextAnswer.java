package org.ccem.otus.model.survey.activity.filling.answer;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.ExtractionFill;

import java.util.LinkedHashMap;
import java.util.Map;

public class TextAnswer extends AnswerFill {

	private String value;

	public String getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getAnswerExtract(String questionID) {
		Map<String, Object> extraction = new LinkedHashMap<>();
		extraction.put(questionID, ExtractionFill.escapeText(this.value));
		return extraction;
	}


}
