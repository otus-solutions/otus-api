package br.org.otus.participant;

import br.org.otus.participant.api.ParticipantContactFacade;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import br.org.otus.ResourceTestsParent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)

public class ParticipantContactResourceTest extends ResourceTestsParent {

  private static final String PARTICIPANT_CONTACT_ID = "5e0658135b4ff40f8916d2b5";
  private static final Long RN = 1234567L;

  @InjectMocks
  private ParticipantContactResource participantContactResource;
  @Mock
  private ParticipantContactFacade participantContactFacade;

  private ParticipantContact participantContact;
  private String participantContactJson;

  @Before
  public void setUp() {
    participantContact = new ParticipantContact();
    participantContactJson = ParticipantContact.serialize(participantContact);
  }

  @Test
  public void create_method_should_create_participantContact_by_participantContactFacade() {
    when(participantContactFacade.create(participantContactJson)).thenReturn(PARTICIPANT_CONTACT_ID);
    assertEquals(
      encapsulateExpectedResponse("\""+PARTICIPANT_CONTACT_ID+"\""),
      participantContactResource.create(participantContactJson));
  }

  @Test
  public void addNonMainEmail_method_should_addNonMainEmail_participantContact_by_participantContactFacade() {
    participantContactResource.addNonMainEmail(participantContactJson);
    verify(participantContactFacade, times(1)).addNonMainEmail(participantContactJson);
  }

  @Test
  public void addNonMainAddress_method_should_addNonMainAddress_participantContact_by_participantContactFacade() {
    participantContactResource.addNonMainAddress(participantContactJson);
    verify(participantContactFacade, times(1)).addNonMainAddress(participantContactJson);
  }

  @Test
  public void addNonMainPhoneNumber_method_should_addNonMainPhoneNumber_participantContact_by_participantContactFacade() {
    participantContactResource.addNonMainPhoneNumber(participantContactJson);
    verify(participantContactFacade, times(1)).addNonMainPhoneNumber(participantContactJson);
  }


  @Test
  public void updateEmail_method_should_updateEmail_participantContact_by_participantContactFacade() {
    participantContactResource.updateEmail(participantContactJson);
    verify(participantContactFacade, times(1)).updateEmail(participantContactJson);
  }

  @Test
  public void updateAddress_method_should_updateAddress_participantContact_by_participantContactFacade() {
    participantContactResource.updateAddress(participantContactJson);
    verify(participantContactFacade, times(1)).updateAddress(participantContactJson);
  }

  @Test
  public void updatePhoneNumber_method_should_updatePhoneNumber_participantContact_by_participantContactFacade() {
    participantContactResource.updatePhoneNumber(participantContactJson);
    verify(participantContactFacade, times(1)).updatePhoneNumber(participantContactJson);
  }

  @Test
  public void swapMainContact_method_should_swapMainContact_participantContact_by_participantContactFacade() {
    participantContactResource.swapMainContact(participantContactJson);
    verify(participantContactFacade, times(1)).swapMainContact(participantContactJson);
  }

  @Test
  public void delete_method_should_delete_participantContact_by_participantContactFacade() {
    participantContactResource.delete(PARTICIPANT_CONTACT_ID);
    verify(participantContactFacade, times(1)).delete(PARTICIPANT_CONTACT_ID);
  }

  @Test
  public void deleteNonMainContact_method_should_deleteNonMainContact_participantContact_by_participantContactFacade() {
    participantContactResource.deleteNonMainContact(participantContactJson);
    verify(participantContactFacade, times(1)).deleteNonMainContact(participantContactJson);
  }

  @Test
  public void getParticipantContact_method_should_get_participantContact_by_participantContactFacade() {
    when(participantContactFacade.getParticipantContact(PARTICIPANT_CONTACT_ID)).thenReturn(participantContact);
    assertEquals(
      encapsulateExpectedResponse(participantContactJson),
      participantContactResource.getParticipantContact(PARTICIPANT_CONTACT_ID));
  }

  @Test
  public void getParticipantContactByRecruitmentNumber_method_should_return_participantContact_by_RecruitmentNumber() {
    when(participantContactFacade.getParticipantContactByRecruitmentNumber(RN.toString())).thenReturn(participantContact);
    assertEquals(
      encapsulateExpectedResponse(participantContactJson),
      participantContactResource.getParticipantContactByRecruitmentNumber(RN.toString()));
  }
}
