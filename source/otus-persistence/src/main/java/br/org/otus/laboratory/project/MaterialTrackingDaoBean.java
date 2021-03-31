package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.builder.MaterialTrackingQueryBuilder;
import br.org.otus.laboratory.project.transportation.MaterialTrail;
import br.org.otus.laboratory.project.transportation.TrailHistoryRecord;
import br.org.otus.laboratory.project.transportation.persistence.MaterialTrackingDao;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

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
        Document currentMaterialLocation = super.collection.find(new Document("materialCode", materialCode).append("isCurrentLocation", true)).first();

        if (currentMaterialLocation != null) {
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
                new Document("$group", new Document("_id", "").append("materialCodes", new Document("$push", "$materialCode")))
        )).first();

        if (first != null) {
            materialCodeList = (ArrayList<String>) first.get("materialCodes");
        }
        return materialCodeList;
    }

    @Override
    public void insert(ArrayList<Document> trails) {
        collection.insertMany(trails);
    }

    @Override
    public void updatePrevious(List<String> materialCodeList) {
        collection.updateMany(new Document("materialCode", new Document("$in", materialCodeList)), new Document("$set", new Document("isCurrentLocation", false)));
    }

    @Override
    public ArrayList<String> verifyNeedToRollback(List<String> removedAliquotCodes, ObjectId transportationLotId) {
        ArrayList<String> materialCodeList = new ArrayList<>();
        Document first = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("materialCode", new Document("$in", removedAliquotCodes)).append("transportationLotId", transportationLotId).append("isCurrentLocation", true)
                ),
                new Document("$group", new Document("_id", "").append("materialCodes", new Document("$push", "$materialCode")))
        )).first();

        if (first != null) {
            materialCodeList = (ArrayList<String>) first.get("materialCodes");
        }
        return materialCodeList;
    }

    @Override
    public ArrayList<ObjectId> getLastTrailsToRollBack(ArrayList<String> materialsToRollBack) {
        ArrayList<ObjectId> trailIdList = new ArrayList<>();
        Document first = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("materialCode", new Document("$in", materialsToRollBack))
                ),
                new Document("$sort",
                        new Document("materialCode", 1).append("OperationDate", 1)
                ),
                new Document("$group",
                        new Document("_id", "$materialCode").append("trailIds", new Document("$push", "$_id"))
                ),
                new Document("$group",
                        new Document("_id", "").append("activateTrails", new Document("$push", new Document("$arrayElemAt", Arrays.asList("$trailIds", -1)))))
        )).first();

        if (first != null) {
            trailIdList = (ArrayList<ObjectId>) first.get("activateTrails");
        }
        return trailIdList;
    }

    @Override
    public void activateTrails(ArrayList<ObjectId> trailsToActivate) {
        collection.updateMany(new Document("_id", new Document("$in", trailsToActivate)), new Document("$set", new Document("isCurrentLocation", true)));
    }

    @Override
    public void removeTransportation(ObjectId transportationLotId) {
        collection.deleteMany(new Document("transportationLotId", transportationLotId));
    }

    @Override
    public void removeMaterialTransportation(ObjectId transportationLotId, ArrayList<String> materialsToRollBack) {
        collection.deleteMany(new Document("transportationLotId", transportationLotId).append("materialCode", new Document("$in", materialsToRollBack)));
    }

    @Override
    public ArrayList<String> verifyNeedToRollback(ObjectId transportationLotId) {
        ArrayList<String> materialCodeList = new ArrayList<>();
        Document first = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("transportationLotId", transportationLotId).append("isCurrentLocation", true)
                ),
                new Document("$group", new Document("_id", "").append("materialCodes", new Document("$push", "$materialCode")))
        )).first();

        if (first != null) {
            materialCodeList = (ArrayList<String>) first.get("materialCodes");
        }
        return materialCodeList;
    }

    @Override
    public void insert(MaterialTrail materialTrail) {
        Document parsed = Document.parse(MaterialTrail.serializeToJsonString(materialTrail));
        parsed.remove("_id");
        super.persist(parsed);
    }

    @Override
    public void setReceived(MaterialTrail materialTrail) {
        collection.updateMany(new Document("_id", materialTrail.get_id()), new Document("$set", new Document("isReceived", true)));
    }

    @Override
    public List<String> verifyIfAliquotsAreInOrigin(List<String> aliquotsOfLocationPoint, String locationPointId) {
        ArrayList<String> materialCodes = new ArrayList<>();
        Document first = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("materialCode", new Document("$in", aliquotsOfLocationPoint))
                                .append("isCurrentLocation", true)
                                .append("locationPoint", new Document("$ne", new ObjectId(locationPointId)))
                ),
                new Document("$group",
                        new Document("_id", "").append("materialCodes", new Document("$push", "$materialCode")))
        )).first();

        if (first != null) {
            materialCodes = (ArrayList<String>) first.get("materialCodes");
        }
        return materialCodes;
    }

    @Override
    public List<TrailHistoryRecord> getMaterialTrackingList(String materialCode) throws DataNotFoundException {
        ArrayList<TrailHistoryRecord> materialTracking = new ArrayList<>();
        AggregateIterable<Document> queryResult = collection.aggregate(new MaterialTrackingQueryBuilder().getMaterialTrackingListQuery(materialCode).build());

        for (Document document : queryResult) {
            materialTracking.add(TrailHistoryRecord.deserialize(document.toJson()));
        }

        if (materialTracking.size() == 0) {
            throw new DataNotFoundException("Tracking List not found for material code " + materialCode);
        }

        return materialTracking;
    }

    @Override
    public MaterialTrail getTrail(String materialCode, ObjectId transportationLotId) {
        MaterialTrail materialTrail = null;
        Document currentMaterialLocation = super.collection.find(new Document("materialCode", materialCode).append("transportationLotId", transportationLotId)).first();

        if (currentMaterialLocation != null) {
            materialTrail = MaterialTrail.deserialize(currentMaterialLocation.toJson());
        }

        return materialTrail;
    }
}
