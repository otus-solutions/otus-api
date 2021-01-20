package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAddressAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participant_contact.Address;
import org.ccem.otus.participant.persistence.ParticipantContactAttemptDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ParticipantContactAttemptDaoBean extends MongoGenericDao<Document> implements ParticipantContactAttemptDao {

  private static final String COLLECTION_NAME = "participant_contact_attempt";
  private static final String USER_COLLECTION_NAME = "user";
  private static final String RECRUITMENT_NUMBER_FIELD_NAME = "recruitmentNumber";
  private static final String OBJECT_TYPE_FIELD_NAME = "objectType";
  private static final String ADDRESS_FIELD_NAME = "address";
  private static final String REGISTEREDBY_FIELD_NAME = "registeredBy";
  private static final String CONCAT_ADDRESS = "concatAddress";

  public ParticipantContactAttemptDaoBean(){
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId create(ParticipantContactAttempt participantContactAttempt) throws DataFormatException {
    Document parsed = Document.parse(ParticipantContactAttempt.serialize(participantContactAttempt));
    collection.insertOne(parsed);
    return parsed.getObjectId(ID_FIELD_NAME);
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(ID_FIELD_NAME, participantContactOID));
    if(deleteResult.getDeletedCount() == 0){
      throw new DataNotFoundException("Participant contact with id { " + participantContactOID.toString() + " } was not found");
    }
  }

  @Override
  public void updateAttemptAddress(Long recruitmentNumber, String objectType, String position, Address address) throws DataNotFoundException {
    ArrayList<Bson> pipeline = new ArrayList<>();
    String addressField = ADDRESS_FIELD_NAME.concat(".").concat(position).concat(".").concat("value").concat(".");
    pipeline.add(new Document("$match",
      new Document(RECRUITMENT_NUMBER_FIELD_NAME, recruitmentNumber)
        .append(OBJECT_TYPE_FIELD_NAME, objectType)
        .append(ADDRESS_FIELD_NAME.concat(".").concat(position), new Document("$exists", true))
        .append("isValid", true)
    ));
    AggregateIterable<Document> result = collection.aggregate(pipeline);
    MongoCursor<Document> iterator = result.iterator();

    while (iterator.hasNext()) {
      Document document = iterator.next();
      ParticipantContactAttempt participantContactAttempt = ParticipantContactAttempt.deserialize(document.toJson());
      collection.updateOne(new Document("_id", participantContactAttempt.get_id()),
        new Document("$set",
          new Document(addressField.concat("postalCode"), address.getPostalCode())
            .append(addressField.concat("street"), address.getStreet())
            .append(addressField.concat("streetNumber"), address.getStreetNumber())
            .append(addressField.concat("complements"), address.getComplements())
            .append(addressField.concat("neighbourhood"), address.getNeighbourhood())
            .append(addressField.concat("city"), address.getCity())
            .append(addressField.concat("country"), address.getCountry())
            .append(addressField.concat("state"), address.getState())
            .append(addressField.concat("census"), address.getCensus())
        )
      );
    }
  }

  @Override
  public void changeAddress(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException {
    collection.updateMany(and(
        eq(RECRUITMENT_NUMBER_FIELD_NAME, recruitmentNumber),
        eq(OBJECT_TYPE_FIELD_NAME, objectType),
        eq(ADDRESS_FIELD_NAME.concat(".").concat(position), new Document("$exists", true)),
        eq("isValid", true)
      ),
      new Document("$set",
        new Document("isValid", false)
        ));
  }

  @Override
  public ArrayList<ParticipantContactAddressAttempt> findAddressAttempts(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException {
    try{
      ArrayList<Bson> pipeline = new ArrayList<>();

      pipeline.add(new Document("$match",
        new Document(RECRUITMENT_NUMBER_FIELD_NAME, recruitmentNumber)
          .append(OBJECT_TYPE_FIELD_NAME, objectType)
          .append(ADDRESS_FIELD_NAME.concat(".").concat(position), new Document("$exists", true))
      ));

      pipeline.add(new Document("$lookup", new Document("from", USER_COLLECTION_NAME)
        .append("localField", REGISTEREDBY_FIELD_NAME)
        .append("foreignField", "_id")
        .append("as", "user")
      ));

      pipeline.add(new Document("$addFields",
        new Document("userEmail", new Document("$arrayElemAt", Arrays.asList("$user.email", 0)))));

      pipeline.add(new Document("$group",
        new Document("_id", "$address."+position+".value")
          .append("attemptList", new Document("$push", "$$ROOT"))
      ));

      pipeline.add(new Document("$sort",
        new Document("attemptList._id", new Integer(-1))));

      pipeline.add(new Document("$project",
        new Document("_id", 0)
          .append("address", "$_id")
          .append("attemptList", 1)
      ));

      pipeline.add(new Document("$addFields",
        new Document("fullAddress", new Document("$concat",
          Arrays.asList(new Document("$toString", "$address.census")," - ",
            "$address.street",", ", new Document("$toString","$address.streetNumber"),"/", "$address.complements", " - ",
            "$address.neighbourhood", " - ",new Document("$toString","$address.postalCode"), " - ", "$address.city", ", ", "$address.state", "(","$address.country", ")")
      ))));


      AggregateIterable<Document> result = collection.aggregate(pipeline);
      ArrayList<ParticipantContactAddressAttempt> attempts = new ArrayList<>();

      MongoCursor<Document> iterator = result.iterator();

      while (iterator.hasNext()) {
        Document document = iterator.next();

        ParticipantContactAddressAttempt participantContactAddressAttempt = ParticipantContactAddressAttempt.deserialize(document.toJson());
        attempts.add(participantContactAddressAttempt);
      }
      return attempts;
    }
    catch (NullPointerException e){
      throw new DataNotFoundException("No participant contact attempts found for recruitmentNumber {" + recruitmentNumber.toString() + "}");
    }
  }
}
