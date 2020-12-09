package br.org.otus.laboratory;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupConfiguration;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupRaffle;
import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.service.UnattachedLaboratoryService;
import br.org.otus.response.exception.HttpResponseException;
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
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class UnattachedLaboratoryFacadeTest {

  private static final String USER_EMAIL = "user@otus.com";
  private static final String FIELD_CENTER_ACRONYM = "RS";
  private static final String COLLECT_GROUP_DESCRIPTOR_NAME = "collect_group";
  private static final String LABORATORY_OID = "123";
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final Integer LAST_INSERTION = 1;
  private static final Integer PAGE = 1;
  private static final Integer QUANTITY_BY_PAGE = 5;
  private static final Integer LABORATORY_IDENTIFICATION = 0;

  @InjectMocks
  private UnattachedLaboratoryFacade unattachedLaboratoryFacade;
  @Mock
  private UnattachedLaboratoryService unattachedLaboratoryService;
  @Mock
  private FieldCenterDao fieldCenterDao;
  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;
  @Mock
  private ParticipantDao participantDao;
  @Mock
  private CollectGroupRaffle groupRaffle;


  private CollectGroupDescriptor collectGroupDescriptor = PowerMockito.spy(new CollectGroupDescriptor(COLLECT_GROUP_DESCRIPTOR_NAME, "", null));
  private FieldCenter fieldCenter = new FieldCenter();
  private Participant participant = PowerMockito.spy(new Participant(RECRUITMENT_NUMBER));


  @Before
  public void setUp() throws DataNotFoundException {
    fieldCenter.setAcronym(FIELD_CENTER_ACRONYM);
    participant.setFieldCenter(fieldCenter);
  }


  @Test
  public void getLaboratoryConfiguration_method_should_invoke_service_method() throws DataNotFoundException {
    LaboratoryConfiguration laboratoryConfiguration = new LaboratoryConfiguration();
    when(laboratoryConfigurationService.getLaboratoryConfiguration()).thenReturn(laboratoryConfiguration);

    CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(new HashSet<CollectGroupDescriptor>(){{
      add(collectGroupDescriptor);
    }});
    Whitebox.setInternalState(laboratoryConfiguration, "collectGroupConfiguration", collectGroupConfiguration);

    when(fieldCenterDao.fetchByAcronym(FIELD_CENTER_ACRONYM)).thenReturn(fieldCenter);
    when(laboratoryConfigurationService.updateUnattachedLaboratoryLastInsertion()).thenReturn(LAST_INSERTION);

    unattachedLaboratoryFacade.create(USER_EMAIL, FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME);
    verify(unattachedLaboratoryService, Mockito.times(1)).create(USER_EMAIL, LAST_INSERTION, collectGroupDescriptor, fieldCenter);
  }

  @Test(expected = HttpResponseException.class)
  public void getLaboratoryConfiguration_method_should_handle_DataNotFoundException_from_unattachedLaboratoryService_creat() throws DataNotFoundException {
    LaboratoryConfiguration laboratoryConfiguration = new LaboratoryConfiguration();
    when(laboratoryConfigurationService.getLaboratoryConfiguration()).thenReturn(laboratoryConfiguration);

    CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(new HashSet<CollectGroupDescriptor>(){{
      add(collectGroupDescriptor);
    }});
    Whitebox.setInternalState(laboratoryConfiguration, "collectGroupConfiguration", collectGroupConfiguration);

    when(fieldCenterDao.fetchByAcronym(FIELD_CENTER_ACRONYM)).thenReturn(fieldCenter);
    when(laboratoryConfigurationService.updateUnattachedLaboratoryLastInsertion()).thenReturn(LAST_INSERTION);

    doThrow(new DataNotFoundException()).when(unattachedLaboratoryService).create(USER_EMAIL, LAST_INSERTION, collectGroupDescriptor, fieldCenter);

    unattachedLaboratoryFacade.create(USER_EMAIL, FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME);
  }

  @Test(expected = HttpResponseException.class)
  public void getLaboratoryConfiguration_method_should_handle_NoSuchElementException() throws DataNotFoundException {
    LaboratoryConfiguration laboratoryConfiguration = new LaboratoryConfiguration();
    when(laboratoryConfigurationService.getLaboratoryConfiguration()).thenReturn(laboratoryConfiguration);

    CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(new HashSet<>());
    Whitebox.setInternalState(laboratoryConfiguration, "collectGroupConfiguration", collectGroupConfiguration);

    unattachedLaboratoryFacade.create(USER_EMAIL, FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME);
  }

  @Test(expected = HttpResponseException.class)
  public void getLaboratoryConfiguration_method_should_handle_DataNotFoundException_from_getLaboratoryConfiguration() throws DataNotFoundException {
    when(laboratoryConfigurationService.getLaboratoryConfiguration()).thenThrow(new DataNotFoundException());
    unattachedLaboratoryFacade.create(USER_EMAIL, FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME);
  }


  @Test
  public void find_method_should_return_ListUnattachedLaboratoryDTO_instance() throws DataNotFoundException {
    ListUnattachedLaboratoryDTO dto = new ListUnattachedLaboratoryDTO();
    when(unattachedLaboratoryService.find(FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME, PAGE, QUANTITY_BY_PAGE)).thenReturn(dto);
    assertEquals(dto,
      unattachedLaboratoryFacade.find(FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME, PAGE, QUANTITY_BY_PAGE));
  }

  @Test(expected = HttpResponseException.class)
  public void find_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    when(unattachedLaboratoryService.find(FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME, PAGE, QUANTITY_BY_PAGE)).thenThrow(new DataNotFoundException());
    unattachedLaboratoryFacade.find(FIELD_CENTER_ACRONYM, COLLECT_GROUP_DESCRIPTOR_NAME, PAGE, QUANTITY_BY_PAGE);
  }


  @Test
  public void attache_method_should_call_unattachedLaboratoryService_method() throws DataNotFoundException, ValidationException {
    when(participantDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
    when(participant.isIdentified()).thenReturn(true);
    when(groupRaffle.perform(participant)).thenReturn(collectGroupDescriptor);
    unattachedLaboratoryFacade.attache(USER_EMAIL, LABORATORY_IDENTIFICATION, RECRUITMENT_NUMBER);
    verify(unattachedLaboratoryService, Mockito.times(1)).attache(RECRUITMENT_NUMBER,
      USER_EMAIL, LABORATORY_IDENTIFICATION, collectGroupDescriptor.getName(), FIELD_CENTER_ACRONYM);
  }

  @Test(expected = HttpResponseException.class)
  public void attache_method_should_handle_DataNotFound_in_case_participant_not_found() throws DataNotFoundException {
    when(participantDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenThrow(new DataNotFoundException());
    unattachedLaboratoryFacade.attache(USER_EMAIL, LABORATORY_IDENTIFICATION, RECRUITMENT_NUMBER);
  }

  @Test(expected = HttpResponseException.class)
  public void attache_method_should_throw_HttpResponseException_in_case_participant_not_identified() throws DataNotFoundException {
    when(participantDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
    when(participant.isIdentified()).thenReturn(false);
    unattachedLaboratoryFacade.attache(USER_EMAIL, LABORATORY_IDENTIFICATION, RECRUITMENT_NUMBER);
  }

  //@Test(expected = HttpResponseException.class)
  public void attache_method_should_handle_ValidationException() throws DataNotFoundException, ValidationException {
    when(participantDao.findByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
    when(participant.isIdentified()).thenReturn(true);
    when(groupRaffle.perform(participant)).thenReturn(collectGroupDescriptor);

    doThrow(new ValidationException(new Throwable(""))).when(unattachedLaboratoryService).attache(
      RECRUITMENT_NUMBER, USER_EMAIL, LABORATORY_IDENTIFICATION, collectGroupDescriptor.getName(), FIELD_CENTER_ACRONYM);

    unattachedLaboratoryFacade.attache(USER_EMAIL, LABORATORY_IDENTIFICATION, RECRUITMENT_NUMBER);
  }


  @Test
  public void discard_method_should_call_service_discard_method() throws DataNotFoundException {
    unattachedLaboratoryFacade.discard(USER_EMAIL, LABORATORY_OID);
    verify(unattachedLaboratoryService, Mockito.times(1)).discard(USER_EMAIL, LABORATORY_OID);
  }

  @Test(expected = HttpResponseException.class)
  public void discard_method_should_handle_DataNotFound() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(unattachedLaboratoryService).discard(USER_EMAIL, LABORATORY_OID);
    unattachedLaboratoryFacade.discard(USER_EMAIL, LABORATORY_OID);
  }


  @Test
  public void findById_method_should_call_service_findById_method() throws DataNotFoundException {
    unattachedLaboratoryFacade.findById(LABORATORY_OID);
    verify(unattachedLaboratoryService, Mockito.times(1)).findById(LABORATORY_OID);
  }

  @Test(expected = HttpResponseException.class)
  public void findById_method_should_handle_DataNotFound() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(unattachedLaboratoryService).findById(LABORATORY_OID);
    unattachedLaboratoryFacade.findById(LABORATORY_OID);
  }


  @Test
  public void findByIdentification_method_should_call_service_findByIdentification_method() throws DataNotFoundException {
    unattachedLaboratoryFacade.findByIdentification(LABORATORY_IDENTIFICATION);
    verify(unattachedLaboratoryService, Mockito.times(1)).findByIdentification(LABORATORY_IDENTIFICATION);
  }

  @Test(expected = HttpResponseException.class)
  public void findByIdentification_method_should_handle_DataNotFound() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(unattachedLaboratoryService).findByIdentification(LABORATORY_IDENTIFICATION);
    unattachedLaboratoryFacade.findByIdentification(LABORATORY_IDENTIFICATION);
  }

}
