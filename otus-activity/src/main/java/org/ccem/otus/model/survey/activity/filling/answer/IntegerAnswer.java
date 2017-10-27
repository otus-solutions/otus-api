package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class IntegerAnswer extends AnswerFill {

	private Integer value;

	public Integer getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getAnswerExtract(String questionID) {
		Map<String, Object> extraction = new LinkedHashMap<String, Object>();
		extraction.put(questionID, this.value);
		return extraction;
	}

}
