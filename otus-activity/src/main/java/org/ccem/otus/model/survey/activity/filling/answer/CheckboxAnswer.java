package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.List;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class CheckboxAnswer extends AnswerFill {

	private List<CheckboxAnswerOption> value;

	public List<CheckboxAnswerOption> getValue() {
		return value;
	}

}
