package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptConfiguration;
import org.ccem.otus.participant.persistence.ParticipantContactAttemptConfigurationDao;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantContactAttemptConfigurationDaoBean extends MongoGenericDao<Document> implements ParticipantContactAttemptConfigurationDao {

  private static final String COLLECTION_NAME = "participant_contact_attempt_configuration";
  private static final String OBJECT_TYPE_FIELD = "objectType";
  private static final String DEFAULT_ATTEMPT_NUMBER_OBJTYPE = "objectType";

  public ParticipantContactAttemptConfigurationDaoBean(){
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ParticipantContactAttemptConfiguration findMetadataAttempt(String objectType) throws DataNotFoundException {
    try {
      Document result = collection.find(eq(OBJECT_TYPE_FIELD, objectType)).first();
      return ParticipantContactAttemptConfiguration.deserialize(result.toJson());
    } catch (NullPointerException e) {
      throw new DataNotFoundException("No metadata Attempt found for objectType {" + objectType + "}");
    }
  }

}
