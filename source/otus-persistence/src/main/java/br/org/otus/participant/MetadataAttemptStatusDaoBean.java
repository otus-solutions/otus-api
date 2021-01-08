package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.MetadataAttemptStatus;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.persistence.MetadataAttemptStatusDao;
import org.ccem.otus.participant.persistence.ParticipantContactAttemptDao;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

import static com.mongodb.client.model.Filters.eq;

public class MetadataAttemptStatusDaoBean extends MongoGenericDao<Document> implements MetadataAttemptStatusDao {

  private static final String COLLECTION_NAME = "participant_metadata_attempt_status";
  private static final String OBJECT_TYPE_FIELD = "objectType";

  public MetadataAttemptStatusDaoBean(){
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public MetadataAttemptStatus findMetadataAttempt(String objectType) throws DataNotFoundException {
    try {
      Document result = collection.find(eq(OBJECT_TYPE_FIELD, objectType)).first();
      return MetadataAttemptStatus.deserialize(result.toJson());
    } catch (NullPointerException e) {
      throw new DataNotFoundException("No metadata Attempt found for objectType {" + objectType + "}");
    }
  }

}
