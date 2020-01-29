package org.ccem.otus.model.survey.activity.filling.answer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ccem.otus.model.survey.activity.filling.AnswerFill;

public class FileUploadAnswer extends AnswerFill {

  private List<FileAnswer> value;

  public List<FileAnswer> getValue() {
    return value;
  }

  @Override
  public Map<String, Object> getAnswerExtract(String questionID) {
    Map<String, Object> extraction = new LinkedHashMap<String, Object>();
    if (this.value != null) {
      String concatFileNames = "";
      for (FileAnswer fileAnswer : value) {
        if (concatFileNames.length() == 0) {
          concatFileNames = concatFileNames.concat(fileAnswer.getName());
        } else {
          concatFileNames = concatFileNames.concat("," + fileAnswer.getName());
        }
      }
      extraction.put(questionID, concatFileNames);
    }
    return extraction;
  }

}
