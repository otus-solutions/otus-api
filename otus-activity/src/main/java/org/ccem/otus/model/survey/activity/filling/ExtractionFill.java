package org.ccem.otus.model.survey.activity.filling;

import java.util.Map;

public class ExtractionFill {

	private String questionID;
	private Map<String, Object> answerExtract;
	private String metadata;
	private String comment;

	public ExtractionFill(String questionID) {
		this.questionID = questionID;
	}

	public String getQuestionID() {
		return questionID;
	}

	public Map<String, Object> getAnswerExtract() {
		return answerExtract;
	}

	public void setAnswerExtract(Map<String, Object> answerExtract) {
		this.answerExtract = answerExtract;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
