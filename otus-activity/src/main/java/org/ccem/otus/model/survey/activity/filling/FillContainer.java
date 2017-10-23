package org.ccem.otus.model.survey.activity.filling;

import java.util.List;
import java.util.Optional;

public class FillContainer {
	
	private List<QuestionFill> fillingList;

	public List<QuestionFill> getFillingList() {
		return fillingList;
	}

	public Optional<QuestionFill> getQuestionFill(String templateID) {
		return fillingList.stream().filter(questionFill -> questionFill.getQuestionID().equals(templateID)).findAny();
	}
}
