package br.org.otus.examUploader.business.extraction.model;

import java.util.LinkedList;
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
  private static final String RELEASE_DATE = "1000330";
  private List<Observation> observations;
  private static final Long RECRUITMENT_NUMBER = 1000330L;
  private ParticipantExamUploadRecordExtraction participantExamUploadRecordExtraction;

  @Before
  public void setup() {
    this.participantExamUploadRecordExtraction = new ParticipantExamUploadRecordExtraction();
    List<ParticipantExamUploadResultExtraction> results = this.createFakeParticipantExamUploadResultExtractioList();

    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "recruitmentNumber", RECRUITMENT_NUMBER);
    Whitebox.setInternalState(this.participantExamUploadRecordExtraction, "results", results);
  }

  @Test
  public void getRecruitmentNumber_method_must_return_value_expected() {
    Assert.assertEquals(RECRUITMENT_NUMBER, participantExamUploadRecordExtraction.getRecruitmentNumber());
  }

  @Test
  public void getResults_method_must_return_value_expected() {
    List<ParticipantExamUploadResultExtraction> results = this.participantExamUploadRecordExtraction.getResults();

    Assert.assertEquals(results, participantExamUploadRecordExtraction.getResults());
  }

  private List<ParticipantExamUploadResultExtraction> createFakeParticipantExamUploadResultExtractioList() {
    List<ParticipantExamUploadResultExtraction> results = new LinkedList<>();
    ParticipantExamUploadResultExtraction result = new ParticipantExamUploadResultExtraction();
    Whitebox.setInternalState(result, "aliquotCode", ALIQUOT_CODE);
    Whitebox.setInternalState(result, "resultName", RESULT_NAME);
    Whitebox.setInternalState(result, "value", VALUE);
    Whitebox.setInternalState(result, "releaseDate", RELEASE_DATE);
    Whitebox.setInternalState(result, "observations", observations);
    results.add(result);

    return results;
  }

}