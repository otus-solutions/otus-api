package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class GridTextQuestionAnswer extends AnswerFill {

	private List<List<GridTextAnswer>> value;

	public List<List<GridTextAnswer>> getValue() {
		return value;
	}

	@Override
	public Map<Object, Object> getAnswerExtract(String questionID) {
		Map<Object, Object> extraction = new HashMap<Object, Object>();
		for (List<GridTextAnswer> list : value) {
			for (GridTextAnswer gridTextAnswer : list) {
				extraction.put(gridTextAnswer.getGridText(), gridTextAnswer.getValue());
			}
		}
		return extraction;
	}

}
