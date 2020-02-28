package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.transportation.MaterialTrail;
import br.org.otus.laboratory.project.transportation.persistence.MaterialTrackingDao;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaterialTrackingDaoBean extends MongoGenericDao<Document> implements MaterialTrackingDao {

  private static final String COLLECTION_NAME = "laboratory_material_tracking";

  public MaterialTrackingDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public MaterialTrail getCurrent(String materialCode) {
    MaterialTrail materialTrail = null;
    Document currentMaterialLocation = super.collection.find(new Document("materialCode", materialCode).append("isCurrentLocation",true)).first();

    if (currentMaterialLocation != null){
      materialTrail = MaterialTrail.deserialize(currentMaterialLocation.toJson());
    }

    return materialTrail;
  }

  @Override
  public List<String> getAliquotsInLocation(String locationPointId) {
    List<String> materialCodeList = new ArrayList<>();
    Document first = collection.aggregate(Arrays.asList(
      new Document("$match",
        new Document("locationPoint", new ObjectId(locationPointId))
          .append("isCurrentLocation", true)
      ),
      new Document("$group", new Document("_id","").append("materialCodes", new Document("$push", "$materialCode")))
    )).first();

    if (first != null) {
      materialCodeList = (ArrayList<String>) first.get("materialCodes");
    }
    return  materialCodeList;
  }

  @Override
  public void insert(ArrayList<Document> trails) {
    collection.insertMany(trails);
  }

  @Override
  public void updatePrevious(ArrayList<String> materialCodeList) {
    collection.updateMany(new Document("materialCode",new Document("$in",materialCodeList)), new Document("$set", new Document("isCurrentLocation", false)));
  }
}
