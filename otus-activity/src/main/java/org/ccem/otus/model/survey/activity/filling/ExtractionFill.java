package org.ccem.otus.model.survey.activity.filling;

import java.util.Map;

public class ExtractionFill {

	private Map<Object, Object> answerExtract;
	private String metadata;
	private String comment;

	public Map<Object, Object> getAnswerExtract() {
		return answerExtract;
	}

	public void setAnswerExtract(Map<Object, Object> answerExtract) {
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
