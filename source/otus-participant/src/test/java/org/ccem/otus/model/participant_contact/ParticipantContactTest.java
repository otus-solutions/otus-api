package org.ccem.otus.model.participant_contact;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactTypeOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class ParticipantContactTest {

  private static final ObjectId OID = new ObjectId("5e13997795818e14a91a5268");
  private static final String OBJECT_TYPE = "ParticipantContact";
  private static final Long RN = 1234567L;
  private static final ParticipantContactItem mainEmail = new ParticipantContactItem();
  private static final ParticipantContactItem mainAddress = new ParticipantContactItem();
  private static final ParticipantContactItem mainPhoneNumber = new ParticipantContactItem();
  private static final ParticipantContactItem[] secondaryEmails = new ParticipantContactItem[3];
  private static final ParticipantContactItem[] secondaryAddresses = new ParticipantContactItem[3];
  private static final ParticipantContactItem[] secondaryPhoneNumbers = new ParticipantContactItem[3];
  private static final String CONTACT_TYPE = ParticipantContactTypeOptions.EMAIL.getName();

  private ParticipantContact participantContact = new ParticipantContact();

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContact, "_id", OID);
    Whitebox.setInternalState(participantContact, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(participantContact, "recruitmentNumber", RN);
    Whitebox.setInternalState(participantContact, "mainEmail", mainEmail);
    Whitebox.setInternalState(participantContact, "mainAddress", mainAddress);
    Whitebox.setInternalState(participantContact, "mainPhoneNumber", mainPhoneNumber);
    Whitebox.setInternalState(participantContact, "secondaryEmails", secondaryEmails);
    Whitebox.setInternalState(participantContact, "secondaryAddresses", secondaryAddresses);
    Whitebox.setInternalState(participantContact, "secondaryPhoneNumbers", secondaryPhoneNumbers);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(OID, participantContact.getObjectId());
    assertEquals(OBJECT_TYPE, participantContact.getObjectType());
    assertEquals(RN, participantContact.getRecruitmentNumber());

  }

  @Test
  public void getMainParticipantContactItemByType_method_should_return_hashMap_with_main_value(){
    assertEquals(mainEmail, participantContact.getMainParticipantContactItemByType(CONTACT_TYPE));
  }

  @Test
  public void getSecondaryParticipantContactsItemByType_method_should_return_hashMap_with_main_value(){
    //assertArrayEquals(secondaryEmails, participantContact.getSecondaryParticipantContactsItemByType(CONTACT_TYPE));
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
  public void getFrontGsonBuilder_static_method_return_GsonBuilder_instance(){
    assertTrue(ParticipantContact.getFrontGsonBuilder() instanceof GsonBuilder);
  }

}
