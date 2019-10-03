package org.ccem.otus.utils;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.model.survey.activity.filling.answer.CheckboxAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.DecimalAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.FileUploadAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.GridIntegerQuestionAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.GridTextQuestionAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.ImmutableDateAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.IntegerAnswer;
import org.ccem.otus.model.survey.activity.filling.answer.TextAnswer;

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
	PHONE_QUESTION(TextAnswer.class, "PhoneQuestion"),
	GRID_INTEGER_QUESTION(GridIntegerQuestionAnswer.class, "GridIntegerQuestion"),
	GRID_TEXT_QUESTION(GridTextQuestionAnswer.class, "GridTextQuestion");
		
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

	public static boolean isEquals(AnswerMapping answerMapping, String answertType) {
		return answerMapping.getQuestionType().equals(answertType);
	}

}
