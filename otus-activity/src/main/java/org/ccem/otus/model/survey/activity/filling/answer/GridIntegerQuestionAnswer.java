package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.List;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class GridIntegerQuestionAnswer  extends AnswerFill {
	
	private List<List<GridIntegerAnswer>> value;

	public List<List<GridIntegerAnswer>> getValue() {
		return value;
	}
	
}
