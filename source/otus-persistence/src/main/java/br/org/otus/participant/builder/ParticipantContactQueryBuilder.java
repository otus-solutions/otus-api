package br.org.otus.participant.builder;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItemSet;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactPositionOptions;

import java.util.ArrayList;

public class ParticipantContactQueryBuilder {

  public static final String OBJECT_TYPE = "ParticipantContact";

  private static final String EMAIL_OBJECT_TYPE = "Email";
  private static final String ADDRESS_OBJECT_TYPE = "Address";
  private static final String PHONE_NUMBER_OBJECT_TYPE = "PhoneNumber";
  private static final String EMAIL_FIELD_NAME = "email";
  private static final String ADDRESS_FIELD_NAME = "address";
  private static final String PHONE_NUMBER_FIELD_NAME = "phoneNumber";

  private ArrayList<Bson> pipeline;

  public ArrayList<Document> participantContactToDocuments(ParticipantContact participantContact) {
    ArrayList<Document> documents = new ArrayList<>();
    participantContactItemSetToDocuments(participantContact, EMAIL_FIELD_NAME, EMAIL_OBJECT_TYPE, documents);
    participantContactItemSetToDocuments(participantContact, ADDRESS_FIELD_NAME, ADDRESS_OBJECT_TYPE, documents);
    participantContactItemSetToDocuments(participantContact, PHONE_NUMBER_FIELD_NAME, PHONE_NUMBER_OBJECT_TYPE, documents);
    return documents;
  }

  private void participantContactItemSetToDocuments(ParticipantContact participantContact, String fieldName, String contactType, ArrayList<Document> documents){
    for (ParticipantContactPositionOptions position : ParticipantContactPositionOptions.values()) {
      ParticipantContactItemSet participantContactItemSet = participantContact.getParticipantContactsItemByType(fieldName);
      ParticipantContactItem participantContactItem = participantContactItemSet.getItemByPosition(position.toString());
      if(participantContactItem == null)
        break;

      Document doc = new Document();

      doc.put("objectType", OBJECT_TYPE);
      doc.put("recruitmentNumber", participantContact.getRecruitmentNumber());
      doc.put("position", position);

      Document contactDoc = new Document();
      contactDoc.put("objectType", contactType);
      contactDoc.put(contactType, participantContactItem.getValue());
      doc.put(fieldName, contactDoc);

      doc.put("observation", participantContactItem.getObservation());

      documents.add(doc);
    }
  }
}
