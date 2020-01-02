package br.org.otus.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ActivityExtractionDao;
import org.ccem.otus.service.ParseQuery;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;

import br.org.mongodb.MongoGenericDao;

public class ActivityExtractionDaoBean extends MongoGenericDao<Document> implements ActivityExtractionDao {

  public static final String COLLECTION_NAME = "activity";

  @Inject
  private ParticipantDao participantDao;

  public ActivityExtractionDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public HashMap<Long, String> getParticipantFieldCenter(String acronym, Integer version) {
    List<Long> recruitmentNumbersByActivity = getRecruitmentNumbersByActivity(acronym, version);
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $match: {\n" + 
        "      \"recruitmentNumber\": { $in: " + recruitmentNumbersByActivity + "}\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    \"$project\": {\n" + 
        "      \"_id\": \"$recruitmentNumber\",\n" + 
        "      \"fieldCenter\": \"$fieldCenter.acronym\"\n" + 
        "    }\n" + 
        "  }"));
    HashMap<Long, String> fieldCenterByRecruitmentNumber = new HashMap<>();
    AggregateIterable<Document> results = participantDao.aggregate(pipeline).allowDiskUse(true);
    MongoCursor<Document> iterator = results.iterator();
    while(iterator.hasNext()) {
      fieldCenterByRecruitmentNumber.put((Long) iterator.next().get("_id"), iterator.next().getString("fieldCenter"));
    }
    
    return fieldCenterByRecruitmentNumber;
  }
  
  private List<Long> getRecruitmentNumbersByActivity(String acronym, Integer version) {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    \"$match\": {\n" + 
        "      \"surveyForm.acronym\": " + acronym + ",\n" + 
        "      \"surveyForm.version\": " + version + ",\n" + 
        "      \"isDiscarded\": false\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    \"$group\": {\n" + 
        "      \"_id\": \"$participantData.recruitmentNumber\"\n" + 
        "    }\n" + 
        "  }"));
    ArrayList<Long> rns = new ArrayList<>();
    AggregateIterable<Document> results = collection.aggregate(pipeline).allowDiskUse(true);
    MongoCursor<Document> iterator = results.iterator();
    while(iterator.hasNext()) {
      rns.add(Long.parseLong(iterator.next().get("_id").toString()));
    }
    
    return rns;
  }

}
