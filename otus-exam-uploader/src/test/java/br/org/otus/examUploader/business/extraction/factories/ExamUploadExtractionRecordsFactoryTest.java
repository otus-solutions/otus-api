package br.org.otus.examUploader.business.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import br.org.otus.examUploader.Observation;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;

public class ExamUploadExtractionRecordsFactoryTest {

  private static final Long RECRUITMENT_NUMBER = 1000330L;
  private static final String ALIQUOT_CODE = "1000330";
  private static final String RESULT_NAME = "EXAM_EXEMPLE";
  private static final String VALUE_1 = "10";
  private static final String VALUE_2 = "20";
  private static final String RELEASE_DATE = "1000330";

  private List<String> headers;
  private LinkedList<ParticipantExamUploadRecordExtraction> records;
  private ParticipantExamUploadRecordExtraction record;
    
  private ExamUploadExtractionRecordsFactory examUploadExtractionRecordsFactory;

  @Before
  public void setup() {
    ExamUploadExtractionHeadersFactory headersFactory = new ExamUploadExtractionHeadersFactory();
    this.headers = headersFactory.getHeaders();
    this.records = new LinkedList<>();

//    this.examUploadExtractionRecordsFactory = new ExamUploadExtractionRecordsFactory(this.records);
  }

  @Test
  public void buildResultInformation_method_should_call_createRecordsAnswers_method() throws Exception {
    List<Observation> observations = new ArrayList<>();
    this.record = this.createFakeParticipantExamUploadRecord(RECRUITMENT_NUMBER, ALIQUOT_CODE, RESULT_NAME, VALUE_1, RELEASE_DATE, observations);
//    this.examUploadExtractionRecordsFactory = PowerMockito.spy(new ExamUploadExtractionRecordsFactory(this.records));
    this.examUploadExtractionRecordsFactory.buildResultInformation();

    PowerMockito.verifyPrivate(this.examUploadExtractionRecordsFactory).invoke("createRecordsAnswers", this.record);
  }

  @Test
  public void getRecords_method_should_only_return_one_line() {
    List<Observation> observations = new ArrayList<>();
    this.records.add(this.createFakeParticipantExamUploadRecord(RECRUITMENT_NUMBER, ALIQUOT_CODE, RESULT_NAME, VALUE_1, RELEASE_DATE, observations));
    this.examUploadExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.examUploadExtractionRecordsFactory.getRecords();

    Assert.assertEquals(1, records.size());
  }

  @Test
  public void getRecords_method_should_return_list_with_expected_values() {
    List<Observation> observations = new ArrayList<>();
    this.records.add(this.createFakeParticipantExamUploadRecord(RECRUITMENT_NUMBER, ALIQUOT_CODE, RESULT_NAME, VALUE_1, RELEASE_DATE, observations));
    this.examUploadExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.examUploadExtractionRecordsFactory.getRecords();

    List<Object> results = records.get(0);
    Assert.assertEquals(RECRUITMENT_NUMBER.toString(), results.get(0));
    Assert.assertEquals(ALIQUOT_CODE, results.get(1));
    Assert.assertEquals(RESULT_NAME, results.get(2));
    Assert.assertEquals(VALUE_1, results.get(3));
    Assert.assertEquals(RELEASE_DATE, results.get(4));
    Assert.assertEquals("", results.get(5));
  }

  private ParticipantExamUploadRecordExtraction createFakeParticipantExamUploadRecord(Long rn, String aliquotCode,
      String resultName, String value, String releaseDate, List<Observation> observations) {
    ParticipantExamUploadRecordExtraction participantExamUploadRecordExtraction = new ParticipantExamUploadRecordExtraction();

    Whitebox.setInternalState(participantExamUploadRecordExtraction, "recruitmentNumber", rn);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "aliquotCode", aliquotCode);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "resultName", resultName);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "value", value);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "releaseDate", releaseDate);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "observations", observations);

    return participantExamUploadRecordExtraction;
  }

}
