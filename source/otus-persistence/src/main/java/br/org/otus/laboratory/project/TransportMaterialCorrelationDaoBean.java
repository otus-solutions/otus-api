package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.transportation.TransportMaterialCorrelation;
import br.org.otus.laboratory.project.transportation.persistence.TransportMaterialCorrelationDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public class TransportMaterialCorrelationDaoBean extends MongoGenericDao<Document> implements TransportMaterialCorrelationDao {

  private static final String COLLECTION_NAME = "transport_material_correlation";

  public TransportMaterialCorrelationDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(TransportMaterialCorrelation transportMaterialCorrelation) {
    Document parsed = Document.parse(TransportMaterialCorrelation.serializeToJsonString(transportMaterialCorrelation));
    super.persist(parsed);
  }

  @Override
  public TransportMaterialCorrelation get(ObjectId lotId) throws DataNotFoundException {
    Document found = collection.find(new Document("_id", lotId)).first();
    if (found == null){
      throw new DataNotFoundException(new Throwable("Transport material correlation not found"));
    }
    return TransportMaterialCorrelation.deserialize(found.toJson());
  }
}