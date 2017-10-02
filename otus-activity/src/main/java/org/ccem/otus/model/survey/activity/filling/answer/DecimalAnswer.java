package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.HashMap;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class DecimalAnswer extends AnswerFill {

	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public Map<Object, Object> getAnswerExtract(String questionID) {
		Map<Object, Object> extraction = new HashMap<Object, Object>();
		extraction.put(questionID, this.value);
		return extraction;
	}
}
