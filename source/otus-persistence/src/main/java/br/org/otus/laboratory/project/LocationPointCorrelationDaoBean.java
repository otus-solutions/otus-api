package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.transportation.persistence.LocationPointCorrelationDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointListDTO;
import com.google.gson.GsonBuilder;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;

public class LocationPointCorrelationDaoBean extends MongoGenericDao<Document> implements LocationPointCorrelationDao {
  private static final String COLLECTION_NAME = "user_location_point_correlation";
  private static final String PUSH_OPERATOR = "$push";
  private static final String PULL_OPERATOR = "$pull";
  private static final String USERS_FIELD = "users";

  public LocationPointCorrelationDaoBean() {
      super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void create(ObjectId id) {
    Document userOnLocation = this.collection.find(eq("_id", id)).first();
    if (userOnLocation == null){
      this.collection.insertOne(new Document("_id",id).append("users",new ArrayList<>()));
    }
  }

  @Override
  public void addUser(ObjectId userId, ObjectId locationPointId) {
    Document userOnLocation = this.collection.find(and(eq("_id", locationPointId), in("users", userId))).first();
    if (userOnLocation == null){
      this.collection.updateOne(new Document("_id",locationPointId),new Document( PUSH_OPERATOR, new Document( USERS_FIELD, userId) ), new UpdateOptions().upsert(true));
    }
  }

  @Override
  public void removeUser(ObjectId userId, ObjectId locationPointId) {
    this.collection.updateOne(new Document("_id",locationPointId),new Document( PULL_OPERATOR, new Document( USERS_FIELD, userId) ));
  }

  @Override
  public TransportLocationPointListDTO getLocationPointList() {
    ArrayList<Bson> pipeline = new ArrayList<Bson>();
    TransportLocationPointListDTO transportLocationPointListDTO = null;
    pipeline.add(parseQuery("{\n" +
      "        $lookup: {\n" +
      "         from: \"transport_location_point\",\n" +
      "         localField: \"_id\",    \n" +
      "         foreignField: \"_id\",  \n" +
      "         as: \"locationData\"\n" +
      "      }\n" +
      "    }"));
    pipeline.add(parseQuery("{\n" +
      "      $lookup:\n" +
      "         {\n" +
      "           from: \"user\",\n" +
      "           let: { user_ids: \"$users\"},\n" +
      "           pipeline: [\n" +
      "              { $match:\n" +
      "                 { $expr:\n" +
      "                    { $and:\n" +
      "                       [\n" +
      "                         { $in: [ \"$_id\",  \"$$user_ids\" ] }\n" +
      "                       ]\n" +
      "                    }\n" +
      "                 }\n" +
      "              }\n" +
      "           ],\n" +
      "           as: \"users\"\n" +
      "         }\n" +
      "    }"));
    pipeline.add(parseQuery("{$unwind: {path:\"$users\",preserveNullAndEmptyArrays:true}}"));
    pipeline.add(parseQuery("{\n" +
      "        $group: { _id: \"$locationData\", users:{$push: \"$users.email\"} }\n" +
      "    }"));
    pipeline.add(parseQuery(" {\n" +
      "        $project: {\n" +
      "            _id: { $arrayElemAt: [\"$_id._id\",0]},\n" +
      "            name:{ $arrayElemAt: [\"$_id.name\",0]},\n" +
      "            users:\"$users\"\n" +
      "        }\n" +
      "    }"));
    pipeline.add(parseQuery("{\n" +
      "      $group: { _id: {}, transportLocationPoints: {$push: \"$$ROOT\"}}  \n" +
      "    }"));

    Document output = collection.aggregate(pipeline).first();
    if (output != null) {
      transportLocationPointListDTO = TransportLocationPointListDTO.deserialize(output.toJson());
    }
    return transportLocationPointListDTO;
  }

  @Override
  public TransportLocationPointListDTO getUserLocationPoints(ObjectId userId) throws DataNotFoundException {
    ArrayList<Bson> pipeline = new ArrayList<Bson>();
    pipeline.add(parseQuery("{\n" +
      "      $unwind: \"$users\"  \n" +
      "    }"));
    pipeline.add(new Document("$match", new Document("users",userId)));
    return getLocationPoints(pipeline);
  }

  @Override
  public TransportLocationPointListDTO getLocationPoints() throws DataNotFoundException {
    ArrayList<Bson> pipeline = new ArrayList<Bson>();
    return getLocationPoints(pipeline);
  }

  private TransportLocationPointListDTO getLocationPoints(ArrayList<Bson> pipeline) throws DataNotFoundException {
    pipeline.add(parseQuery("{\n" +
      "        $lookup: {\n" +
      "         from: \"transport_location_point\",\n" +
      "         localField: \"_id\",    \n" +
      "         foreignField: \"_id\",  \n" +
      "         as: \"locationData\"\n" +
      "      }\n" +
      "    }"));
    pipeline.add(parseQuery("{\n" +
      "        $project: {\n" +
      "            _id:{ $arrayElemAt: [\"$locationData._id\",0]},\n" +
      "            name:{ $arrayElemAt: [\"$locationData.name\",0]},\n" +
      "            users:[]\n" +
      "        }\n" +
      "    }"));
    pipeline.add(parseQuery(" {\n" +
      "      $group: { _id: {}, transportLocationPoints: {$push: \"$$ROOT\"}}  \n" +
      "    }"));

    Document output = collection.aggregate(pipeline).first();
    if (output == null) {
      throw new DataNotFoundException(new Throwable("Transport location point not found"));
    }

    return TransportLocationPointListDTO.deserialize(output.toJson());
  }

  private Bson parseQuery(String stage) {
    return new GsonBuilder().create().fromJson(stage, Document.class);
  }
}
