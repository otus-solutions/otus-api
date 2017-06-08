package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.List;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class GridTextQuestionAnswer extends AnswerFill {
	
	private List<List<GridTextAnswer>> value;

	public List<List<GridTextAnswer>> getValue() {
		return value;
	}

}
