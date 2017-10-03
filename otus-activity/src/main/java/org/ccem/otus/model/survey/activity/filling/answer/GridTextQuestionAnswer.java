package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class GridTextQuestionAnswer extends AnswerFill {

	private List<List<GridTextAnswer>> value;

	public List<List<GridTextAnswer>> getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getAnswerExtract(String questionID) {
		Map<String, Object> extraction = new LinkedHashMap<String, Object>();
		for (List<GridTextAnswer> list : value) {
			for (GridTextAnswer gridTextAnswer : list) {
				extraction.put(gridTextAnswer.getGridText(), gridTextAnswer.getValue());
			}
		}
		return extraction;
	}

}
