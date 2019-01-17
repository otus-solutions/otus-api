package br.org.otus.examUploader.business.extraction.factories;

import java.util.LinkedHashSet;

import br.org.otus.examUploader.business.extraction.enums.ExamUploadExtractionHeaders;

public class ExamUploadExtractionHeadersFactory {

  private LinkedHashSet<String> headers;

  public ExamUploadExtractionHeadersFactory(LinkedHashSet<String> resultNames) {
    this.headers = new LinkedHashSet<>();
    this.buildHeader(resultNames);
  }

  public LinkedHashSet<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader(LinkedHashSet<String> resultNames) {
    /* Basic info headers */
    this.headers.add(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    /* Answers headers */
    resultNames.forEach(value -> {
      this.headers.add(value);
      this.headers.add(ExamUploadExtractionHeaders.REALIZATION_DATE.getValue());
      this.headers.add(ExamUploadExtractionHeaders.RELEASE_DATE.getValue());
    });
  }

}
