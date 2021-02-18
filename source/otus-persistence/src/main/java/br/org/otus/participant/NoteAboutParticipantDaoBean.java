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
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantResponse;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantSearchSettingsDto;
import org.ccem.otus.participant.persistence.NoteAboutParticipantDao;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class NoteAboutParticipantDaoBean extends MongoGenericDao<Document> implements NoteAboutParticipantDao {

  private static final String COLLECTION_NAME = "participant_note_about";
  private static final String USER_ID_PATH = "userId";
  private static final String STARRED_PATH = "starred";

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
  public NoteAboutParticipant get(ObjectId noteAboutParticipantId) {
    Document result = collection.find(eq(ID_FIELD_NAME, noteAboutParticipantId)).first();
    if (result == null) {
      return null;
    }
    return NoteAboutParticipant.deserialize(result.toJson());
  }

  @Override
  public void update(ObjectId userOid, NoteAboutParticipant noteAboutParticipant) throws DataNotFoundException {
    UpdateResult updateResult = collection.updateOne(
      new Document(ID_FIELD_NAME, noteAboutParticipant.getId()).append(USER_ID_PATH, userOid),
      new Document(SET_OPERATOR, Document.parse(noteAboutParticipant.serialize()))
    );
    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException("There is no note about participant with id {" + noteAboutParticipant.getId().toHexString() + "}");
    }
  }

  @Override
  public void updateStarred(ObjectId userOid, ObjectId noteAboutParticipantId, boolean starred) throws DataNotFoundException {
    UpdateResult updateResult = collection.updateOne(
      new Document(ID_FIELD_NAME, noteAboutParticipantId).append(USER_ID_PATH, userOid),
      new Document(SET_OPERATOR, new Document(STARRED_PATH, starred))
    );
    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException("There is no note about participant with id {" + noteAboutParticipantId.toHexString() + "}");
    }
  }

  @Override
  public void delete(ObjectId userId, ObjectId noteAboutParticipantId) throws DataNotFoundException {
    Document query = new Document(ID_FIELD_NAME, noteAboutParticipantId);
    query.put(USER_ID_PATH, userId);
    DeleteResult deleteResult = collection.deleteOne(query);
    if (deleteResult.getDeletedCount() == 0) {
      throw new DataNotFoundException("There is no note about participant with id {" + noteAboutParticipantId.toHexString() + "}");
    }
  }

  @Override
  public List<NoteAboutParticipantResponse> getAll(ObjectId userOid, Long recruitmentNumber, NoteAboutParticipantSearchSettingsDto searchSettingsDto) throws MemoryExcededException, DataNotFoundException {
    AggregateIterable<Document> results = collection.aggregate((new NoteAboutParticipantQueryBuilder().getByRnQuery(userOid, recruitmentNumber, searchSettingsDto))).allowDiskUse(true);
    if (results == null) {
      throw new DataNotFoundException("No results for user note about participant.");
    }

    MongoCursor<Document> iterator = results.iterator();

    List<NoteAboutParticipantResponse> notes = new ArrayList<>();

    while (iterator.hasNext()) {
      try {
        notes.add(new NoteAboutParticipantResponse().deserializeNonStatic(iterator.next().toJson()));
      } catch (OutOfMemoryError e) {
        notes.clear();
        throw new MemoryExcededException("Notes about participant {" + recruitmentNumber + "} exceeded memory used");
      }
    }

    return notes;
  }

}
