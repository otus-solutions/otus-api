package org.ccem.otus.participant.service;

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
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class RecruitmentNumberServiceBeanTest {
  private static final long RECRUIMENT_NUMBER = 4000123L;
  private static final String ACRONYM = "RS";
  private static final Integer CENTER_CODE = 4;

  @InjectMocks
  private RecruitmentNumberServiceBean recruitmentNumberService;

  @Mock
  ParticipantDao participantDao;

  @Mock
  FieldCenterDao fieldCenterDao;

  private FieldCenter fieldCenter;
  private Participant participant;

  @Before
  public void setUp() {
    fieldCenter = new FieldCenter();
    fieldCenter.setAcronym(ACRONYM);
    fieldCenter.setCode(CENTER_CODE);

    participant = new Participant(RECRUIMENT_NUMBER);

    when(fieldCenterDao.fetchByAcronym(ACRONYM)).thenReturn(fieldCenter);
  }

  @Test
  public void get_should_generate_a_new_recruitment_number_when_no_previous_inserted_participant_is_found() throws ValidationException, DataNotFoundException {
    when(participantDao.getLastInsertion(Mockito.any()))
        .thenThrow(new DataNotFoundException());

    Long result = recruitmentNumberService.get(ACRONYM);

    assertEquals(java.util.Optional.of(4000001L).get(), result);
  }

  @Test
  public void get_should_the_next_recruitment_number_when_a_previous_inserted_participant_is_found() throws ValidationException, DataNotFoundException {
    Long aLong = 4000123L;
    when(participantDao.getLastInsertion(Mockito.any()))
        .thenReturn(participant);
    Long result = recruitmentNumberService.get(ACRONYM);

    assertEquals(java.util.Optional.of(RECRUIMENT_NUMBER + 1).get(), result);
  }

  @Test
  public void should_increase_the_size_of_the_recruitment_number_when_necessary() throws ValidationException, DataNotFoundException {
    Long aLong = 4000123L;
    Participant participant = new Participant(4999999L);
    when(participantDao.getLastInsertion(Mockito.any()))
        .thenReturn(participant);

    Long result = recruitmentNumberService.get(ACRONYM);

    assertEquals(java.util.Optional.of(41000000L).get(), result);
  }

  @Test(expected = DataNotFoundException.class)
  public void get_should_throw_an_exception_when_field_center_is_not_found() throws ValidationException, DataNotFoundException {
    when(fieldCenterDao.fetchByAcronym("IA"))
        .thenReturn(null);
    recruitmentNumberService.get("IA");

  }

  @Test(expected = ValidationException.class)
  public void validate_should_throw_an_ValidationException_when_recruitment_number_is_null() throws ValidationException {
    recruitmentNumberService.validate(fieldCenter,null);
  }

  @Test(expected = ValidationException.class)
  public void validate_should_throw_an_ValidationException_when_recruitment_number_is_invalid() throws ValidationException {
    fieldCenter.setCode(1);
    recruitmentNumberService.validate(fieldCenter,(long) 2);
  }

  @Test
  public void validate_should_throw_nothing_when_recruitment_number_is_valid() {
    fieldCenter.setCode(1);
    try {
      recruitmentNumberService.validate(fieldCenter,(long) 1);
    } catch (ValidationException e) {
      fail();
    }
  }
}