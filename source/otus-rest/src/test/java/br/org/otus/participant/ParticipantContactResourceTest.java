package br.org.otus.participant;

import br.org.otus.participant.api.ParticipantContactFacade;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
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

public class ParticipantContactResourceTest {
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

//  @Test
//  public void create_method_should_create_participantContact_by_participantContactFacade() {
//    when(participantContactFacade.create(participantContactJson)).thenReturn(PARTICIPANT_CONTACT_ID);
//    assertEquals(
//      encapsulateExpectedResponse("\""+PARTICIPANT_CONTACT_ID+"\""),
//      participantContactResource.create(participantContactJson));
//  }
//
//  @Test
//  public void updateMainContact_method_should_updateMainContact_participantContact_by_participantContactFacade() {
//    participantContactResource.updateMainContact(participantContactJson);
//    verify(participantContactFacade, times(1)).updateMainContact(participantContactJson);
//  }
//
//  @Test
//  public void addSecondaryContact_method_should_addSecondaryContact_participantContact_by_participantContactFacade() {
//    participantContactResource.addSecondaryContact(participantContactJson);
//    verify(participantContactFacade, times(1)).addSecondaryContact(participantContactJson);
//  }
//
//  @Test
//  public void updateSecondaryContact_method_should_updateSecondaryContact_participantContact_by_participantContactFacade() {
//    participantContactResource.updateSecondaryContact(participantContactJson);
//    verify(participantContactFacade, times(1)).updateSecondaryContact(participantContactJson);
//  }
//
//  @Test
//  public void swapMainContactWithSecondary_method_should_swapMainContactWithSecondary_participantContact_by_participantContactFacade() {
//    participantContactResource.swapMainContactWithSecondary(participantContactJson);
//    verify(participantContactFacade, times(1)).swapMainContactWithSecondary(participantContactJson);
//  }
//
//  @Test
//  public void delete_method_should_delete_participantContact_by_participantContactFacade() {
//    participantContactResource.delete(PARTICIPANT_CONTACT_ID);
//    verify(participantContactFacade, times(1)).delete(PARTICIPANT_CONTACT_ID);
//  }
//
//  @Test
//  public void deleteSecondaryContact_method_should_deleteSecondaryContact_participantContact_by_participantContactFacade() {
//    participantContactResource.deleteSecondaryContact(participantContactJson);
//    verify(participantContactFacade, times(1)).deleteSecondaryContact(participantContactJson);
//  }
//
//  @Test
//  public void get_method_should_get_participantContact_by_participantContactFacade() {
//    when(participantContactFacade.get(PARTICIPANT_CONTACT_ID)).thenReturn(participantContact);
//    assertEquals(
//      encapsulateExpectedResponse(participantContactJson),
//      participantContactResource.get(PARTICIPANT_CONTACT_ID));
//  }
//
//  @Test
//  public void getByRecruitmentNumber_method_should_return_participantContact_by_RecruitmentNumber() {
//    when(participantContactFacade.getByRecruitmentNumber(RN.toString())).thenReturn(participantContact);
//    assertEquals(
//      encapsulateExpectedResponse(participantContactJson),
//      participantContactResource.getByRecruitmentNumber(RN.toString()));
//  }

  private String encapsulateExpectedResponse(String data) {
    return "{\"data\":" + data + "}";
  }
}
