package br.org.otus.laboratory.participant.validators;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.when;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.powermock.reflect.Whitebox;


@RunWith(PowerMockRunner.class)
@PrepareForTest(AliquotDeletionValidator.class)
public class AliquotDeletionValidatorTest {

  @InjectMocks
  private AliquotDeletionValidator aliquotDeletionValidatorInjected;
  @Mock
  private ExamLotDao examLotDao;
  @Mock
  private AliquotDao aliquotDao;
  @Mock
  private TransportationLotDao transportationLotDao;
  @Mock
  private ExamUploader examUploader;
  @Mock
  private Aliquot aliquot;
  @Mock
  private TransportationLot transportationLot;
  @Mock
  private ExamLot examLot;

  private static final String ALIQUOT_CODE = "354005002";
  private static final String LOT_CODE = "300000624";
  private static final ObjectId EXAM_LOT_ID = new ObjectId();
  private static final ObjectId TRANSPORTATION_LOT_ID = new ObjectId();

  private static final String EXCEPTION_MESSAGE = "Exclusion of unauthorized aliquot.";
  private AliquotDeletionValidator aliquotDeletionValidator;

  @Before
  public void setup() throws DataNotFoundException {
    aliquot = Aliquot.deserialize("{code:300000624 }");
    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    aliquotDeletionValidator = PowerMockito.spy(new AliquotDeletionValidator(ALIQUOT_CODE, aliquotDao, examUploader, examLotDao, transportationLotDao));
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
  public void validate_should_return_validationException_when_contains_aliquot_in_exam_lot() throws ValidationException, DataNotFoundException {
    aliquot = Aliquot.deserialize("{code:300000624, examLotId:" + JSON.serialize(EXAM_LOT_ID) + "}");
    Whitebox.setInternalState(aliquotDeletionValidatorInjected, "code", ALIQUOT_CODE);
    Whitebox.setInternalState(aliquotDeletionValidatorInjected, "aliquotDao", aliquotDao);
    Whitebox.setInternalState(aliquotDeletionValidatorInjected, "examLotDao", examLotDao);

    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    when(examLotDao.find(EXAM_LOT_ID)).thenReturn(examLot);
    when(examLot.getCode()).thenReturn(LOT_CODE);
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.FALSE);

    try {
      aliquotDeletionValidator.validate();
      fail();
    } catch (ValidationException expected) {
      assertThat(expected.getMessage(), CoreMatchers.containsString(EXCEPTION_MESSAGE));
    }
  }

  @Test
  public void validate_should_return_validationException_when_contains_aliquot_in_transportation_lot() throws ValidationException, DataNotFoundException {
    aliquot = Aliquot.deserialize("{code:300000624, transportationLotId:" + JSON.serialize(TRANSPORTATION_LOT_ID) + "}");
    Whitebox.setInternalState(aliquotDeletionValidatorInjected, "code", ALIQUOT_CODE);
    Whitebox.setInternalState(aliquotDeletionValidatorInjected, "aliquotDao", aliquotDao);
    Whitebox.setInternalState(aliquotDeletionValidatorInjected, "transportationLotDao", transportationLotDao);
    when(aliquotDao.find(ALIQUOT_CODE)).thenReturn(aliquot);
    when(transportationLotDao.find(any())).thenReturn(transportationLot);
    when(transportationLot.getCode()).thenReturn(LOT_CODE);
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.FALSE);

    try {
      aliquotDeletionValidatorInjected.validate();
      fail();
    } catch (ValidationException expected) {
      assertThat(expected.getMessage(), CoreMatchers.containsString(EXCEPTION_MESSAGE));
    }
  }

  @Test
  public void validate_should_return_validationException_when_contains_aliquot_in_exam_result() throws ValidationException, DataNotFoundException {
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.TRUE);

    try {
      aliquotDeletionValidator.validate();
      fail();
    } catch (ValidationException expected) {
      assertThat(expected.getMessage(), CoreMatchers.containsString(EXCEPTION_MESSAGE));
    }
  }

  @Test
  public void validate_not_should_return_validationException() throws ValidationException, DataNotFoundException {
    when(examLotDao.checkIfThereInExamLot(ALIQUOT_CODE)).thenReturn(null);
    when(transportationLotDao.checkIfThereInTransport(ALIQUOT_CODE)).thenReturn(null);
    when(examUploader.checkIfThereInExamResultLot(ALIQUOT_CODE)).thenReturn(Boolean.FALSE);

    aliquotDeletionValidator.validate();
  }

}
