package br.org.otus.examUploader.business.extraction;

import java.util.LinkedHashSet;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.api.Extractable;
import br.org.otus.examUploader.business.extraction.factories.ExamUploadExtractionHeadersFactory;
import br.org.otus.examUploader.business.extraction.factories.ExamUploadExtractionRecordsFactory;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;

public class ExamUploadExtration implements Extractable {

  private ExamUploadExtractionHeadersFactory headersFactory;
  private ExamUploadExtractionRecordsFactory recordsFactory;

  public ExamUploadExtration(LinkedHashSet<String> headers, LinkedHashSet<ParticipantExamUploadRecordExtraction> records) {
    this.headersFactory = new ExamUploadExtractionHeadersFactory(headers);
    this.recordsFactory = new ExamUploadExtractionRecordsFactory(this.headersFactory.getHeaders(), records);
  }

  @Override
  public List<String> getHeaders() {
    return this.headersFactory.getHeaders();
  }

  @Override
  public List<List<Object>> getValues() throws DataNotFoundException {
    this.recordsFactory.buildResultInformation();

    return this.recordsFactory.getValues();
  }

}
