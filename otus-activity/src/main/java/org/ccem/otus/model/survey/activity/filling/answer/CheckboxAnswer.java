package org.ccem.otus.model.survey.activity.filling.answer;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CheckboxAnswer extends AnswerFill {

	private List<CheckboxAnswerOption> value;

	public List<CheckboxAnswerOption> getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getAnswerExtract(String questionID) {
		Map<String, Object> extraction = new LinkedHashMap<String, Object>();
		if (this.value != null) {
			for (CheckboxAnswerOption answer : value) {
				extraction.put(answer.getOption(), answer.getState());
			}
		}
		return extraction;
	}
}