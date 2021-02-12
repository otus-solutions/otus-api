package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;
import org.ccem.otus.participant.persistence.NoteAboutParticipantDao;

import java.util.List;

public class NoteAboutParticipantDaoBean extends MongoGenericDao<Document> implements NoteAboutParticipantDao {

  private static final String COLLECTION_NAME = "participant_note_about";

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
  public void delete(ObjectId commentAboutParticipantId) throws DataNotFoundException {

  }

  @Override
  public List<NoteAboutParticipantDto> get(Long recruitmentNumber, int skip, int limit) {
    return null;
  }
}
