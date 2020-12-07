package br.org.otus.laboratory.unattached.service;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeService;
import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.UnattachedLaboratoryDao;
import br.org.otus.laboratory.unattached.enums.UnattachedLaboratoryActions;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import org.bson.types.ObjectId;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnattachedLaboratoryServiceBean.class})
public class UnattachedLaboratoryServiceBeanTest {

  private static final String USER_EMAIL = "user@otus.com";
  private static final String FIELD_CENTER_ACRONYM = "RS";
  private static final String COLLECT_GROUP_DESCRIPTOR_NAME = "collect_group";
  private static final String LABORATORY_OID = "123";
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final Integer LAST_INSERTION = 1;
  private static final Integer PAGE = 1;
  private static final Integer QUANTITY_BY_PAGE = 5;
  private static final Integer LABORATORY_IDENTIFICATION = 0;

  private static final String TUBE_TYPE = "type";
  private static final String TUBE_MOMENT = "POST OVERLOAD";
  private static final String TUBE_CODE = "331005009";

  @InjectMocks
  private UnattachedLaboratoryServiceBean unattachedLaboratoryServiceBean;
  @Mock
  private TubeService tubeService;
  @Mock
  private UnattachedLaboratoryDao unattachedLaboratoryDao;
  @Mock
  private ParticipantLaboratoryDao participantLaboratoryDao;
  @Mock
  private ParticipantDao participantDao;
  @Mock
  private FieldCenterDao fieldCenterDao;

  private List<Tube> tubes;
  private UnattachedLaboratory unattachedLaboratory;
  private CollectGroupDescriptor collectGroupDescriptor = PowerMockito.spy(new CollectGroupDescriptor(COLLECT_GROUP_DESCRIPTOR_NAME, "", new HashSet<>()));
  private FieldCenter fieldCenter = new FieldCenter();
  private Participant participant = new Participant(RECRUITMENT_NUMBER);
  private ParticipantLaboratory participantLaboratory;

  @Before
  public void setUp() throws DataNotFoundException {
    tubes = new ArrayList<Tube>(){{
      add(new Tube(TUBE_TYPE, TUBE_MOMENT, TUBE_CODE, COLLECT_GROUP_DESCRIPTOR_NAME));
    }};
    unattachedLaboratory = PowerMockito.spy(new UnattachedLaboratory(LAST_INSERTION, FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME, tubes));
    fieldCenter.setAcronym(FIELD_CENTER_ACRONYM);
    participant.setFieldCenter(fieldCenter);
    participantLaboratory = new ParticipantLaboratory(new ObjectId(), RECRUITMENT_NUMBER, COLLECT_GROUP_DESCRIPTOR_NAME, tubes);
  }

  @Test
  public void create_method_should_add_user_and_persist_new_UnattachedLaboratory() throws Exception {
    when(tubeService.generateTubes(Mockito.any())).thenReturn(tubes);
    PowerMockito.whenNew(UnattachedLaboratory.class).withAnyArguments().thenReturn(unattachedLaboratory);
    unattachedLaboratoryServiceBean.create(USER_EMAIL, LAST_INSERTION, collectGroupDescriptor, fieldCenter);
    verify(unattachedLaboratory, Mockito.times(1)).addUserHistory(USER_EMAIL, UnattachedLaboratoryActions.CREATED);
    verify(unattachedLaboratoryDao, Mockito.times(1)).persist(unattachedLaboratory);
  }

  @Test
  public void find_method_should_return_ListUnattachedLaboratoryDTO() throws DataNotFoundException {
    ListUnattachedLaboratoryDTO listUnattachedLaboratoryDTO = new ListUnattachedLaboratoryDTO();
    when(unattachedLaboratoryDao.find(FIELD_CENTER_ACRONYM,COLLECT_GROUP_DESCRIPTOR_NAME, PAGE, QUANTITY_BY_PAGE)).thenReturn(listUnattachedLaboratoryDTO);
    assertEquals(listUnattachedLaboratoryDTO, unattachedLaboratoryServiceBean.find(FIELD_CENTER_ACRONYM,COLLECT_GROUP_DESCRIPTOR_NAME, PAGE, QUANTITY_BY_PAGE));
  }


  @Test(expected = ValidationException.class)
  public void attache_method_should_throw_ValidationException_in_case_participantLaboratoryDao_return_a_participant() throws DataNotFoundException, ValidationException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participantLaboratory);
    unattachedLaboratoryServiceBean.attache(RECRUITMENT_NUMBER, USER_EMAIL, LABORATORY_IDENTIFICATION, COLLECT_GROUP_DESCRIPTOR_NAME, FIELD_CENTER_ACRONYM);
  }

  @Test(expected = ValidationException.class)
  public void attache_method_should_throw_ValidationException_in_case_unattachedLaboratory_not_AvailableToAttache_and_last_history_action_is_ATTACHED() throws DataNotFoundException, ValidationException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenThrow(new DataNotFoundException());
    when(unattachedLaboratoryDao.find(LABORATORY_IDENTIFICATION)).thenReturn(unattachedLaboratory);
    when(unattachedLaboratory.getAvailableToAttache()).thenReturn(false);
    unattachedLaboratory.addUserHistory(USER_EMAIL, UnattachedLaboratoryActions.ATTACHED);
    unattachedLaboratoryServiceBean.attache(RECRUITMENT_NUMBER, USER_EMAIL, LABORATORY_IDENTIFICATION, COLLECT_GROUP_DESCRIPTOR_NAME, FIELD_CENTER_ACRONYM);
  }

  @Test(expected = ValidationException.class)
  public void attache_method_should_throw_ValidationException_in_case_unattachedLaboratory_not_AvailableToAttache_and_last_history_action_is_not_ATTACHED() throws DataNotFoundException, ValidationException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenThrow(new DataNotFoundException());
    when(unattachedLaboratoryDao.find(LABORATORY_IDENTIFICATION)).thenReturn(unattachedLaboratory);
    when(unattachedLaboratory.getAvailableToAttache()).thenReturn(false);
    unattachedLaboratory.addUserHistory(USER_EMAIL, UnattachedLaboratoryActions.CREATED);
    unattachedLaboratoryServiceBean.attache(RECRUITMENT_NUMBER, USER_EMAIL, LABORATORY_IDENTIFICATION, COLLECT_GROUP_DESCRIPTOR_NAME, FIELD_CENTER_ACRONYM);
  }

  @Test(expected = ValidationException.class)
  public void attache_method_should_throw_ValidationException_in_case_unattachedLaboratory_AvailableToAttache_but_field_center_does_not_match_with_participant() throws DataNotFoundException, ValidationException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenThrow(new DataNotFoundException());
    when(unattachedLaboratoryDao.find(LABORATORY_IDENTIFICATION)).thenReturn(unattachedLaboratory);
    when(unattachedLaboratory.getAvailableToAttache()).thenReturn(true);
    when(unattachedLaboratory.getFieldCenterAcronym()).thenReturn(FIELD_CENTER_ACRONYM+"X");
    unattachedLaboratoryServiceBean.attache(RECRUITMENT_NUMBER, USER_EMAIL, LABORATORY_IDENTIFICATION, COLLECT_GROUP_DESCRIPTOR_NAME, FIELD_CENTER_ACRONYM);
  }

  @Test(expected = ValidationException.class)
  public void attache_method_should_throw_ValidationException_in_case_unattachedLaboratory_AvailableToAttache_but_collectGroupName_does_not_match_with_participant() throws DataNotFoundException, ValidationException {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenThrow(new DataNotFoundException());
    when(unattachedLaboratoryDao.find(LABORATORY_IDENTIFICATION)).thenReturn(unattachedLaboratory);
    when(unattachedLaboratory.getAvailableToAttache()).thenReturn(true);
    when(unattachedLaboratory.getCollectGroupName()).thenReturn(COLLECT_GROUP_DESCRIPTOR_NAME+"X");
    unattachedLaboratoryServiceBean.attache(RECRUITMENT_NUMBER, USER_EMAIL, LABORATORY_IDENTIFICATION, COLLECT_GROUP_DESCRIPTOR_NAME, FIELD_CENTER_ACRONYM);
  }

  @Test
  public void attache_method_should_throw_ValidationException_in_case_unattachedLaboratory_AvailableToAttache_and_match_with_participant() throws Exception {
    when(participantLaboratoryDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenThrow(new DataNotFoundException());
    when(unattachedLaboratoryDao.find(LABORATORY_IDENTIFICATION)).thenReturn(unattachedLaboratory);
    when(unattachedLaboratory.getAvailableToAttache()).thenReturn(true);
    when(participantDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
    when(fieldCenterDao.fetchByAcronym(FIELD_CENTER_ACRONYM)).thenReturn(fieldCenter);
    PowerMockito.whenNew(ParticipantLaboratory.class).withAnyArguments().thenReturn(participantLaboratory);

    unattachedLaboratoryServiceBean.attache(RECRUITMENT_NUMBER, USER_EMAIL, LABORATORY_IDENTIFICATION, COLLECT_GROUP_DESCRIPTOR_NAME, FIELD_CENTER_ACRONYM);

    verify(participantLaboratoryDao, Mockito.times(1)).persist(participantLaboratory);
    verify(unattachedLaboratory, Mockito.times(1)).disable();
    verify(unattachedLaboratory, Mockito.times(1)).addUserHistory(USER_EMAIL, UnattachedLaboratoryActions.ATTACHED);
    verify(unattachedLaboratoryDao,Mockito.times(1)).update(unattachedLaboratory.getIdentification(), unattachedLaboratory);
  }


  @Test
  public void discard_method_should_disable_unattachedLaboratory_add_user_and_invoke_unattachedLaboratoryDao_update_method() throws DataNotFoundException {
    when(unattachedLaboratoryDao.findById(LABORATORY_OID)).thenReturn(unattachedLaboratory);
    when(unattachedLaboratory.getAvailableToAttache()).thenReturn(true);
    unattachedLaboratoryServiceBean.discard(USER_EMAIL, LABORATORY_OID);
    verify(unattachedLaboratory, Mockito.times(1)).disable();
    verify(unattachedLaboratory, Mockito.times(1)).addUserHistory(USER_EMAIL, UnattachedLaboratoryActions.DISCARDED);
    verify(unattachedLaboratoryDao, Mockito.times(1)).update(unattachedLaboratory.getIdentification(), unattachedLaboratory);
  }

  @Test
  public void discard_method_should_NOT_disable_unattachedLaboratory_add_user_nether_invoke_unattachedLaboratoryDao_update_method() throws DataNotFoundException {
    when(unattachedLaboratoryDao.findById(LABORATORY_OID)).thenReturn(unattachedLaboratory);
    when(unattachedLaboratory.getAvailableToAttache()).thenReturn(false);
    unattachedLaboratoryServiceBean.discard(USER_EMAIL, LABORATORY_OID);
    verify(unattachedLaboratory, Mockito.times(0)).disable();
    verify(unattachedLaboratory, Mockito.times(0)).addUserHistory(USER_EMAIL, UnattachedLaboratoryActions.DISCARDED);
    verify(unattachedLaboratoryDao, Mockito.times(0)).update(unattachedLaboratory.getIdentification(), unattachedLaboratory);
  }

  @Test
  public void findById_method_should_return_UnattachedLaboratory() throws DataNotFoundException {
    when(unattachedLaboratoryDao.findById(LABORATORY_OID)).thenReturn(unattachedLaboratory);
    assertEquals(unattachedLaboratory, unattachedLaboratoryServiceBean.findById(LABORATORY_OID));
  }

  @Test
  public void findByIdentification_method_should_return_UnattachedLaboratory() throws DataNotFoundException {
    when(unattachedLaboratoryDao.find(LABORATORY_IDENTIFICATION)).thenReturn(unattachedLaboratory);
    assertEquals(unattachedLaboratory, unattachedLaboratoryServiceBean.findByIdentification(LABORATORY_IDENTIFICATION));
  }
}
