package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class FileUploadAnswer extends AnswerFill {

	private List<FileAnswer> value;

	public List<FileAnswer> getValue() {
		return value;
	}

	@Override
	public Map<Object, Object> getAnswerExtract(String questionID) {
		Map<Object, Object> extraction = new HashMap<Object, Object>();
		for (FileAnswer fileAnswer : value) {
			extraction.put(questionID, fileAnswer.getName());
		}
		return extraction;
	}

}
