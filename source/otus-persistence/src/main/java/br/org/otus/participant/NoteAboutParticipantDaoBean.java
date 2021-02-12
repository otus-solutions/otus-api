package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;
import org.ccem.otus.participant.persistence.NoteAboutParticipantDao;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class NoteAboutParticipantDaoBean extends MongoGenericDao<Document> implements NoteAboutParticipantDao {

  private static final String COLLECTION_NAME = "participant_note_about";

  private static final String USER_ID_PATH = "userId";

  public NoteAboutParticipantDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId create(NoteAboutParticipant noteAboutParticipant) {
    Document parsed = Document.parse(noteAboutParticipant.serialize());
    collection.insertOne(parsed);
    return parsed.getObjectId(ID_FIELD_NAME);
  }

  @Override
  public ObjectId update(NoteAboutParticipant commentAboutParticipant) {
    return null;
  }

  @Override
  public void delete(ObjectId userId, ObjectId noteAboutParticipantId) throws DataNotFoundException, ValidationException {
    Document query = new Document(ID_FIELD_NAME, noteAboutParticipantId);
    query.put(USER_ID_PATH, userId);
    DeleteResult deleteResult = collection.deleteOne(query);
    if(deleteResult.getDeletedCount() == 0){
      Document result = collection.find(eq(ID_FIELD_NAME, noteAboutParticipantId)).first();
      if(result != null){
        throw new ValidationException();
      }
      throw new DataNotFoundException("There is no note about participant with id {" + noteAboutParticipantId.toHexString() + "}");
    }
  }

  @Override
  public List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit) {
    return null;
  }
}
