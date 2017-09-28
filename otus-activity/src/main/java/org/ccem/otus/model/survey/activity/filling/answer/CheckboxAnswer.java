package org.ccem.otus.model.survey.activity.filling.answer;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

import java.util.ArrayList;
import java.util.List;

public class CheckboxAnswer extends AnswerFill{

	private List<CheckboxAnswerOption> value;

	public List<CheckboxAnswerOption> getValue() {
		return value;
	}

	@Override
	public List<Object> extract(){
		List<Object> extractableAnswer = new ArrayList<>();
		extractableAnswer.add("checkbox");
		return extractableAnswer;
	}
}
