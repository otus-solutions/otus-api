package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.service.extraction.AnswerExtraction;

public class DecimalAnswer extends AnswerFill implements AnswerExtraction {

	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public List<Object> extract() {
		List<Object> extractableAnswer = new ArrayList<>();
		extractableAnswer.add(value);
		return extractableAnswer;
	}
}
