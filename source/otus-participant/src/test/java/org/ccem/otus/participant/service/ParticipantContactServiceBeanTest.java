package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactPositionOptions;
import org.ccem.otus.participant.persistence.ParticipantContactDao;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ParticipantContactServiceBeanTest {

  private static final ObjectId PARTICIPANT_CONTACT_OID = new ObjectId("5c7400d2d767afded0d84dcf");
  private static final Long RN = 1234567L;
  private static final String MAIN_POSITION_NAME = ParticipantContactPositionOptions.MAIN.getName();
  private static final String NON_MAIN_POSITION_NAME = ParticipantContactPositionOptions.SECOND.getName();

  @InjectMocks
  private ParticipantContactServiceBean participantContactServiceBean;
  @Mock
  private ParticipantContactDao participantContactDao;

  private ParticipantContactDto participantContactDto  = PowerMockito.spy(new ParticipantContactDto());
  private ParticipantContact participantContact = PowerMockito.spy(new ParticipantContact());


  @Test
  public void create_method_should_return_ObjectID_in_case_success_persist() throws DataFormatException {
    when(participantContact.hasAllMainContacts()).thenReturn(true);
    when(participantContactDao.create(participantContact)).thenReturn(PARTICIPANT_CONTACT_OID);
    assertEquals(PARTICIPANT_CONTACT_OID, participantContactServiceBean.create(participantContact));
  }

  @Test(expected = DataFormatException.class)
  public void create_method_should_throw_DataFormatException_in_case_particpantContact_has_not_all_main_contacts() throws DataFormatException {
    participantContactServiceBean.create(participantContact);
  }

  @Test
  public void addNonMainEmail_method_invoke_addNonMainEmail_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    doReturn(NON_MAIN_POSITION_NAME).when(participantContactDto).getPosition();
    participantContactServiceBean.addNonMainEmail(participantContactDto);
    verify(participantContactDao, times(1)).addNonMainEmail(participantContactDto);
  }

  @Test
  public void addNonMainAddress_method_invoke_addNonMainAddress_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    doReturn(NON_MAIN_POSITION_NAME).when(participantContactDto).getPosition();
    participantContactServiceBean.addNonMainAddress(participantContactDto);
    verify(participantContactDao, times(1)).addNonMainAddress(participantContactDto);
  }

  @Test
  public void addNonMainPhoneNumber_method_invoke_addNonMainPhoneNumber_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    doReturn(NON_MAIN_POSITION_NAME).when(participantContactDto).getPosition();
    participantContactServiceBean.addNonMainPhoneNumber(participantContactDto);
    verify(participantContactDao, times(1)).addNonMainPhoneNumber(participantContactDto);
  }

  @Test(expected = DataFormatException.class)
  public void addNonMainEmail_method_should_throw_DataFormatException_in_invalid_participantContactDto() throws Exception {
    doReturn(false).when(participantContactDto).isValid();
    participantContactServiceBean.addNonMainEmail(participantContactDto);
    verify(participantContactDao, times(0)).addNonMainEmail(participantContactDto);
  }

  @Test(expected = DataFormatException.class)
  public void addNonMainEmail_method_should_throw_DataFormatException_in_case_non_main_position_at_participantContactDto() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    doReturn(MAIN_POSITION_NAME).when(participantContactDto).getPosition();
    participantContactServiceBean.addNonMainEmail(participantContactDto);
    verify(participantContactDao, times(0)).addNonMainEmail(participantContactDto);
  }

  @Test
  public void updateEmail_method_invoke_updateEmail_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    participantContactServiceBean.updateEmail(participantContactDto);
    verify(participantContactDao, times(1)).updateEmail(participantContactDto);
  }

  @Test
  public void updateAddress_method_invoke_updateAddress_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    participantContactServiceBean.updateAddress(participantContactDto);
    verify(participantContactDao, times(1)).updateAddress(participantContactDto);
  }

  @Test
  public void updatePhoneNumber_method_invoke_updatePhoneNumber_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    participantContactServiceBean.updatePhoneNumber(participantContactDto);
    verify(participantContactDao, times(1)).updatePhoneNumber(participantContactDto);
  }

  @Test
  public void swapMainContact_method_invoke_swapMainContact_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    participantContactServiceBean.swapMainContact(participantContactDto);
    verify(participantContactDao, times(1)).swapMainContact(participantContactDto);
  }

  @Test
  public void delete_method_invoke_delete_from_participantContactDao() throws Exception {
    participantContactServiceBean.delete(PARTICIPANT_CONTACT_OID);
    verify(participantContactDao, times(1)).delete(PARTICIPANT_CONTACT_OID);
  }

  @Test
  public void deleteNonMainContact_method_invoke_deleteNonMainContact_from_participantContactDao() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    doReturn(NON_MAIN_POSITION_NAME).when(participantContactDto).getPosition();
    participantContactServiceBean.deleteNonMainContact(participantContactDto);
    verify(participantContactDao, times(1)).deleteNonMainContact(participantContactDto);
  }

  @Test
  public void get_method_invoke_get_from_participantContactDao() throws Exception {
    participantContactServiceBean.getParticipantContact(PARTICIPANT_CONTACT_OID);
    verify(participantContactDao, times(1)).getParticipantContact(PARTICIPANT_CONTACT_OID);
  }

  @Test
  public void getByRecruitmentNumber_method_invoke_getByRecruitmentNumber_from_participantContactDao() throws Exception {
    participantContactServiceBean.getParticipantContactByRecruitmentNumber(RN);
    verify(participantContactDao, times(1)).getParticipantContactByRecruitmentNumber(RN);
  }
}
