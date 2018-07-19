package br.org.otus.laboratory.participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.util.JsonObjecParticipantLaboratoryFactory;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOFactory;
import br.org.otus.laboratory.participant.validators.AliquotDeletionValidator;
import br.org.otus.laboratory.participant.validators.ParticipantLaboratoryValidator;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ParticipantLaboratoryServiceBean.class)
public class ParticipantLaboratoryServiceBeanTest {

  // TODO: This test needs to be refactored

  @InjectMocks
  private ParticipantLaboratoryServiceBean injectMocks;

  @Mock
  private ParticipantLaboratoryServiceBean participantLaboratoryService;
  @Mock
  private ParticipantLaboratoryValidator aliquotUpdateValidator;
  @Mock
  private ParticipantLaboratoryDao participantLaboratoryDao;
  @Mock
  private ExamLotDao examLotDao;
  @Mock
  private TransportationLotDao transportationLotDao;
  @Mock
  private ExamUploader examUploader;

  private static final long RECRUIMENT_NUMBER = 12345;
  private static final String ALIQUOT_CODE = "354005002";
  private static final String EXCEPTION_MESSAGE = "Aliquot code not found.";
  private UpdateAliquotsDTO aliquotsDTO;
  private ParticipantLaboratory participantLaboratory;

  @Before
  public void setup() throws DataNotFoundException {
    JsonObjectUpdateAliquotsDTOFactory dtoFactory = new JsonObjectUpdateAliquotsDTOFactory();
    aliquotsDTO = UpdateAliquotsDTO.deserialize(dtoFactory.create().toString());

    JsonObjecParticipantLaboratoryFactory jsonObjecParticipantLaboratoryFactory = new JsonObjecParticipantLaboratoryFactory();
    participantLaboratory = ParticipantLaboratory.deserialize(jsonObjecParticipantLaboratoryFactory.create().toString());

    Mockito.when(participantLaboratoryService.getLaboratory(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);
  }

  @Ignore
  @Test
  public void updateAliquots_method_should_call_methed_validate() throws DataNotFoundException, ValidationException {
    participantLaboratoryService.updateAliquots(aliquotsDTO);

    Mockito.verify(aliquotUpdateValidator).validate();
  }

  @Ignore
  @Test
  public void UpdateAliquots_method_when_executed_with_success_should_call_method_update() throws DataNotFoundException, ValidationException {

    participantLaboratoryService.updateAliquots(aliquotsDTO);

    Mockito.verify(participantLaboratoryService).update(participantLaboratory);
  }

  @Test(expected = Exception.class)
  public void UpdateAliquots_method_should_throw_an_exception_when_aliquots_is_invalid() throws ValidationException, DataNotFoundException {

    Mockito.doThrow(new Exception()).when(aliquotUpdateValidator).validate();
    participantLaboratoryService.updateAliquots(aliquotsDTO);
  }

  @Test
  public void when_the_method_is_executed_successfully_and_the_set_of_aliquots_are_valid_then_the_laboratory_must_be_updated() throws DataNotFoundException, ValidationException {

    ParticipantLaboratory laboratory = participantLaboratoryService.getLaboratory(RECRUIMENT_NUMBER);
    assertEquals(laboratory.getTubes().get(0).getAliquots().size(), 0);

    participantLaboratoryService.updateAliquots(aliquotsDTO);
  }

  @Ignore
  @Test
  public void deleteAliquot_should_call_method_validate() throws Exception {
    ParticipantLaboratoryServiceBean participantLaboratoryService = new ParticipantLaboratoryServiceBean();
    ParticipantLaboratoryServiceBean participantLaboratoryServiceSpy = Mockito.spy(participantLaboratoryService);

    AliquotDeletionValidator aliquotDeletionValidator = Mockito.mock(AliquotDeletionValidator.class);
    AliquotDeletionValidator aliquotDeletionValidatorSpy = Mockito.spy(aliquotDeletionValidator);
    PowerMockito.whenNew(AliquotDeletionValidator.class).withArguments(ALIQUOT_CODE, examLotDao, transportationLotDao, examUploader).thenReturn(aliquotDeletionValidator);

    aliquotDeletionValidator.validate();

    Mockito.verify(participantLaboratoryServiceSpy, Mockito.times(1)).deleteAliquot(ALIQUOT_CODE);
    Mockito.verify(aliquotDeletionValidatorSpy, Mockito.times(1)).validate();
  }

  @Test
  public void deleteAliquot_should_call_method_findParticipantLaboratoryByAliquotCode() throws ValidationException, DataNotFoundException {
    Mockito.when(participantLaboratoryDao.findParticipantLaboratoryByAliquotCode(ALIQUOT_CODE)).thenReturn(participantLaboratory);

    injectMocks.deleteAliquot(ALIQUOT_CODE);

    Mockito.verify(participantLaboratoryDao, Mockito.times(1)).findParticipantLaboratoryByAliquotCode(ALIQUOT_CODE);
  }

  @Test
  public void deleteAliquot_should_call_method_updateLaboratoryData() throws ValidationException, DataNotFoundException {
    Mockito.when(participantLaboratoryDao.findParticipantLaboratoryByAliquotCode(ALIQUOT_CODE)).thenReturn(participantLaboratory);

    injectMocks.deleteAliquot(ALIQUOT_CODE);

    Mockito.verify(participantLaboratoryDao, Mockito.times(1)).updateLaboratoryData(participantLaboratory);
  }

  @Test
  public void deleteAliquot_should_return_exception_when_participant_laboratory_not_found() throws ValidationException, DataNotFoundException {
    Mockito.when(participantLaboratoryDao.findParticipantLaboratoryByAliquotCode(ALIQUOT_CODE)).thenReturn(null);

    try {
      injectMocks.deleteAliquot(ALIQUOT_CODE);
      fail();
    } catch (DataNotFoundException expected) {
      assertThat(expected.getMessage(), CoreMatchers.containsString(EXCEPTION_MESSAGE));
    }

  }

}
