package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.HashMap;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

public class ImmutableDateAnswer extends AnswerFill {

	private ImmutableDate value;

	public ImmutableDate getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getAnswerExtract(String questionID) {
		Map<String, Object> extraction = new HashMap<String, Object>();
		extraction.put(questionID, this.value.getValue());
		return extraction;
	}

}
