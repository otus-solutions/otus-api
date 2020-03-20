package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.ParticipantContactDao;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

//@RunWith(PowerMockRunner.class)
public class ParticipantContactServiceBeanTest {

  private static final ObjectId PARTICIPANT_CONTACT_OID = new ObjectId("5c7400d2d767afded0d84dcf");
  private static final Long RN = 1234567L;

  //@InjectMocks
  private ParticipantContactServiceBean participantContactServiceBean;
  //@Mock
  private ParticipantContactDao participantContactDao;

  private ParticipantContactDto participantContactDto  = PowerMockito.spy(new ParticipantContactDto());
  private ParticipantContact participantContact = new ParticipantContact();

  //@Test
  public void create_method_should_return_ObjectID_in_case_success_persist(){
//    when(participantContactDao.create(participantContact)).thenReturn(PARTICIPANT_CONTACT_OID);
//    assertEquals(PARTICIPANT_CONTACT_OID, participantContactServiceBean.create(participantContact));
  }


  //@Test
  public void addNonMainEmail_method_invoke_addNonMainEmail_from_participantContactDto() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    participantContactServiceBean.addNonMainEmail(participantContactDto);
    verify(participantContactDao, times(1)).addNonMainEmail(participantContactDto);
  }

  //@Test
  public void swapMainContactWithSecondary_method_invoke_swapMainContactWithSecondary_from_participantContactDto() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    participantContactServiceBean.swapMainContactWithSecondary(participantContactDto);
    verify(participantContactDao, times(1)).swapMainContactWithSecondary(participantContactDto);
  }

  //@Test
  public void delete_method_invoke_delete_from_participantContactDto() throws Exception {
    participantContactServiceBean.delete(PARTICIPANT_CONTACT_OID);
    verify(participantContactDao, times(1)).delete(PARTICIPANT_CONTACT_OID);
  }

  //@Test
  public void deleteNonMainContact_method_invoke_deleteNonMainContact_from_participantContactDto() throws Exception {
    doReturn(true).when(participantContactDto).isValid();
    participantContactServiceBean.deleteNonMainContact(participantContactDto);
    verify(participantContactDao, times(1)).deleteNonMainContact(participantContactDto);
  }

  //@Test
  public void get_method_invoke_get_from_participantContactDto() throws Exception {
    participantContactServiceBean.getParticipantContact(PARTICIPANT_CONTACT_OID);
    verify(participantContactDao, times(1)).getParticipantContact(PARTICIPANT_CONTACT_OID);
  }

  //@Test
  public void getByRecruitmentNumber_method_invoke_getByRecruitmentNumber_from_participantContactDto() throws Exception {
    participantContactServiceBean.getParticipantContactByRecruitmentNumber(RN);
    verify(participantContactDao, times(1)).getParticipantContactByRecruitmentNumber(RN);
  }
}
