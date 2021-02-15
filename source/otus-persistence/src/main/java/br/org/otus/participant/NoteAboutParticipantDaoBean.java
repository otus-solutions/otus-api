package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.participant.builder.NoteAboutParticipantQueryBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;
import org.ccem.otus.participant.persistence.NoteAboutParticipantDao;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class NoteAboutParticipantDaoBean extends MongoGenericDao<Document> implements NoteAboutParticipantDao {

  private static final String COLLECTION_NAME = "participant_note_about";
  private static final String USER_ID_PATH = "userId";
  private static final String RECRUITMENT_NUMBER_PATH = "recruitmentNumber";

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
  public ObjectId update(NoteAboutParticipant noteAboutParticipant) throws DataNotFoundException {
    UpdateResult updateResult = collection.updateOne(
      new Document(ID_FIELD_NAME, noteAboutParticipant.getId()),
      new Document(SET_OPERATOR, Document.parse(noteAboutParticipant.serialize()))
    );
    if(updateResult.getMatchedCount() == 0){
      throw new DataNotFoundException("There is no note about participant with id {" + noteAboutParticipant.getId().toHexString() + "}");
    }
    return noteAboutParticipant.getId();
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
  public List<NoteAboutParticipantDto> getAll(ObjectId userOid, Long recruitmentNumber, int skip, int limit) throws MemoryExcededException {
    AggregateIterable<Document> results = collection.aggregate((new NoteAboutParticipantQueryBuilder().getByRnQuery(userOid, recruitmentNumber, skip, limit)));
    MongoCursor<Document> iterator = results.iterator();

    List<NoteAboutParticipantDto> notes = new ArrayList<>();

    while(iterator.hasNext()){
      try{
        notes.add(NoteAboutParticipantDto.deserialize(iterator.next().toJson()));
      }
      catch(OutOfMemoryError e){
        notes.clear();
        throw new MemoryExcededException("Notes about participant {" + recruitmentNumber + "} exceeded memory used");
      }
    }

    return notes;
  }

}
