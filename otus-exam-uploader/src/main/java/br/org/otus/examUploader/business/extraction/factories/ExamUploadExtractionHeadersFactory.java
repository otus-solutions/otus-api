package br.org.otus.examUploader.business.extraction.factories;

import java.util.LinkedHashSet;
import java.util.List;

import br.org.otus.examUploader.business.extraction.enums.ExamUploadExtractionHeaders;

public class ExamUploadExtractionHeadersFactory {

  private LinkedHashSet<String> headers;

  public ExamUploadExtractionHeadersFactory(List<String> resultHeaders) {
    this.headers = new LinkedHashSet<>();
    this.buildHeader(resultHeaders);
  }

  public LinkedHashSet<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader(List<String> resultHeaders) {
    /* Basic info headers */
    this.headers.add(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    /* Answers headers */
    resultHeaders.forEach(value -> {
      this.headers.add(value);
      this.headers.add(ExamUploadExtractionHeaders.RELEASE_DATE.getValue());
    });
  }

}
