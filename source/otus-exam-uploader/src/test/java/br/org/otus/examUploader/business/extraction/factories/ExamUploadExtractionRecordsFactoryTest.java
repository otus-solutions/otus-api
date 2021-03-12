package br.org.otus.examUploader.business.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.examUploader.ExtraVariable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import br.org.otus.examUploader.Observation;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;

public class ExamUploadExtractionRecordsFactoryTest {

  private static final Long RECRUITMENT_NUMBER = 1000330L;
  private static final String CODE = "1000330";
  private static final String RESULT_NAME = "EXAM_EXAMPLE";
  private static final String EXAM_NAME = "EXAM_NAME_EXAMPLE";
  private static final String VALUE_1 = "10";
  private static final String VALUE_2 = "20";
  private static final String RELEASE_DATE = "1000330";
  private static final String REALIZATION_DATE = "1000330";
  private static final String CUT_OFF_VALUE = "1000330";

  private List<String> headers;
  private LinkedList<ParticipantExamUploadResultExtraction> records;
  private ParticipantExamUploadResultExtraction record;

  private ExamUploadExtractionRecordsFactory examUploadExtractionRecordsFactory;

  @Before
  public void setup() {
    ExamUploadExtractionHeadersFactory headersFactory = new ExamUploadExtractionHeadersFactory();
    this.headers = headersFactory.getHeaders();
    this.records = new LinkedList<>();

    this.examUploadExtractionRecordsFactory = new ExamUploadExtractionRecordsFactory(this.records);
  }

  @Test
  public void buildResultInformation_method_should_call_createRecordsAnswers_method() throws Exception {
    List<Observation> observations = new ArrayList<>();
    List<ExtraVariable> extraVariables = new ArrayList<ExtraVariable>();
    extraVariables.add(new ExtraVariable("any", "any"));
    this.examUploadExtractionRecordsFactory = PowerMockito.spy(new ExamUploadExtractionRecordsFactory(this.records));
    this.record = this.createFakeParticipantExamUploadRecord(RECRUITMENT_NUMBER,
            CODE, EXAM_NAME, RESULT_NAME,
            VALUE_1,
            RELEASE_DATE,
            REALIZATION_DATE,
            observations,
            CUT_OFF_VALUE,
            extraVariables);
    this.examUploadExtractionRecordsFactory.buildResultInformation();

    PowerMockito.verifyPrivate(this.examUploadExtractionRecordsFactory).invoke("createRecordsAnswers", this.record);
  }

  @Test
  public void getRecords_method_should_only_return_one_line() {
    List<Observation> observations = new ArrayList<>();
    List<ExtraVariable> extraVariables = new ArrayList<ExtraVariable>();
    extraVariables.add(new ExtraVariable("any", "any"));
    this.records.add(this.createFakeParticipantExamUploadRecord(RECRUITMENT_NUMBER, CODE, EXAM_NAME, RESULT_NAME, VALUE_1,
            RELEASE_DATE, REALIZATION_DATE, observations,  CUT_OFF_VALUE, extraVariables));
    this.examUploadExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.examUploadExtractionRecordsFactory.getRecords();

    Assert.assertEquals(1, records.size());
  }

  @Test
  public void getRecords_method_should_return_list_with_expected_values() {
    List<Observation> observations = new ArrayList<>();
    List<ExtraVariable> extraVariables = new ArrayList<ExtraVariable>();
    extraVariables.add(new ExtraVariable("any", "any"));
    this.records.add(this.createFakeParticipantExamUploadRecord(RECRUITMENT_NUMBER, CODE, EXAM_NAME, RESULT_NAME, VALUE_1,
            RELEASE_DATE, REALIZATION_DATE, observations, CUT_OFF_VALUE, extraVariables));
    this.examUploadExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.examUploadExtractionRecordsFactory.getRecords();

    List<Object> results = records.get(0);
    Assert.assertEquals(RECRUITMENT_NUMBER.toString(), results.get(0));
    Assert.assertEquals(CODE, results.get(1));
    Assert.assertEquals(EXAM_NAME, results.get(2));
    Assert.assertEquals(RESULT_NAME, results.get(3));
    Assert.assertEquals(VALUE_1, results.get(4));
    Assert.assertEquals(RELEASE_DATE, results.get(5));
    Assert.assertEquals(REALIZATION_DATE, results.get(6));
    Assert.assertEquals("", results.get(7));
  }

  private ParticipantExamUploadResultExtraction createFakeParticipantExamUploadRecord(Long rn, String code, String examName, String resultName, String value, String releaseDate, String realizationDate, List<Observation> observations, String cutOffValue, List<ExtraVariable> extraVariables) {
    ParticipantExamUploadResultExtraction participantExamUploadRecordExtraction = new ParticipantExamUploadResultExtraction();

    Whitebox.setInternalState(participantExamUploadRecordExtraction, "recruitmentNumber", rn);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "code", code);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "examName", examName);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "resultName", resultName);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "value", value);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "releaseDate", releaseDate);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "realizationDate", realizationDate);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "observations", observations);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "cutOffValue", cutOffValue);
    Whitebox.setInternalState(participantExamUploadRecordExtraction, "extraVariables", extraVariables);

    return participantExamUploadRecordExtraction;
  }

}
