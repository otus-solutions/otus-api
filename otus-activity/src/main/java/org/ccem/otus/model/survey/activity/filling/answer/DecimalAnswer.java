package org.ccem.otus.model.survey.activity.filling.answer;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class DecimalAnswer extends AnswerFill {

	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
