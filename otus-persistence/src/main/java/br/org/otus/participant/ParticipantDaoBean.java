package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantDaoBean extends MongoGenericDao<Participant> implements ParticipantDao {

  private static final String COLLECTION_NAME = "participant";
  private static final String MATCH = "$match";
  private static final String LATE = "late";
  private static final Boolean LATE_VALUE = Boolean.FALSE;
  private static final String ID = "_id";
  private static final String ID_GROUP_VALUE = "$fieldCenter.acronym";
  private static final String SUM = "$sum";
  private static final Integer SUM_VALUE = 1;
  private static final String GOAL = "goal";
  private static final String LOOKUP = "$lookup";
  private static final String FROM = "from";
  private static final String FROM_VALUE = "field_center";
  private static final String LOCAL_FIELD = "localField";
  private static final String FOREIGN_FIELD = "foreignField";
  private static final String FOREIGN_FIELD_VALUE = "acronym";
  private static final String AS = "as";
  private static final String AS_VALUE = "center";

  @Inject
  private FieldCenterDao fieldCenterDao;

  public ParticipantDaoBean() {
    super(COLLECTION_NAME, Participant.class);
  }

  @Override
  public void persist(Participant participant) {
    this.collection.insertOne(participant);
  }
  
  @Override
  public boolean exists(Long rn) {
    Participant result = this.collection.find(eq("recruitmentNumber", rn)).first();
    return result != null;
  }

  @Override
  public Participant getLastInsertion(FieldCenter fieldCenter) throws DataNotFoundException {
    List<Bson> query = new ArrayList<>();
    Participant result = null;
    query.add(new Document("$match",new Document("fieldCenter.acronym", fieldCenter.getAcronym())));
    query.add(new Document("$addFields",new Document("convertedRN",new Document("$toString","$recruitmentNumber"))));
    query.add(new Document("$match", new Document("convertedRN", new Document("$regex","^"+fieldCenter.getCode()))));
    query.add(new Document("$sort", new Document("_id",-1)));
    query.add(new Document("$limit", 1));
    Participant participant = this.collection.aggregate(query).first();

    return participant;
  }

  @Override
  public ArrayList<Participant> find() {
    Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();

    ArrayList<Participant> participants = this.collection.find().into(new ArrayList<Participant>());
    for (Participant participant : participants) {
      String acronym = participant.getFieldCenter().getAcronym();
      participant.setFieldCenter(fieldCenterMap.get(acronym));
    }

    return participants;
  }

  @Override
  public ArrayList<Participant> findByFieldCenter(FieldCenter fieldCenter) {
    Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();

    ArrayList<Participant> participants = this.collection.find(eq("fieldCenter.acronym", fieldCenter.getAcronym()))
      .into(new ArrayList<Participant>());
    for (Participant participant : participants) {
      String acronym = participant.getFieldCenter().getAcronym();
      participant.setFieldCenter(fieldCenterMap.get(acronym));
    }

    return participants;
  }

  @Override
  public Participant findByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();

    Participant result = this.collection.find(eq("recruitmentNumber", recruitmentNumber)).first();

    if (result == null) {
      throw new DataNotFoundException(
        new Throwable("Participant with recruitment number {" + recruitmentNumber + "} not found."));
    }

    String acronym = result.getFieldCenter().getAcronym();
    result.setFieldCenter(fieldCenterMap.get(acronym));
    return result;
  }

  @Override
  public Long countParticipantActivities(String centerAcronym) throws DataNotFoundException {

    Document query = new Document();

    query.put("fieldCenter.acronym", centerAcronym);
    query.put("late", Boolean.FALSE);

    return collection.count(query);
  }
}
