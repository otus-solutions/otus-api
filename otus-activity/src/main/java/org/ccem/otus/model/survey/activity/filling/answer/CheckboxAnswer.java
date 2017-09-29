package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.service.extraction.AnswerExtraction;

public class CheckboxAnswer extends AnswerFill implements AnswerExtraction {

	private List<CheckboxAnswerOption> value;
	private List<Object> extractableAnswer;

	public List<CheckboxAnswerOption> getValue() {
		return value;
	}

	@Override
	public List<Object> extract() {
		extractableAnswer = new ArrayList<>();
		for (CheckboxAnswerOption answer : value) {
			extractableAnswer.add(answer.getState());
		}
		return extractableAnswer;
	}
}
