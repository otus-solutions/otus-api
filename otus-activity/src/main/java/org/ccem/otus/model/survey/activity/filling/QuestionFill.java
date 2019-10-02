package org.ccem.otus.model.survey.activity.filling;

public class QuestionFill {

	private String objectType;
	private String questionID;
	private String customID; //todo: testar funcionamento no sistema
	private AnswerFill answer;
	private boolean forceAnswer;
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

	public boolean isForceAnswer() {
		return forceAnswer;
	}

	public void setCustomID(String customID) {
		this.customID = customID;
	}

	public ExtractionFill extraction() {
		ExtractionFill extractionFill = new ExtractionFill(questionID);
		extractionFill.setAnswerExtract(this.answer.getAnswerExtract(this.questionID));
		extractionFill.setMetadata(this.metadata.getValue());
		extractionFill.setComment(this.getComment());
		return extractionFill;
	}
}
