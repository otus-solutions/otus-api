package org.ccem.otus.participant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantServiceBeanTest {
  private static final long RECRUIMENT_NUMBER = 3051442;
  private static final String PARTICIPANT_NAME = "Jose Otus";
  private static final String ACRONYM = "RS";
  @InjectMocks
  ParticipantServiceBean participantServiceBean;
  @Mock
  ParticipantDao participantDao;
  private Set<Participant> setParticipants;
  private Participant participant;
  private FieldCenter fieldCenter;
  private ArrayList<Participant> listParticipants;

  @Before
  public void setUp() {
    setParticipants = Mockito.spy(new HashSet<>());
    participant = new Participant(RECRUIMENT_NUMBER);
    participant.setName(PARTICIPANT_NAME);
    listParticipants = new ArrayList<>();
    listParticipants.add(participant);
    listParticipants.add(participant);
  }

  @Test
  public void method_create_with_SetParticipant_should_evocate_forEach_of_participants_list()
      throws ValidationException {
    participantServiceBean.create(setParticipants);
    verify(setParticipants, times(1)).forEach(anyObject());
  }

  @Test
  public void method_create_with_Participant_shoud_evocate_persist_of_ParticipantDao() throws ValidationException {
    participantServiceBean.create(participant);
    verify(participantDao, times(1)).persist(participant);
  }

  @Test
  public void method_list_should_execute_find_of_participantDao() {
    when(participantDao.find()).thenReturn(listParticipants);
    assertEquals(PARTICIPANT_NAME, participantServiceBean.list(fieldCenter).get(0).getName());
    verify(participantDao, times(0)).findByFieldCenter(fieldCenter);
  }
  
  @Test
  public void method_create_participants_validate_recruitmentNumber_should_inserts_successful() throws ValidationException {
    setParticipants.add(participant);
    when(participantDao.validateRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(null);
    participantServiceBean.create(setParticipants);
  }

  @Test
  public void method_create_participants_validate_recruitmentNumber_should_return_exception() throws ValidationException {
    setParticipants.add(participant);
    when(participantDao.validateRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    participantServiceBean.create(setParticipants);
  }
  
  @Test(expected = ValidationException.class)
  public void method_create_a_participant_validate_recruitmentNumber_should_return_exception() throws ValidationException {
    when(participantDao.validateRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    assertNull(participantServiceBean.create(participant));
  }

  @Test
  public void method_list_should_execute_findByFieldCenter_of_participantDao() {
    fieldCenter = new FieldCenter();
    fieldCenter.setAcronym(ACRONYM);
    participant.setFieldCenter(fieldCenter);
    when(participantDao.findByFieldCenter(fieldCenter)).thenReturn(listParticipants);
    assertEquals(participant.getName(), participantServiceBean.list(fieldCenter).get(0).getName());
    assertEquals(ACRONYM, participantServiceBean.list(fieldCenter).get(0).getFieldCenter().getAcronym());
    verify(participantDao, times(0)).find();
  }

  @Test
  public void method_getByRecruitmentNumber_should_return_participant() throws DataNotFoundException {
    when(participantDao.findByRecruitmentNumber(RECRUIMENT_NUMBER)).thenReturn(participant);
    assertEquals(PARTICIPANT_NAME, participantServiceBean.getByRecruitmentNumber(RECRUIMENT_NUMBER).getName());
  }

}
