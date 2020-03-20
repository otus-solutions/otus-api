package br.org.otus.participant.api;

import br.org.otus.response.exception.HttpResponseException;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;
import org.ccem.otus.participant.service.ParticipantContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class ParticipantContactFacadeTest {

  private static final String PARTICIPANT_CONTACT_ID = "5e0658135b4ff40f8916d2b5";
  private static final Long RN = 1234567L;

  @InjectMocks
  private ParticipantContactFacade participantContactFacade;
  @Mock
  private ParticipantContactService participantContactService;

  private ParticipantContact participantContact;
  private ObjectId participantContactOID;
  private String participantContactJson;
  private DataNotFoundException dataNotFoundException;

  @Before
  public void setUp(){
    participantContact = new ParticipantContact();
    participantContactOID = new ObjectId(PARTICIPANT_CONTACT_ID);
    participantContactJson = ParticipantContact.serialize(participantContact);
    dataNotFoundException = PowerMockito.spy(new DataNotFoundException());
  }

//  @Test
//  public void create_method_should_invoke_create_from_participantContactService() {
//    when(participantContactService.create(Mockito.any())).thenReturn(participantContactOID);
//    assertEquals(PARTICIPANT_CONTACT_ID, participantContactFacade.create(participantContactJson));
//  }
//
//  @Test
//  public void updateMainContact_method_should_invoke_updateMainContact_from_participantContactService() throws Exception {
//    participantContactFacade.updateMainContact(participantContactJson);
//    verify(participantContactService, times(1)).updateMainContact(Mockito.any());
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void updateMainContact_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "updateMainContact", Mockito.any());
//    participantContactFacade.updateMainContact(participantContactJson);
//  }
//
//  @Test
//  public void addNonMainContact_method_should_invoke_addNonMainContact_from_participantContactService() throws Exception {
//    participantContactFacade.addNonMainContact(participantContactJson);
//    verify(participantContactService, times(1)).addNonMainContact(Mockito.any());
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void addNonMainContact_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "addNonMainContact", Mockito.any());
//    participantContactFacade.addNonMainContact(participantContactJson);
//  }
//
//  @Test
//  public void updateSecondaryContact_method_should_invoke_updateSecondaryContact_from_participantContactService() throws Exception {
//    participantContactFacade.updateSecondaryContact(participantContactJson);
//    verify(participantContactService, times(1)).updateSecondaryContact(Mockito.any());
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void updateSecondaryContact_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "updateSecondaryContact", Mockito.any());
//    participantContactFacade.updateSecondaryContact(participantContactJson);
//  }
//
//  @Test
//  public void swapMainContactWithSecondary_method_should_invoke_swapMainContactWithSecondary_from_participantContactService() throws Exception {
//    participantContactFacade.swapMainContactWithSecondary(participantContactJson);
//    verify(participantContactService, times(1)).swapMainContactWithSecondary(Mockito.any());
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void swapMainContactWithSecondary_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "swapMainContactWithSecondary", Mockito.any());
//    participantContactFacade.swapMainContactWithSecondary(participantContactJson);
//  }
//
//  @Test
//  public void delete_method_should_invoke_delete_from_participantContactService() throws Exception {
//    participantContactFacade.delete(PARTICIPANT_CONTACT_ID);
//    verify(participantContactService, times(1)).delete(participantContactOID);
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void delete_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "delete", participantContactOID);
//    participantContactFacade.delete(PARTICIPANT_CONTACT_ID);
//  }
//
//  @Test
//  public void deleteNonMainContact_method_should_invoke_deleteNonMainContact_from_participantContactService() throws Exception {
//    participantContactFacade.deleteNonMainContact(participantContactJson);
//    verify(participantContactService, times(1)).deleteNonMainContact(Mockito.any());
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void deleteNonMainContact_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "deleteNonMainContact", Mockito.any());
//    participantContactFacade.deleteNonMainContact(participantContactJson);
//  }
//
//  @Test
//  public void get_method_should_invoke_get_from_participantContactService() throws Exception {
//    participantContactFacade.get(PARTICIPANT_CONTACT_ID);
//    verify(participantContactService, times(1)).get(participantContactOID);
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void get_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "get", participantContactOID);
//    participantContactFacade.get(PARTICIPANT_CONTACT_ID);
//  }
//
//  @Test
//  public void getByRecruitmentNumber_method_should_invoke_getByRecruitmentNumber_from_participantContactService() throws Exception {
//    participantContactFacade.getByRecruitmentNumber(RN.toString());
//    verify(participantContactService, times(1)).getByRecruitmentNumber(RN);
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void getByRecruitmentNumber_method_should_handle_DataNotFoundException() throws Exception {
//    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "getByRecruitmentNumber", RN);
//    participantContactFacade.getByRecruitmentNumber(RN.toString());
//  }
}
