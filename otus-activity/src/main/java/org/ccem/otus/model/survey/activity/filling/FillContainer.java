package org.ccem.otus.model.survey.activity.filling;

import java.util.List;
import java.util.Optional;

public class FillContainer {
	
	private List<QuestionFill> fillingList;

	public List<QuestionFill> getFillingList() {
		return fillingList;
	}

	public QuestionFill getQuestionFill(String templateID){
		final Optional<QuestionFill> first = fillingList.stream().filter(questionFill -> questionFill.getQuestionID().equals(templateID)).findFirst();
		if (first.isPresent()){
			return first.get();
		}
		else {
			// TODO: 11/10/17 throw
		}
		return null;
	}

}
