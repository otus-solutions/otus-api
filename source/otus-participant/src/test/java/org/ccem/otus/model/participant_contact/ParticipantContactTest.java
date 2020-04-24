package org.ccem.otus.model.participant_contact;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantContactTest {

  private static final ObjectId OID = new ObjectId("5e13997795818e14a91a5268");
  private static final String OBJECT_TYPE = "ParticipantContact";
  private static final Long RN = 1234567L;

  private ParticipantContact participantContact = new ParticipantContact();
  private ParticipantContactItemSet<Email> emailSet = PowerMockito.spy(new ParticipantContactItemSet<>());
  private ParticipantContactItemSet<Address> addressSet = PowerMockito.spy(new ParticipantContactItemSet<>());
  private ParticipantContactItemSet<PhoneNumber> phoneNumberSet = PowerMockito.spy(new ParticipantContactItemSet<>());

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContact, "_id", OID);
    Whitebox.setInternalState(participantContact, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(participantContact, "recruitmentNumber", RN);
    Whitebox.setInternalState(participantContact, "emailSet", emailSet);
    Whitebox.setInternalState(participantContact, "addressSet", addressSet);
    Whitebox.setInternalState(participantContact, "phoneNumberSet", phoneNumberSet);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(OID, participantContact.getObjectId());
    assertEquals(OBJECT_TYPE, participantContact.getObjectType());
    assertEquals(RN, participantContact.getRecruitmentNumber());
    assertEquals(emailSet, participantContact.getEmailSet());
    assertEquals(addressSet, participantContact.getAddressSet());
    assertEquals(phoneNumberSet, participantContact.getPhoneNumberSet());
  }

  @Test
  public void serialize_static_method_should_convert_objectModel_to_JsonString(){
    assertTrue(ParticipantContact.serialize(participantContact) instanceof String);
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    String participantContactJson = ParticipantContact.serialize(participantContact);
    assertTrue(ParticipantContact.deserialize(participantContactJson) instanceof ParticipantContact);
  }

  @Test
  public void getFrontGsonBuilder_static_should_method_return_GsonBuilder_instance(){
    assertTrue(ParticipantContact.getFrontGsonBuilder() instanceof GsonBuilder);
  }

  @Test
  public void getMainParticipantContactItemByType_method_should_return_main_email(){
    assertEquals(emailSet,
      participantContact.getParticipantContactItemSetByType(
        ParticipantContactTypeOptions.EMAIL.getName()));
  }

  @Test
  public void hasAllMainContacts_method_should_return_TRUE(){
    Whitebox.setInternalState(emailSet, "main", new ParticipantContactItem<Email>());
    Whitebox.setInternalState(addressSet, "main", new ParticipantContactItem<Address>());
    Whitebox.setInternalState(phoneNumberSet, "main", new ParticipantContactItem<PhoneNumber>());
    assertTrue(participantContact.hasAllMainContacts());
  }

  @Test
  public void hasAllMainContacts_method_should_return_FALSE_in_case_any_itemSet_has_null_main_contact(){
    assertFalse(participantContact.hasAllMainContacts());
  }

  @Test
  public void hasAllMainContacts_method_should_return_FALSE_in_case_emailSet_be_null(){
    Whitebox.setInternalState(participantContact, "emailSet", (ParticipantContactItemSet)null);
    assertFalse(participantContact.hasAllMainContacts());
  }

  @Test
  public void hasAllMainContacts_method_should_return_FALSE_in_case_addressSet_be_null(){
    Whitebox.setInternalState(emailSet, "main", new ParticipantContactItem<Email>());
    Whitebox.setInternalState(participantContact, "addressSet", (ParticipantContactItemSet)null);
    assertFalse(participantContact.hasAllMainContacts());
  }

  @Test
  public void hasAllMainContacts_method_should_return_FALSE_in_case_phoneNumberSet_be_null(){
    Whitebox.setInternalState(emailSet, "main", new ParticipantContactItem<Email>());
    Whitebox.setInternalState(addressSet, "main", new ParticipantContactItem<Address>());
    Whitebox.setInternalState(participantContact, "phoneNumberSet", (ParticipantContactItemSet)null);
    assertFalse(participantContact.hasAllMainContacts());
  }

}
