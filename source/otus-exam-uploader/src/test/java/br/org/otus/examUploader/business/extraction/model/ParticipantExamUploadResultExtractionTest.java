package br.org.otus.examUploader.business.extraction.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import br.org.otus.examUploader.Observation;

public class ParticipantExamUploadResultExtractionTest {

  private static final String ALIQUOT_CODE = "1000330";
  private static final String RESULT_NAME = "1000330";
  private static final String VALUE = "1000330";
  private static final String RELEASE_DATE = "1000330";
  private static final String REALIZATION_DATE = "1000330";
  private List<Observation> observations;

  private ParticipantExamUploadResultExtraction participantExamUploadResultExtraction;

  @Before
  public void setup() {
    this.participantExamUploadResultExtraction = new ParticipantExamUploadResultExtraction();

    Whitebox.setInternalState(participantExamUploadResultExtraction, "code", ALIQUOT_CODE);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "resultName", RESULT_NAME);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "value", VALUE);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "releaseDate", RELEASE_DATE);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "realizationDate", REALIZATION_DATE);
    Whitebox.setInternalState(participantExamUploadResultExtraction, "observations", observations);
  }

  @Test
  public void getters_methods_should_return_values_expected() {
    Assert.assertEquals(ALIQUOT_CODE, participantExamUploadResultExtraction.getCode());
    Assert.assertEquals(RESULT_NAME, participantExamUploadResultExtraction.getResultName());
    Assert.assertEquals(VALUE, participantExamUploadResultExtraction.getValue());
    Assert.assertEquals(RELEASE_DATE, participantExamUploadResultExtraction.getReleaseDate());
    Assert.assertEquals(REALIZATION_DATE, participantExamUploadResultExtraction.getRealizationDate());
    Assert.assertEquals(observations, participantExamUploadResultExtraction.getObservations());
  }

}