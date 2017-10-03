package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class GridIntegerQuestionAnswer extends AnswerFill {

	private List<List<GridIntegerAnswer>> value;

	public List<List<GridIntegerAnswer>> getValue() {
		return value;
	}

	@Override
	public Map<String, Object> getAnswerExtract(String questionID) {
		Map<String, Object> extraction = new LinkedHashMap<String, Object>();
		for (List<GridIntegerAnswer> list : value) {
			for (GridIntegerAnswer gridIntegerAnswer : list) {
				extraction.put(gridIntegerAnswer.getCustomID(), gridIntegerAnswer.getValue());
			}
		}
		return extraction;
	}

}
