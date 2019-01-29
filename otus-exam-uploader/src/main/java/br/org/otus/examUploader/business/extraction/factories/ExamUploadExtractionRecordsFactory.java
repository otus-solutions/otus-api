package br.org.otus.examUploader.business.extraction.factories;

import java.util.ArrayList;
import java.util.Iterator;
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
    records.forEach(record -> {
      while (!record.getResults().isEmpty()) {
        this.outputValues.add(new ArrayList<>(this.createRecordsAnswers(record.getRecruitmentNumber().toString(), record.getResults())));
      }
    });
  }

  private List<String> createRecordsAnswers(String rn, List<ParticipantExamUploadResultExtraction> results) {
    List<String> answers = new LinkedList<String>();
    /* prepare to receive answers */
    while (answers.size() < headers.size()) {
      answers.add("");
    }

    answers.set(0, rn);
    int count = 1;
    Iterator<ParticipantExamUploadResultExtraction> iterator = results.iterator();
    while (iterator.hasNext()) {
      ParticipantExamUploadResultExtraction result = iterator.next();
      int index = this.headers.indexOf(result.getResultName());
      if (index > 0) {
        if (answers.get(index).isEmpty()) {
          answers.set(index - 1, result.getAliquotCode());
          answers.set(index, result.getValue());
          answers.set(index + 1, result.getReleaseDate());
          if (!result.getObservations().isEmpty()) {
            result.getObservations().forEach(observation -> {
              String old = answers.get(index + 2);
              if (old.isEmpty())
                answers.set(index + 2, observation.getValue());
              else
                answers.set(index + 2, old + ", " + observation.getValue());
            });
          }
          iterator.remove();
          count++;
        } else if (count > answers.size()) {
          break;
        }
      }
    }

    return answers;
  }

}
