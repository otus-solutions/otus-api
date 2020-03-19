package br.org.otus.participant.builder;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.participant.model.participant_contact.*;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantContactQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ArrayList<Document> participantContactToDocuments(ParticipantContact participantContact) {
    ArrayList<Document> documents = new ArrayList<>();
    for (ParticipantContactTypeOptions contactType : ParticipantContactTypeOptions.values()) {
      for (ParticipantContactPositionOptions position : ParticipantContactPositionOptions.values()) {
        ParticipantContactItem participantContactItem = participantContact.getParticipantContactsItemByType(contactType)
          .getItemByPosition(position);
        if (participantContactItem == null)
          break;
        documents.add(participantContactItemToDocument(participantContactItem, participantContact.getRecruitmentNumber(), contactType, position));
      }
    }
    return documents;
  }

  public Document participantContactItemToDocument(ParticipantContactItem participantContactItem, Long recruitmentNumber,
                                                    ParticipantContactTypeOptions contactType, ParticipantContactPositionOptions position){
      Document doc = new Document();
      doc.put("recruitmentNumber", recruitmentNumber);
      doc.put("position", position.toString());
      doc.put(contactType.toString().toLowerCase(), Document.parse(participantContactItem.getValue().toJson()));
      doc.put("observation", participantContactItem.getObservation());
      return doc;
  }

  public void getByRecruitmentNumber(Long recruitmentNumber) {

  }


}
