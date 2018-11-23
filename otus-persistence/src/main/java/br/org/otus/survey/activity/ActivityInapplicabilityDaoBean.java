package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;
import org.ccem.otus.persistence.ActivityInapplicabilityDao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ActivityInapplicabilityDaoBean extends MongoGenericDao<Document> implements ActivityInapplicabilityDao {

  private static final String COLLECTION_NAME = "activity_inapplicability";
  private static final String RECRUITMENT_NUMBER = "recruitmentNumber";
  private static final String ACRONYM = "acronym";

  public ActivityInapplicabilityDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void update(ActivityInapplicability applicability) throws DataNotFoundException {
    Document parsed = Document.parse(ActivityInapplicability.serialize(applicability));

    UpdateResult updateLabData = collection.updateOne(and(eq(RECRUITMENT_NUMBER, applicability.getRecruitmentNumber()), eq(ACRONYM, applicability.getAcronym())),
      new Document("$set", parsed), new UpdateOptions().upsert(true));


    if ((updateLabData.getModifiedCount() == 0) && (updateLabData.getUpsertedId() == null)) {
      throw new DataNotFoundException(new Throwable("Update Fail"));
    }
  }

  @Override
  public void delete(Long rn, String acronym) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(
      new Document("acronym", acronym)
        .append("recruitmentNumber", rn)
    );

    if(deleteResult.getDeletedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Delete fail"));
    }
  }

}
