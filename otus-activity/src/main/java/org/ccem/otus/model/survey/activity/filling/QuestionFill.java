package org.ccem.otus.model.survey.activity.filling;

public class QuestionFill {

	private String objectType;
	private String questionID;
	private AnswerFill answer;
	private MetadataFill metadata;
	private String comment;

	public String getObjectType() {
		return objectType;
	}

	public String getQuestionID() {
		return questionID;
	}

	public AnswerFill getAnswer() {
		return answer;
	}

	public MetadataFill getMetadata() {
		return metadata;
	}

	public String getComment() {
		return comment;
	}
	
}
