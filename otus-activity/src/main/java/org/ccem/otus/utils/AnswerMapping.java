package org.ccem.otus.utils;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.*;

public enum AnswerMapping {
	
	CALENDAR_QUESTION(ImmutableDateAnswer.class, "CalendarQuestion"),
	INTEGER_QUESTION(IntegerAnswer.class, "IntegerQuestion"),
	DECIMAL_QUESTION(DecimalAnswer.class, "DecimalQuestion"),
	SINGLE_SELECTION_QUESTION(TextAnswer.class, "SingleSelectionQuestion"),
	CHECKBOX_QUESTION(CheckboxAnswer.class, "CheckboxQuestion"),
	TEXT_QUESTION(TextAnswer.class, "TextQuestion"),
	EMAIL_QUESTION(TextAnswer.class, "EmailQuestion"),
	TIME_QUESTION(ImmutableDateAnswer.class, "TimeQuestion"),
	FILE_UPLOAD_QUESTION(FileUploadAnswer.class, "FileUploadQuestion"),
	AUTOCOMPLETE_QUESTION(TextAnswer.class, "AutocompleteQuestion"),
	PHONE_QUESTION(TextAnswer.class, "PhoneQuestion");
	
	private Class<? extends AnswerFill> answerType;
	private String questionType;
	
	private AnswerMapping(Class<? extends AnswerFill> answerType, String questionType) {
		this.answerType = answerType;
		this.questionType = questionType;
	}
	
	public Class<? extends AnswerFill> getAnswerClass() {
		return answerType;
	}

	public String getQuestionType() {
		return questionType;
	}
	
	public static AnswerMapping getEnumByObjectType(String questionType) {
		AnswerMapping aux = null;
		
		for (AnswerMapping answerMapping : values()) {
			if(answerMapping.getQuestionType().equals(questionType)) {
				aux = answerMapping;
			}
		}
		
		if(aux == null) {
			throw new RuntimeException("Error: " + questionType + " was not found at AnswerMapping.");
		};
		
		return aux;
	}

}
