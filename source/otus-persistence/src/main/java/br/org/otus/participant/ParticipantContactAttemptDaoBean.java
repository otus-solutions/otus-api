package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.persistence.ParticipantContactAttemptDao;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantContactAttemptDaoBean extends MongoGenericDao<Document> implements ParticipantContactAttemptDao {

  private static final String COLLECTION_NAME = "participant_contact_attempt";
  private static final String RECRUITMENT_NUMBER_FIELD_NAME = "recruitmentNumber";
  private static final String OBJECT_TYPE_FIELD_NAME = "objectType";
  private static final String ADDRESS_FIELD_NAME = "address";

  public ParticipantContactAttemptDaoBean(){
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId create(ParticipantContactAttempt participantContactAttempt) throws DataFormatException {
    Document parsed = Document.parse(ParticipantContactAttempt.serialize(participantContactAttempt));
    collection.insertOne(parsed);
    return parsed.getObjectId(ID_FIELD_NAME);
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(ID_FIELD_NAME, participantContactOID));
    if(deleteResult.getDeletedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactOID.toString() + " } was not found");
    }
  }

  @Override
  public ArrayList<ParticipantContactAttempt> findAttempts(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException {
    try{
      ArrayList<ParticipantContactAttempt> attempts = new ArrayList<>();
      BasicDBObject query = new BasicDBObject();
      query.put(RECRUITMENT_NUMBER_FIELD_NAME, recruitmentNumber);
      query.put(OBJECT_TYPE_FIELD_NAME, objectType);
      FindIterable<Document> result = collection.find(query);
      MongoCursor<Document> iterator = result.iterator();
      while (iterator.hasNext()) {
        Document document = ( Document ) iterator.next();
        ParticipantContactAttempt participantContactAttempt = ParticipantContactAttempt.deserialize(document.toJson());
        String address = participantContactAttempt.getAddress().toString();
        if(address.contains(position)) {
          attempts.add(participantContactAttempt);
        }
      }
      return attempts;
    }
    catch (NullPointerException e){
      throw new DataNotFoundException("No participant contact attempts found for recruitmentNumber {" + recruitmentNumber.toString() + "}");
    }
  }
}
