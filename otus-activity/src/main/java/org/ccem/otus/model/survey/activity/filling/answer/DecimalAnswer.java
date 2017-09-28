package org.ccem.otus.model.survey.activity.filling.answer;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

import java.util.ArrayList;
import java.util.List;

public class DecimalAnswer extends AnswerFill {

	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public List<Object> extract(){
		List<Object> extractableAnswer = new ArrayList<>();
		extractableAnswer.add(1);
		return extractableAnswer;
	}
}
