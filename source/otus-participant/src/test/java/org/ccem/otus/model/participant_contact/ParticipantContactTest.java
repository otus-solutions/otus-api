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

  private static final String CONTACT_TYPE = ParticipantContactTypeOptions.EMAIL.getName();

  private ParticipantContact participantContact = new ParticipantContact();
  private ParticipantContactItemSet<ParticipantContactItemValueString> emails = PowerMockito.spy(new ParticipantContactItemSet<>());
  private ParticipantContactItemSet<Address> addresses = PowerMockito.spy(new ParticipantContactItemSet<>());
  private ParticipantContactItemSet<ParticipantContactItemValueString> phoneNumbers = PowerMockito.spy(new ParticipantContactItemSet<>());

  @Test
  public void deserialize_static_method(){
    String participantContactJson = "{\n" +
      "  \"objectType\": \"ParticipantContact\",\n" +
      "  \"recruitmentNumber\": 1234567,\n" +
      "  \"email\": {\n" +
      "    \"main\": {\n" +
      "      \"value\": { \"content\": \"main.email@gmail.com\" },\n" +
      "      \"observation\": \"personal\"\n" +
      "    },\n" +
      "    \"second\": {\n" +
      "      \"value\": { \"content\": \"secondary0.email@gmail.com\" },\n" +
      "      \"observation\": \"work\"\n" +
      "    },\n" +
      "    \"third\": {\n" +
      "      \"value\": { \"content\": \"secondary1.email@gmail.com\" },\n" +
      "      \"observation\": \"university\"\n" +
      "    }\n" +
      "  },\n" +
      "  \"address\": {\n" +
      "    \"main\": {\n" +
      "      \"value\": {\n" +
      "        \"postalCode\": \"90010-907\",\n" +
      "        \"street\": \"Rua dos Bobos\",\n" +
      "        \"streetNumber\": 0,\n" +
      "        \"complements\": \"Feita com muito esmero!\",\n" +
      "        \"neighbourhood\": \"Centro\",\n" +
      "        \"city\": \"Porto Alegre\",\n" +
      "        \"country\": \"Brasil\"\n" +
      "      },\n" +
      "      \"observations\": \"Casa\"\n" +
      "    },\n" +
      "    \"second:\": {\n" +
      "      \"value\": {\n" +
      "        \"postalCode\": \"90010-907\",\n" +
      "        \"street\": \"Rua dos Bobos\",\n" +
      "        \"streetNumber\": 0,\n" +
      "        \"complements\": \"Feita com muito esmero!\",\n" +
      "        \"neighbourhood\": \"Centro\",\n" +
      "        \"city\": \"Porto Alegre\",\n" +
      "        \"country\": \"Brasil\"\n" +
      "      },\n" +
      "      \"observations\": \"Casa da vizinha da minha tia.\"\n" +
      "    }\n" +
      "  },\n" +
      "  \"phoneNumber\": {\n" +
      "    \"main\": {\n" +
      "      \"value\": { \"content\":  \"51123456789\" },\n" +
      "      \"observation\": \"casa\"\n" +
      "    },\n" +
      "    \"second\": {\n" +
      "      \"value\": { \"content\":  \"51987654321\" },\n" +
      "      \"observation\": \"celular\"\n" +
      "    }\n" +
      "  }\n" +
      "}";
    ParticipantContact result = ParticipantContact.deserialize(participantContactJson);
    assertTrue(result instanceof ParticipantContact);
  }

  @Before
  public void setUp(){
    Whitebox.setInternalState(participantContact, "_id", OID);
    Whitebox.setInternalState(participantContact, "objectType", OBJECT_TYPE);
    Whitebox.setInternalState(participantContact, "recruitmentNumber", RN);
    Whitebox.setInternalState(participantContact, "email", emails);
    Whitebox.setInternalState(participantContact, "address", addresses);
    Whitebox.setInternalState(participantContact, "phoneNumber", phoneNumbers);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(OID, participantContact.getObjectId());
    assertEquals(OBJECT_TYPE, participantContact.getObjectType());
    assertEquals(RN, participantContact.getRecruitmentNumber());
    assertEquals(emails, participantContact.getEmail());
    assertEquals(addresses, participantContact.getAddress());
    assertEquals(phoneNumbers, participantContact.getPhoneNumber());
  }

  @Test
  public void getMainParticipantContactItemByType_method_should_return_hashMap_with_main_value(){
    //assertEquals(emails, participantContact.getParticipantContactsItemByType(CONTACT_TYPE));
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
