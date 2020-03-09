package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.transportation.TransportLocationPoint;
import br.org.otus.laboratory.project.transportation.persistence.TransportLocationPointDao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public class TransportLocationPointDaoBean extends MongoGenericDao<Document> implements TransportLocationPointDao {

  private static final String COLLECTION_NAME = "transport_location_point";

  public TransportLocationPointDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(TransportLocationPoint transportLocationPoint) {
    Document parsed = Document.parse(TransportLocationPoint.serializeToJsonString(transportLocationPoint));
    super.persist(parsed);
  }

  @Override
  public void update(TransportLocationPoint transportLocationPoint) throws DataNotFoundException {
    Document parsed = Document.parse(TransportLocationPoint.serializeToJsonString(transportLocationPoint));
    parsed.remove("_id");
    UpdateResult updateResult = super.collection.updateOne(new Document("_id", transportLocationPoint.get_id()), new Document("$set", parsed));
    if (updateResult.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Transport location point not found"));
    }
  }

  @Override
  public void delete(String locationPointId) throws DataNotFoundException {
    DeleteResult deleteResult = super.collection.deleteOne(new Document("_id", new ObjectId(locationPointId)));
    if (deleteResult.getDeletedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Transport location point not found"));
    }
  }
}
