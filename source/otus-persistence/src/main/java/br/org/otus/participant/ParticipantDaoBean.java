package br.org.otus.participant;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import br.org.mongodb.MongoGenericDao;

public class ParticipantDaoBean extends MongoGenericDao<Document> implements ParticipantDao {

  private static final String COLLECTION_NAME = "participant";
  private static final String TOKEN_LIST_FIELD = "tokenList";
  private static final String EMAIL = "email";
  private static final String PUSH = "$push";
  private static final String PULL = "$pull";
  private static final String SET = "$set";
  private static final String PASSWORD = "password";

  @Inject
  private FieldCenterDao fieldCenterDao;

  public ParticipantDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(Participant participant) {
    Document parsed = Document.parse(Participant.serialize(participant));
    this.collection.insertOne(parsed);
  }

  @Override
  public void addAuthToken(String email, String Token) {
    this.collection.updateOne(new Document(EMAIL, email),new Document(PUSH, new Document(TOKEN_LIST_FIELD, Token)));
  }

  @Override
  public void removeAuthToken(String email, String Token) {
    this.collection.updateOne(new Document(EMAIL, email),new Document(PULL, new Document(TOKEN_LIST_FIELD, Token)));
  }

  @Override
  public boolean exists(Long rn) {
    Document result = this.collection.find(eq("recruitmentNumber", rn)).first();
    return result == null ? false : true;
  }

  @Override
  public Participant getLastInsertion(FieldCenter fieldCenter) throws DataNotFoundException {
    List<Bson> query = new ArrayList<>();
    query.add(new Document("$match", new Document("fieldCenter.acronym", fieldCenter.getAcronym())));
    query.add(new Document("$addFields", new Document("convertedRN", new Document("$toString", "$recruitmentNumber"))));
    query.add(new Document("$match", new Document("convertedRN", new Document("$regex", "^" + fieldCenter.getCode()))));
    query.add(new Document("$sort", new Document("_id", -1)));
    query.add(new Document("$limit", 1));
    query.add(new Document("$project", new Document("convertedRN", 0)));
    Document participant = this.collection.aggregate(query).first();

    if (participant == null) {
      throw new DataNotFoundException("Any insertion found for the given field center");
    }

    return Participant.deserialize(participant.toJson());
  }

  @Override
  public ArrayList<Long> getRecruitmentNumbersByFieldCenter(String center) throws DataNotFoundException {
    Document query = new Document("fieldCenter.acronym", center);
    MongoCursor<Long> cursor = collection.distinct("recruitmentNumber", query, Long.class).iterator();

    ArrayList<Long> rns = new ArrayList<Long>();

    try {
      while (cursor.hasNext()) {
        rns.add(cursor.next());
      }
    } finally {
      cursor.close();
    }

    if (rns.isEmpty()) {
      throw new DataNotFoundException("Any participant found for given center: " + center);
    }

    return rns;
  }

  @Override
  public Participant fetchByEmail(String userEmail) throws DataNotFoundException {
    Document participantFound = this.collection.find(eq(EMAIL, userEmail)).first();
    if (participantFound == null) {
      throw new DataNotFoundException(new Throwable("Participant with email: {" + userEmail + "} not found."));
    }
    return Participant.deserialize(participantFound.toJson());
  }

  @Override
  public Participant fetchByToken(String token) throws DataNotFoundException {
    Document participantFound = this.collection.find(eq(TOKEN_LIST_FIELD, token)).first();
    if (participantFound == null) {
      throw new DataNotFoundException(new Throwable("Participant token not found."));
    }
    return Participant.deserialize(participantFound.toJson());
  }

  @Override
  public void registerPassword(String email, String password) throws DataNotFoundException {
    UpdateResult updateResult = this.collection.updateOne(new Document(EMAIL, email), new Document(SET,new Document(PASSWORD, password)));
    if(updateResult.getMatchedCount() == 0){
      throw new DataNotFoundException("Participant no found");
    }
  }

  @Override
  public ArrayList<Participant> find() {
    Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();
    ArrayList<Participant> participants = new ArrayList<>();

    FindIterable<Document> find = this.collection.find();

    MongoCursor<Document> iterator = find.iterator();
    while (iterator.hasNext()) {
      Document document = (Document) iterator.next();
      Participant participant = Participant.deserialize(document.toJson());
      String acronym = participant.getFieldCenter().getAcronym();
      participant.setFieldCenter(fieldCenterMap.get(acronym));
      participants.add(participant);
    }

    return participants;
  }

  @Override
  public ArrayList<Participant> findByFieldCenter(FieldCenter fieldCenter) {
    Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();
    ArrayList<Participant> participants = new ArrayList<>();

    FindIterable<Document> find = this.collection.find(eq("fieldCenter.acronym", fieldCenter.getAcronym()));

    MongoCursor<Document> iterator = find.iterator();
    while (iterator.hasNext()) {
      Document document = (Document) iterator.next();
      Participant participant = Participant.deserialize(document.toJson());
      String acronym = participant.getFieldCenter().getAcronym();
      participant.setFieldCenter(fieldCenterMap.get(acronym));
      participants.add(participant);
    }

    return participants;
  }

  @Override
  public Participant findByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    Map<String, FieldCenter> fieldCenterMap = fieldCenterDao.getFieldCentersMap();

    Document result = this.collection.find(eq("recruitmentNumber", recruitmentNumber)).first();
    if (result == null) {
      throw new DataNotFoundException(new Throwable("Participant with recruitment number {" + recruitmentNumber + "} not found."));
    }

    Participant participant = Participant.deserialize(result.toJson());
    String acronym = participant.getFieldCenter().getAcronym();
    participant.setFieldCenter(fieldCenterMap.get(acronym));
    return participant;
  }

  @Override
  public ObjectId findIdByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    Document result = this.collection.find(eq("recruitmentNumber", recruitmentNumber)).first();
    return result.getObjectId("_id");
  }

  @Override
  public Long countParticipantActivities(String centerAcronym) throws DataNotFoundException {
    Document query = new Document();

    query.put("fieldCenter.acronym", centerAcronym);
    query.put("late", Boolean.FALSE);

    return collection.count(query);
  }

}