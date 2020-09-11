package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantDaoBean extends MongoGenericDao<Document> implements ParticipantDao {

  private static final String COLLECTION_NAME = "participant";
  private static final String TOKEN_LIST_FIELD = "tokenList";
  private static final String EMAIL = "email";
  private static final String RN = "recruitmentNumber";
  private static final String PUSH = "$push";
  private static final String PULL = "$pull";
  private static final String SET = "$set";
  private static final String PASSWORD = "password";
  private static final String ID = "_id";
  private static final String EMPTY = "";
  private static final String NAME = "name";
  private static final String SEX = "sex";
  private static final String FIELD_CENTER_ACRONYM = "fieldCenter.acronym";
  private static final String FIELD_CENTER_CODE = "fieldCenter.code";
  private static final String BIRTHDATE_VALUE = "birthdate.value";
  private static final String LATE = "late";
  private static final String IDENTIFIED = "identified";

  @Inject
  private FieldCenterDao fieldCenterDao;

  public ParticipantDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(Participant participant) {
    Document parsed = Document.parse(Participant.serialize(participant));
    parsed.append("created", new Date());
    this.collection.insertOne(parsed);
  }

  @Override
  public void update(Participant participant) {
    this.collection.updateOne(new Document(RN, participant.getRecruitmentNumber()), new Document(SET, new Document(NAME, participant.getName())
      .append(SEX, participant.getSex().toString()).append(FIELD_CENTER_ACRONYM, participant.getFieldCenter().getAcronym())
      .append(FIELD_CENTER_CODE, participant.getFieldCenter().getCode()).append(BIRTHDATE_VALUE, participant.getBirthdate().getFormattedValue())
      .append(LATE, participant.getLate()).append(IDENTIFIED, participant.isIdentified())));
  }

  @Override
  public void addAuthToken(String email, String Token) {
    this.collection.updateOne(new Document(EMAIL, email), new Document(PUSH, new Document(TOKEN_LIST_FIELD, Token)));
  }

  @Override
  public void removeAuthToken(String email, String Token) {
    this.collection.updateOne(new Document(EMAIL, email), new Document(PULL, new Document(TOKEN_LIST_FIELD, Token)));
  }

  @Override
  public boolean exists(Long rn) {
    Document result = this.collection.find(eq(RN, rn)).first();
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
    MongoCursor<Long> cursor = collection.distinct(RN, query, Long.class).iterator();

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
    UpdateResult updateResult = this.collection.updateOne(new Document(EMAIL, email), new Document(SET, new Document(PASSWORD, password)));
    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException("Participant no found");
    }
  }

  @Override
  public Participant getParticpant(ObjectId id) throws DataNotFoundException {
    Document participantFound = this.collection.find(eq(ID, id)).first();
    if (participantFound == null) {
      throw new DataNotFoundException(new Throwable("Participant with id: {" + id + "} not found."));
    }
    return Participant.deserialize(participantFound.toJson());
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
    Document result = this.collection.find(eq(RN, recruitmentNumber)).first();
    return result.getObjectId(ID);
  }

  @Override
  public String getParticipantFieldCenterByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    Document result = this.collection.find(eq(RN, recruitmentNumber)).first();
    if (result == null) {
      throw new DataNotFoundException(new Throwable("Participant with recruitment number {" + recruitmentNumber + "} not found."));
    }

    Participant participant = Participant.deserialize(result.toJson());
    return participant.getFieldCenter().getAcronym();
  }

  @Override
  public Long countParticipantActivities(String centerAcronym) throws DataNotFoundException {
    Document query = new Document();

    query.put("fieldCenter.acronym", centerAcronym);
    query.put("late", Boolean.FALSE);

    return collection.count(query);
  }

  @Override
  public Boolean updateEmail(ObjectId id, String email) throws DataNotFoundException, AlreadyExistException {
    Boolean result;
    UpdateResult updateResult = this.collection.updateOne(new Document(ID, id), new Document(SET, new Document( EMAIL, email).append(TOKEN_LIST_FIELD, new ArrayList())));

    result = updateResult.getModifiedCount() != 0;

    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Participant no found"));
    }
    if(!result){
      throw new AlreadyExistException(new Throwable("Mail already exists"));
    }

    return result;
  }

  @Override
  public String getEmail(ObjectId id) throws DataNotFoundException {
    Document participantFound = this.collection.find(eq(ID, id)).first();
    if (participantFound == null) {
      throw new DataNotFoundException(new Throwable("Participant with id: {" + id + "} not found."));
    }

    return Participant.deserialize(participantFound.toJson()).getEmail();
  }

  @Override
  public Boolean deleteEmail(ObjectId id) throws DataNotFoundException {
    UpdateResult updateResult = this.collection.updateOne(new Document(ID, id), new Document(SET, new Document(EMAIL, EMPTY).append(TOKEN_LIST_FIELD, new ArrayList())));

    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Participant no found"));
    }

    return updateResult.getModifiedCount() != 0;
  }
}
