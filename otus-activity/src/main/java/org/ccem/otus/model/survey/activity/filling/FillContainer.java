package org.ccem.otus.model.survey.activity.filling;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public class FillContainer {
	
	private List<QuestionFill> fillingList;

	public List<QuestionFill> getFillingList() {
		return fillingList;
	}

	public QuestionFill getQuestionFill(String templateID) throws DataNotFoundException {
		final Optional<QuestionFill> first = fillingList.stream().filter(questionFill -> questionFill.getQuestionID().equals(templateID)).findFirst();
		return first.orElseThrow(() -> new DataNotFoundException());
	}
}
