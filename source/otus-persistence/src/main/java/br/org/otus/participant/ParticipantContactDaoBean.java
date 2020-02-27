package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.ParticipantContactDao;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantContactDaoBean extends MongoGenericDao<Document> implements ParticipantContactDao {

  private static final String COLLECTION_NAME = "participant_contact";

  public ParticipantContactDaoBean(){
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId create(ParticipantContact participantContact) {
    Document parsed = Document.parse(ParticipantContact.serialize(participantContact));
    collection.insertOne(parsed);
    return parsed.getObjectId(ID_FIELD_NAME);
  }

  @Override
  public void update(ObjectId participantContactOID, ParticipantContact participantContact) throws DataNotFoundException {
    UpdateResult update = collection.updateOne(
      eq(ID_FIELD_NAME, participantContactOID),
      Document.parse(ParticipantContact.serialize(participantContact))
    );
    if(update.getMatchedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactOID.toString() + " } was not found");
    }
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(ID_FIELD_NAME, participantContactOID));
    if(deleteResult.getDeletedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactOID.toString() + " } was not found");
    }
  }

  @Override
  public ParticipantContact get(ObjectId participantContactOID) throws DataNotFoundException {
    Document result = collection.find(eq(ID_FIELD_NAME, participantContactOID)).first();
    try{
      return ParticipantContact.deserialize(result.toJson());
    }
    catch (NullPointerException e){
      throw new DataNotFoundException("No participant contact found for OID {" + participantContactOID.toString() + "}");
    }
  }
}
