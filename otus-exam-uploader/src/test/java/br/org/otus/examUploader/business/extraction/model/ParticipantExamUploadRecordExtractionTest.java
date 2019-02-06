package br.org.otus.examUploader.business.extraction.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import br.org.otus.examUploader.Observation;

public class ParticipantExamUploadRecordExtractionTest {

  private static final String ALIQUOT_CODE = "1000330";
  private static final String RESULT_NAME = "1000330";
  private static final String VALUE = "1000330";
  private static final String RELEASE_DATE = "2018-07-08T15:13:00.000Z";
  private List<Observation> observations;
  private static final Long RECRUITMENT_NUMBER = 1000330L;

  private ParticipantExamUploadRecordExtraction participantExamUploadRecordExtraction;

  @Before
  public void setup() {
    this.participantExamUploadRecordExtraction = new ParticipantExamUploadRecordExtraction();

    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "recruitmentNumber", RECRUITMENT_NUMBER);
    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "aliquotCode", ALIQUOT_CODE);
    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "resultName", RESULT_NAME);
    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "value", VALUE);
    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "releaseDate", RELEASE_DATE);
    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "observations", observations);
  }

  @Test
  public void getRecruitmentNumber_method_must_return_value_expected() {
    Assert.assertEquals(RECRUITMENT_NUMBER, participantExamUploadRecordExtraction.getRecruitmentNumber());
  }

  @Test
  public void getAliquotCode_method_must_return_value_expected() {
    Assert.assertEquals(ALIQUOT_CODE, participantExamUploadRecordExtraction.getAliquotCode());
  }

  @Test
  public void getResultName_method_must_return_value_expected() {
    Assert.assertEquals(RESULT_NAME, participantExamUploadRecordExtraction.getResultName());
  }

  @Test
  public void getValue_method_must_return_value_expected() {
    Assert.assertEquals(VALUE, participantExamUploadRecordExtraction.getValue());
  }

  @Test
  public void getReleaseDate_method_must_return_value_expected() {
    Assert.assertEquals(RELEASE_DATE, participantExamUploadRecordExtraction.getReleaseDate());
  }

  @Test
  public void getObservations_method_must_return_value_expected() {
    Assert.assertEquals(observations, participantExamUploadRecordExtraction.getObservations());
  }

}
