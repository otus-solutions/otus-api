package br.org.otus.examUploader.business.extraction.factories;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import br.org.otus.examUploader.Observation;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;

public class ExamUploadExtractionRecordsFactoryTest {

  private static final Long RECRUITMENT_NUMBER = 1000330L;
  private static final String ALIQUOT_CODE = "1000330";
  private static final String RESULT_NAME = "1000330";
  private static final String VALUE = "1000330";
  private static final String RELEASE_DATE = "1000330";
  private List<Observation> observations;

  private ParticipantExamUploadResultExtraction participantExamUploadResultExtraction;

  private ParticipantExamUploadRecordExtraction participantExamUploadRecordExtraction;
  private LinkedHashSet<ParticipantExamUploadRecordExtraction> records;
  private ExamUploadExtractionRecordsFactory examUploadExtractionRecordsFactory;

  @Before
  public void setup() {
    LinkedHashSet<String> additionalHeaders = this.createAdditionalHeaders();
    ExamUploadExtractionHeadersFactory factory = new ExamUploadExtractionHeadersFactory(additionalHeaders);
    List<String> headers = factory.getHeaders();

    this.participantExamUploadRecordExtraction = new ParticipantExamUploadRecordExtraction();
    List<ParticipantExamUploadResultExtraction> results = this.createFakeParticipantExamUploadResultExtractioList();
    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "results", results);

    this.records = new LinkedHashSet<>();
    this.records.add(this.participantExamUploadRecordExtraction);

    this.examUploadExtractionRecordsFactory = new ExamUploadExtractionRecordsFactory(headers, this.records);
  }

  @Test
  public void getValues_method_must_return_value_expected() {
    List<List<Object>> values = this.examUploadExtractionRecordsFactory.getValues();

    Assert.assertTrue(values.contains(this.participantExamUploadRecordExtraction));
  }

  private LinkedHashSet<String> createAdditionalHeaders() {
    LinkedHashSet<String> headers = new LinkedHashSet<>();
    headers.add("VALUE_1");
    return headers;
  }

  private List<ParticipantExamUploadResultExtraction> createFakeParticipantExamUploadResultExtractioList() {
    List<ParticipantExamUploadResultExtraction> results = new LinkedList<>();
    participantExamUploadResultExtraction = new ParticipantExamUploadResultExtraction();
    Whitebox.setInternalState(participantExamUploadResultExtraction, "aliquotCode", ALIQUOT_CODE);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "resultName", RESULT_NAME);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "value", VALUE);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "releaseDate", RELEASE_DATE);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "observations", observations);
    results.add(participantExamUploadResultExtraction);

    return results;
  }

}
