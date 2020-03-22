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

import java.util.zip.DataFormatException;

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
  private DataNotFoundException dataNotFoundException = PowerMockito.spy(new DataNotFoundException());
  private DataFormatException dataFormatException = PowerMockito.spy(new DataFormatException());

  @Before
  public void setUp(){
    participantContact = new ParticipantContact();
    participantContactOID = new ObjectId(PARTICIPANT_CONTACT_ID);
    participantContactJson = ParticipantContact.serialize(participantContact);
  }

  @Test
  public void create_method_should_invoke_create_from_participantContactService() throws DataFormatException {
    when(participantContactService.create(Mockito.any())).thenReturn(participantContactOID);
    assertEquals(PARTICIPANT_CONTACT_ID, participantContactFacade.create(participantContactJson));
  }

  @Test(expected = HttpResponseException.class)
  public void create_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "create", Mockito.any());
    participantContactFacade.create(participantContactJson);
  }

  @Test
  public void addNonMainEmail_method_should_invoke_addNonMainEmail_from_participantContactService() throws Exception {
    participantContactFacade.addNonMainEmail(participantContactJson);
    verify(participantContactService, times(1)).addNonMainEmail(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void addNonMainEmail_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "addNonMainEmail", Mockito.any());
    participantContactFacade.addNonMainEmail(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void addNonMainEmail_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "addNonMainEmail", Mockito.any());
    participantContactFacade.addNonMainEmail(participantContactJson);
  }


  @Test
  public void addNonMainAddress_method_should_invoke_addNonMainAddress_from_participantContactService() throws Exception {
    participantContactFacade.addNonMainAddress(participantContactJson);
    verify(participantContactService, times(1)).addNonMainAddress(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void addNonMainAddress_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "addNonMainAddress", Mockito.any());
    participantContactFacade.addNonMainAddress(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void addNonMainAddress_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "addNonMainAddress", Mockito.any());
    participantContactFacade.addNonMainAddress(participantContactJson);
  }


  @Test
  public void addNonMainPhoneNumber_method_should_invoke_addNonMainPhoneNumber_from_participantContactService() throws Exception {
    participantContactFacade.addNonMainPhoneNumber(participantContactJson);
    verify(participantContactService, times(1)).addNonMainPhoneNumber(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void addNonMainPhoneNumber_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "addNonMainPhoneNumber", Mockito.any());
    participantContactFacade.addNonMainPhoneNumber(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void addNonMainPhoneNumber_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "addNonMainPhoneNumber", Mockito.any());
    participantContactFacade.addNonMainPhoneNumber(participantContactJson);
  }

  @Test
  public void updateEmail_method_should_invoke_updateEmail_from_participantContactService() throws Exception {
    participantContactFacade.updateEmail(participantContactJson);
    verify(participantContactService, times(1)).updateEmail(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void updateEmail_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "updateEmail", Mockito.any());
    participantContactFacade.updateEmail(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void updateEmail_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "updateEmail", Mockito.any());
    participantContactFacade.updateEmail(participantContactJson);
  }


  @Test
  public void updateAddress_method_should_invoke_updateAddress_from_participantContactService() throws Exception {
    participantContactFacade.updateAddress(participantContactJson);
    verify(participantContactService, times(1)).updateAddress(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void updateAddress_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "updateAddress", Mockito.any());
    participantContactFacade.updateAddress(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void updateAddress_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "updateAddress", Mockito.any());
    participantContactFacade.updateAddress(participantContactJson);
  }


  @Test
  public void updatePhoneNumber_method_should_invoke_updatePhoneNumber_from_participantContactService() throws Exception {
    participantContactFacade.updatePhoneNumber(participantContactJson);
    verify(participantContactService, times(1)).updatePhoneNumber(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void updatePhoneNumber_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "updatePhoneNumber", Mockito.any());
    participantContactFacade.updatePhoneNumber(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void updatePhoneNumber_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "updatePhoneNumber", Mockito.any());
    participantContactFacade.updatePhoneNumber(participantContactJson);
  }


  @Test
  public void swapMainContact_method_should_invoke_swapMainContact_from_participantContactService() throws Exception {
    participantContactFacade.swapMainContact(participantContactJson);
    verify(participantContactService, times(1)).swapMainContact(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void swapMainContact_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "swapMainContact", Mockito.any());
    participantContactFacade.swapMainContact(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void swapMainContact_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "swapMainContact", Mockito.any());
    participantContactFacade.swapMainContact(participantContactJson);
  }


  @Test
  public void delete_method_should_invoke_delete_from_participantContactService() throws Exception {
    participantContactFacade.delete(PARTICIPANT_CONTACT_ID);
    verify(participantContactService, times(1)).delete(participantContactOID);
  }

  @Test(expected = HttpResponseException.class)
  public void delete_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "delete", participantContactOID);
    participantContactFacade.delete(PARTICIPANT_CONTACT_ID);
  }


  @Test
  public void deleteNonMainContact_method_should_invoke_deleteNonMainContact_from_participantContactService() throws Exception {
    participantContactFacade.deleteNonMainContact(participantContactJson);
    verify(participantContactService, times(1)).deleteNonMainContact(Mockito.any());
  }

  @Test(expected = HttpResponseException.class)
  public void deleteNonMainContact_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "deleteNonMainContact", Mockito.any());
    participantContactFacade.deleteNonMainContact(participantContactJson);
  }

  @Test(expected = HttpResponseException.class)
  public void deleteNonMainContact_method_should_handle_DataFormatException() throws Exception {
    PowerMockito.doThrow(dataFormatException).when(participantContactService, "deleteNonMainContact", Mockito.any());
    participantContactFacade.deleteNonMainContact(participantContactJson);
  }

  @Test
  public void get_method_should_invoke_get_from_participantContactService() throws Exception {
    participantContactFacade.getParticipantContact(PARTICIPANT_CONTACT_ID);
    verify(participantContactService, times(1)).getParticipantContact(participantContactOID);
  }

  @Test(expected = HttpResponseException.class)
  public void get_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "getParticipantContact", participantContactOID);
    participantContactFacade.getParticipantContact(PARTICIPANT_CONTACT_ID);
  }

  @Test
  public void getByRecruitmentNumber_method_should_invoke_getByRecruitmentNumber_from_participantContactService() throws Exception {
    participantContactFacade.getParticipantContactByRecruitmentNumber(RN.toString());
    verify(participantContactService, times(1)).getParticipantContactByRecruitmentNumber(RN);
  }

  @Test(expected = HttpResponseException.class)
  public void getByRecruitmentNumber_method_should_handle_DataNotFoundException() throws Exception {
    PowerMockito.doThrow(dataNotFoundException).when(participantContactService, "getParticipantContactByRecruitmentNumber", RN);
    participantContactFacade.getParticipantContactByRecruitmentNumber(RN.toString());
  }

  @Test(expected = HttpResponseException.class)
  public void getByRecruitmentNumber_method_should_handle_NumberFormatException() throws Exception {
    PowerMockito.doThrow(new NumberFormatException()).when(participantContactService, "getParticipantContactByRecruitmentNumber", RN);
    participantContactFacade.getParticipantContactByRecruitmentNumber(RN.toString());
  }
}
