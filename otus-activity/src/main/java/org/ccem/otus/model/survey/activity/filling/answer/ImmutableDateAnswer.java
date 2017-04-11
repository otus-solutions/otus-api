package org.ccem.otus.model.survey.activity.filling.answer;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

public class ImmutableDateAnswer extends AnswerFill {

	private ImmutableDate value;

	public ImmutableDate getValue() {
		return value;
	}

}
