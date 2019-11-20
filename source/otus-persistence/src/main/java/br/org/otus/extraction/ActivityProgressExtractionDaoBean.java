package br.org.otus.extraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ActivityInapplicabilityDao;
import org.ccem.otus.persistence.ActivityProgressExtractionDao;
import org.ccem.otus.persistence.SurveyDao;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.extraction.builder.ActivityProgressExtractionQueryBuilder;

public class ActivityProgressExtractionDaoBean extends MongoGenericDao<Document> implements ActivityProgressExtractionDao {

  public static final String COLLECTION_NAME = "activity";
  @Inject
  private SurveyDao surveyDao;
  @Inject
  private ParticipantDao participantDao;
  @Inject
  private ActivityInapplicabilityDao activityInapplicabilityDao;
  
  public ActivityProgressExtractionDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public LinkedList<ActivityProgressResultExtraction> getActivityProgressExtraction(String center) throws DataNotFoundException {
    ActivityProgressExtractionQueryBuilder queryBuilder = new ActivityProgressExtractionQueryBuilder();

    List<Bson> queryToAcronyms = queryBuilder.getAllAcronyms();
    AggregateIterable<Document> acronyms = this.surveyDao.aggregate(queryToAcronyms).allowDiskUse(true);

    List<Bson> queryToParticipants = queryBuilder.getParticipants();
    AggregateIterable<Document> rns = this.participantDao.aggregate(queryToParticipants).allowDiskUse(true);

    List<Bson> queryToInapplicabilities = queryBuilder.getInapplicabilities();
    AggregateIterable<Document> inapplicabilities = this.activityInapplicabilityDao.aggregate(queryToInapplicabilities).allowDiskUse(true);

    ArrayList<Bson> query = queryBuilder.getActivityStatusQueryToExtraction(center, rns, acronyms, inapplicabilities);
    AggregateIterable<Document> results = collection.aggregate(query).allowDiskUse(true);

    if (results == null) {
      throw new DataNotFoundException("There are no results");
    }

    LinkedList<ActivityProgressResultExtraction> progress = new LinkedList<>();
    MongoCursor<Document> iterator = results.iterator();
    while (iterator.hasNext()) {
      Document next = (Document) iterator.next();
      progress.add(ActivityProgressResultExtraction.deserialize(next.toJson()));
    }

    return progress;
  }

}