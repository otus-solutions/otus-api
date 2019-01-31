package br.org.otus.examUploader.business.extraction.factories;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.examUploader.business.extraction.enums.ExamUploadExtractionHeaders;

public class ExamUploadExtractionHeadersFactory {

  private List<String> headers;

  public ExamUploadExtractionHeadersFactory(LinkedHashSet<String> headers) {
    this.headers = new LinkedList<String>();
    this.buildHeader(headers);
  }

  public List<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader(LinkedHashSet<String> headers) {
    /* Basic information headers */
    this.headers.add(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue());

    /* Additional headers */
    headers.forEach(value -> {
      this.headers.add(ExamUploadExtractionHeaders.ALIQUOT_CODE.getValue());
      this.headers.add(value);
      this.headers.add(ExamUploadExtractionHeaders.RELEASE_DATE.getValue());
      this.headers.add(ExamUploadExtractionHeaders.OBSERVATIONS.getValue());
    });
  }

}
