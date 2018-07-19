package br.org.otus.laboratory.participant.validators;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AliquotDeletionValidator.class)
public class AliquotDeletionValidatorTest {

  @Mock
  private ExamLotDao examLotDao;
  @Mock
  private TransportationLotDao transportationLotDao;
  @Mock
  private ExamUploader examUploader;

  private static final String ALIQUOT_CODE = "354005002";
  private static final String LOT_CODE = "300000624";
  private static final String EXCEPTION_MESSAGE = "Exclusion of unauthorized aliquot.";
  private AliquotDeletionValidator aliquotDeletionValidator;

  @Before
  public void setup() {
    aliquotDeletionValidator = PowerMockito.spy(new AliquotDeletionValidator(ALIQUOT_CODE, examLotDao, transportationLotDao, examUploader));
  }

  @Test
  public void validate_should_call_method_aliquotInTransportation() throws Exception {
    aliquotDeletionValidator.validate();

    PowerMockito.verifyPrivate(aliquotDeletionValidator).invoke("aliquotInTransportation");
  }

  @Test
  public void validate_should_call_method_aliquotInExamLot() throws Exception {
    aliquotDeletionValidator.validate();

    PowerMockito.verifyPrivate(aliquotDeletionValidator).invoke("aliquotInExamLot");
  }

  @Test
  public void validate_should_call_method_aliquotInExamResult() throws Exception {
    aliquotDeletionValidator.validate();

    PowerMockito.verifyPrivate(aliquotDeletionValidator).invoke("aliquotInExamResult");
  }

  @Test
  public void validate_should_return_validationException_when_contains_aliquot_in_exam_lot() throws ValidationException {
    when(examLotDao.checkIfThereInExamLot(ALIQUOT_CODE)).thenReturn(LOT_CODE);
    when(transportationLotDao.checkIfThereInTransport(ALIQUOT_CODE)).thenReturn(null);
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.FALSE);

    try {
      aliquotDeletionValidator.validate();
      fail();
    } catch (ValidationException expected) {
      assertThat(expected.getMessage(), CoreMatchers.containsString(EXCEPTION_MESSAGE));
    }
  }

  @Test
  public void validate_should_return_validationException_when_contains_aliquot_in_transportation_lot() throws ValidationException {
    when(examLotDao.checkIfThereInExamLot(ALIQUOT_CODE)).thenReturn(null);
    when(transportationLotDao.checkIfThereInTransport(ALIQUOT_CODE)).thenReturn(LOT_CODE);
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.FALSE);

    try {
      aliquotDeletionValidator.validate();
      fail();
    } catch (ValidationException expected) {
      assertThat(expected.getMessage(), CoreMatchers.containsString(EXCEPTION_MESSAGE));
    }
  }

  @Test
  public void validate_should_return_validationException_when_contains_aliquot_in_exam_result() throws ValidationException {
    when(examLotDao.checkIfThereInExamLot(ALIQUOT_CODE)).thenReturn(null);
    when(transportationLotDao.checkIfThereInTransport(ALIQUOT_CODE)).thenReturn(null);
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.TRUE);

    try {
      aliquotDeletionValidator.validate();
      fail();
    } catch (ValidationException expected) {
      assertThat(expected.getMessage(), CoreMatchers.containsString(EXCEPTION_MESSAGE));
    }
  }

  @Test
  public void validate_not_should_return_validationException() throws ValidationException {
    when(examLotDao.checkIfThereInExamLot(ALIQUOT_CODE)).thenReturn(null);
    when(transportationLotDao.checkIfThereInTransport(ALIQUOT_CODE)).thenReturn(null);
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.FALSE);

    aliquotDeletionValidator.validate();
  }

}
