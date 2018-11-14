package br.org.otus.laboratory.participant;

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
import br.org.otus.laboratory.participant.validators.ParticipantLaboratoryValidator;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ParticipantLaboratoryServiceBean.class)
public class ParticipantLaboratoryServiceBeanTest {

  // TODO: This test needs to be refactored

  @InjectMocks
  private ParticipantLaboratoryServiceBean injectMocks = PowerMockito.spy(new ParticipantLaboratoryServiceBean());
  @Mock
  private ParticipantLaboratoryServiceBean participantLaboratoryService;
  @Mock
  private ParticipantDao participantDao;
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

    when(participantLaboratoryService.getLaboratory(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);
  }

  @Test
  public void hasLaboratory_method_should_return_true() throws DataNotFoundException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);
    assertTrue(injectMocks.hasLaboratory(RECRUIMENT_NUMBER));
  }

  @Test
  public void hasLaboratory_method_should_return_false() throws DataNotFoundException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenThrow(new DataNotFoundException());
    assertFalse(injectMocks.hasLaboratory(RECRUIMENT_NUMBER));
  }

  @Test
  public void getLaboratory_method_should_call_participantLaboratory_setAliquots() throws DataNotFoundException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);

    List<Aliquot> aliquotList = new ArrayList<>();
    aliquotList.add(ALIQUOT);
    when(aliquotService.getAliquots(RECRUIMENT_NUMBER)).thenReturn(aliquotList);

    List<SimpleAliquot> simpleAliquotList = new ArrayList<>();
    simpleAliquotList.add(SIMPLE_ALIQUOT);

    participantLaboratory = injectMocks.getLaboratory(RECRUIMENT_NUMBER);

    assertEquals(simpleAliquotList, participantLaboratory.getAliquotsList());
  }

  @Test
  public void create_method_should_persist_laboratory() throws Exception {
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    when(groupRaffle.perform(participant)).thenReturn(collectGroup);
    when(tubeService.generateTubes(TubeSeed.generate(participant, collectGroup))).thenReturn(participantLaboratory.getTubes());
    whenNew(ParticipantLaboratory.class).withAnyArguments().thenReturn(participantLaboratory);
    injectMocks.create(RECRUIMENT_NUMBER);
    verify(participantLaboratoryDao, Mockito.times(1)).persist(participantLaboratory);
  }

  @Test
  public void updateAliquots_method_should_call_method_validate() throws Exception {
    doReturn(participantLaboratory).when(injectMocks, "getLaboratory", RECRUIMENT_NUMBER);
    whenNew(AliquotUpdateValidator.class).withArguments(updateAliquotsDTO,aliquotDao,participantLaboratory).thenReturn(aliquotUpdateValidator);
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    injectMocks.updateAliquots(updateAliquotsDTO);
    verify(aliquotUpdateValidator).validate();
  }

  @Test
  public void UpdateAliquots_method_when_executed_with_success_should_call_method_aliquotDao_persist() throws Exception {
    doReturn(participantLaboratory).when(injectMocks, "getLaboratory", RECRUIMENT_NUMBER);
    whenNew(AliquotUpdateValidator.class).withArguments(updateAliquotsDTO,aliquotDao,participantLaboratory).thenReturn(aliquotUpdateValidator);
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    injectMocks.updateAliquots(updateAliquotsDTO);
    verify(aliquotDao, times(1)).persist(any());
  }

  @Test(expected = Exception.class)
  public void UpdateAliquots_method_should_throw_an_exception_when_aliquots_is_invalid() throws ValidationException, DataNotFoundException {

    doThrow(new Exception()).when(aliquotUpdateValidator).validate();
    participantLaboratoryService.updateAliquots(updateAliquotsDTO);
  }

  @Test
  public void deleteAliquot_should_call_method_validate() throws Exception {

    AliquotDeletionValidator aliquotDeletionValidator = Mockito.mock(AliquotDeletionValidator.class);
    PowerMockito.whenNew(AliquotDeletionValidator.class).withArguments(ALIQUOT_CODE,aliquotDao, examUploader, examLotDao, transportationLotDao).thenReturn(aliquotDeletionValidator);

    aliquotDeletionValidator.validate();

    verify(aliquotDeletionValidator, Mockito.times(1)).validate();
  }

}
