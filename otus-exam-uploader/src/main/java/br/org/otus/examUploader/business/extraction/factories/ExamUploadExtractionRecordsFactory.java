package br.org.otus.examUploader.business.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;

public class ExamUploadExtractionRecordsFactory {

  private List<String> headers;
  private LinkedHashSet<ParticipantExamUploadRecordExtraction> records;
  private List<List<Object>> outputValues;

  public ExamUploadExtractionRecordsFactory(List<String> headers, LinkedHashSet<ParticipantExamUploadRecordExtraction> records) {
    this.headers = headers;
    this.records = records;
    this.outputValues = new LinkedList<>();

  }

  public List<List<Object>> getValues() {
    return this.outputValues;
  }

  public void buildResultInformation() {
    for (ParticipantExamUploadRecordExtraction record : records) {
      List<String> answers = new LinkedList<String>();
      answers.add(record.getRecruitmentNumber().toString());
      /* prepare to receive answers */
      while (answers.size() < headers.size() - 1) {
        answers.add("");
      }
      for (ParticipantExamUploadResultExtraction result : record.getResults()) {
        int index = this.headers.indexOf(result.getResultName());
        if (index > 0) {
          answers.add(index, result.getValue());
          answers.add(index + 1, result.getReleaseDate());
        }
      }
      outputValues.add(new ArrayList<>(answers)); // TODO: Esta alocando um novo espaço na memoria realizando o new?
      answers = null;
    }
  }

  private List<String> createRecordsAnswers(String rn, List<ParticipantExamUploadResultExtraction> results) {
    List<String> answers = new ArrayList<>();
    /* prepare to receive answers */
    while (answers.size() < headers.size()) {
      answers.add("");
    }

    answers.add(0, rn);
    for (ParticipantExamUploadResultExtraction result : results) {
      int index = this.headers.indexOf(result.getResultName());
      if (index > 0) {
        answers.add(index, result.getValue());
        answers.add(index + 1, result.getReleaseDate());
      }
      results.remove(result);
    }

    return answers;
  }

}
