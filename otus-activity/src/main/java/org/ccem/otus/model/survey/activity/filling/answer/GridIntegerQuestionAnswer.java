package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class GridIntegerQuestionAnswer extends AnswerFill {

	private List<List<GridIntegerAnswer>> value;

	public List<List<GridIntegerAnswer>> getValue() {
		return value;
	}

	@Override
	public Map<Object, Object> getAnswerExtract(String questionID) {
		Map<Object, Object> extraction = new HashMap<Object, Object>();
		for (List<GridIntegerAnswer> list : value) {
			for (GridIntegerAnswer gridIntegerAnswer : list) {
				extraction.put(gridIntegerAnswer.getCustomID(), gridIntegerAnswer.getValue());
			}
		}
		return extraction;
	}

}
