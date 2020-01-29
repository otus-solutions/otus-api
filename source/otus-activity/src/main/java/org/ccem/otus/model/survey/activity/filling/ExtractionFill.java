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
    this.comment = ExtractionFill.escapeText(comment);
  }

  /**
   * Removes line break characters replacing for blank spaces.
   * This prevents the csv writer to assume \n from text fields as being a new line char
   *
   * @param text text from source which accept , like text questions, grid text questions and comments
   * @return the escaped text.
   */
  public static String escapeText(String text) {
    return text != null ? text.replace("\n", " ") : text;
  }


}
