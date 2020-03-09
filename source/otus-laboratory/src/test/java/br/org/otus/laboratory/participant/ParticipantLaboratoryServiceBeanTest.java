package br.org.otus.laboratory.participant;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.org.otus.laboratory.participant.aliquot.AliquotEvent;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupRaffle;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.tube.TubeService;
import br.org.otus.laboratory.participant.util.JsonObjecParticipantLaboratoryFactory;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOFactory;
import br.org.otus.laboratory.participant.validators.AliquotDeletionValidator;
import br.org.otus.laboratory.participant.validators.AliquotUpdateValidator;
import br.org.otus.laboratory.participant.validators.ParticipantLaboratoryExtractionDao;
import br.org.otus.laboratory.participant.validators.ParticipantLaboratoryValidator;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ParticipantLaboratoryServiceBean.class)
public class ParticipantLaboratoryServiceBeanTest {

  // TODO: This test needs to be refactored

  @InjectMocks
  private ParticipantLaboratoryServiceBean participantLaboratoryServiceBean = PowerMockito.spy(new ParticipantLaboratoryServiceBean());
  @Mock
  private ParticipantDao participantDao;
  @Mock
  private FieldCenterDao fieldCenterDao;
  @Mock
  private Participant participant;
  @Mock
  private TubeService tubeService;
  @Mock
  private AliquotService aliquotService;
  @Mock
  private AliquotDao aliquotDao;
  @Mock
  private CollectGroupRaffle groupRaffle;
  @Mock
  private CollectGroupDescriptor collectGroup;
  @Mock
  private ParticipantLaboratoryValidator participantLaboratoryValidator;
  @Mock
  private AliquotUpdateValidator aliquotUpdateValidator;
  @Mock
  private ParticipantLaboratoryDao participantLaboratoryDao;
  @Mock
  private ExamLotDao examLotDao;
  @Mock
  private TransportationLotDao transportationLotDao;
  @Mock
  private ExamUploader examUploader;
  @Mock
  private ParticipantLaboratory participantLaboratory;
  @Mock
  private ParticipantLaboratoryExtractionDao participantLaboratoryExtractionDao;
  @Mock
  private Aliquot convertedAliquot;
  @Mock
  private AliquotEvent aliquotEvent;
  @Mock
  private FieldCenter fieldCenter;

  private static final long RECRUIMENT_NUMBER = 12345;
  private static final String ALIQUOT_CODE = "354005002";
  private static final Aliquot ALIQUOT = Aliquot.deserialize("{code:1221654877,tubeCode:\"200000\"}");
  private static final SimpleAliquot SIMPLE_ALIQUOT = SimpleAliquot.deserialize("{code:1221654877}");
  private static final String EXCEPTION_MESSAGE = "Aliquot code not found.";
  private UpdateAliquotsDTO updateAliquotsDTO;

  @Before
  public void setup() throws DataNotFoundException {
    JsonObjectUpdateAliquotsDTOFactory dtoFactory = new JsonObjectUpdateAliquotsDTOFactory();
    updateAliquotsDTO = UpdateAliquotsDTO.deserialize(dtoFactory.create().toString());

    JsonObjecParticipantLaboratoryFactory jsonObjecParticipantLaboratoryFactory = new JsonObjecParticipantLaboratoryFactory();
    participantLaboratory = ParticipantLaboratory.deserialize(jsonObjecParticipantLaboratoryFactory.create().toString());

  }

  @Test
  public void hasLaboratory_method_should_return_true() throws DataNotFoundException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);
    assertTrue(participantLaboratoryServiceBean.hasLaboratory(RECRUIMENT_NUMBER));
  }

  @Test
  public void hasLaboratory_method_should_return_false() throws DataNotFoundException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenThrow(new DataNotFoundException());
    assertFalse(participantLaboratoryServiceBean.hasLaboratory(RECRUIMENT_NUMBER));
  }

  @Test
  public void getLaboratory_method_should_call_participantLaboratory_setAliquots() throws DataNotFoundException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);

    List<Aliquot> aliquotList = new ArrayList<>();
    aliquotList.add(ALIQUOT);
    when(aliquotService.getAliquots(RECRUIMENT_NUMBER)).thenReturn(aliquotList);

    List<SimpleAliquot> simpleAliquotList = new ArrayList<>();
    simpleAliquotList.add(SIMPLE_ALIQUOT);

    participantLaboratory = participantLaboratoryServiceBean.getLaboratory(RECRUIMENT_NUMBER);

    assertEquals(simpleAliquotList, participantLaboratory.getAliquotsList());
  }

  @Test
  public void create_method_should_persist_laboratory() throws Exception {
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    when(groupRaffle.perform(participant)).thenReturn(collectGroup);
    when(tubeService.generateTubes(TubeSeed.generate(participant.getFieldCenter(), collectGroup))).thenReturn(participantLaboratory.getTubes());
    when(participant.getFieldCenter()).thenReturn(fieldCenter);
    when(fieldCenter.getAcronym()).thenReturn("ACRONYM");
    when(fieldCenterDao.fetchByAcronym("ACRONYM")).thenReturn(fieldCenter);
    whenNew(ParticipantLaboratory.class).withAnyArguments().thenReturn(participantLaboratory);
    participantLaboratoryServiceBean.create(RECRUIMENT_NUMBER);
    verify(participantLaboratoryDao, Mockito.times(1)).persist(participantLaboratory);
  }

  @Test
  public void updateAliquots_method_should_call_method_validate() throws Exception {
    doReturn(participantLaboratory).when(participantLaboratoryServiceBean, "getLaboratory", RECRUIMENT_NUMBER);
    whenNew(AliquotUpdateValidator.class).withArguments(updateAliquotsDTO, aliquotDao, participantLaboratory).thenReturn(aliquotUpdateValidator);
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    participantLaboratoryServiceBean.updateAliquots(updateAliquotsDTO);
    verify(aliquotUpdateValidator, times(1)).validate();
  }

  @Test
  public void updateAliquots_method_should_call_aliquotDao_executeFunction() throws Exception {
    doReturn(participantLaboratory).when(participantLaboratoryServiceBean, "getLaboratory", RECRUIMENT_NUMBER);
    whenNew(AliquotUpdateValidator.class).withArguments(updateAliquotsDTO, aliquotDao, participantLaboratory).thenReturn(aliquotUpdateValidator);
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    participantLaboratoryServiceBean.updateAliquots(updateAliquotsDTO);
    Thread.sleep(100);
    verify(aliquotDao, times(1)).executeFunction("syncResults()");
  }

  @Test
  public void UpdateAliquots_method_when_executed_with_success_should_call_method_aliquotDao_persist() throws Exception {
    doReturn(participantLaboratory).when(participantLaboratoryServiceBean, "getLaboratory", RECRUIMENT_NUMBER);
    whenNew(AliquotUpdateValidator.class).withArguments(updateAliquotsDTO, aliquotDao, participantLaboratory).thenReturn(aliquotUpdateValidator);
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    participantLaboratoryServiceBean.updateAliquots(updateAliquotsDTO);
    verify(aliquotDao, times(1)).persist(any());
  }

  @Test(expected = Exception.class)
  public void UpdateAliquots_method_should_throw_an_exception_when_aliquots_is_invalid() throws ValidationException, DataNotFoundException {
    doThrow(new Exception()).when(aliquotUpdateValidator).validate();
    participantLaboratoryServiceBean.updateAliquots(updateAliquotsDTO);
  }

  @Test
  public void deleteAliquot_should_call_method_validate() throws Exception {

    AliquotDeletionValidator aliquotDeletionValidator = Mockito.mock(AliquotDeletionValidator.class);
    whenNew(AliquotDeletionValidator.class).withArguments(ALIQUOT_CODE, aliquotDao, examUploader, examLotDao, transportationLotDao).thenReturn(aliquotDeletionValidator);

    aliquotDeletionValidator.validate();

    verify(aliquotDeletionValidator, Mockito.times(1)).validate();
  }

  @Test
  public void getLaboratoryExtraction_method_should_call_getLaboratoryExtraction_method() throws DataNotFoundException {
    participantLaboratoryServiceBean.getLaboratoryExtraction();

    verify(participantLaboratoryExtractionDao).getLaboratoryExtraction();
  }

  @Test()
  public void convertAliquotRole_method_should_evoke_convert_of_aliquotDao() throws DataNotFoundException, ValidationException {
    List<AliquotEvent> mockAliquotHistory = Arrays.asList(aliquotEvent);
    when(convertedAliquot.getAliquotHistory()).thenReturn(mockAliquotHistory);
    participantLaboratoryServiceBean.convertAliquotRole(convertedAliquot);
    verify(aliquotDao, Mockito.times(1)).convertAliquotRole(convertedAliquot);
  }

  @Test(expected = ValidationException.class)
  public void convertAliquotRole_method_throws_ValidationException_in_case_aliquotHistory_empty() throws DataNotFoundException, ValidationException {
    participantLaboratoryServiceBean.convertAliquotRole(convertedAliquot);
  }

}
