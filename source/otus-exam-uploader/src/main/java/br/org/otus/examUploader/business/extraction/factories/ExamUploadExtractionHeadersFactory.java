package br.org.otus.examUploader.business.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import br.org.otus.examUploader.business.extraction.enums.ExamUploadExtractionHeaders;

public class ExamUploadExtractionHeadersFactory {

  private List<String> headers;

  public ExamUploadExtractionHeadersFactory() {
    this.headers = new LinkedList<String>();
    this.buildHeader();
  }

  public List<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader() {
    this.headers.add(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    this.headers.add(ExamUploadExtractionHeaders.ALIQUOT_CODE.getValue());
    this.headers.add(ExamUploadExtractionHeaders.EXAM_NAME.getValue());
    this.headers.add(ExamUploadExtractionHeaders.RESULT_NAME.getValue());
    this.headers.add(ExamUploadExtractionHeaders.RESULT.getValue());
    this.headers.add(ExamUploadExtractionHeaders.RELEASE_DATE.getValue());
    this.headers.add(ExamUploadExtractionHeaders.REALIZATION_DATE.getValue());
    this.headers.add(ExamUploadExtractionHeaders.OBSERVATIONS.getValue());
    this.headers.add(ExamUploadExtractionHeaders.CUTOFFVALUE.getValue());
    this.headers.add(ExamUploadExtractionHeaders.EXTRAVARIABLES.getValue());
  }

}
