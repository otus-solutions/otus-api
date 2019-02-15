package br.org.otus.laboratory.project;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;

public class TransportationLotDaoBean extends MongoGenericDao<Document> implements TransportationLotDao {

    private static final String COLLECTION_NAME = "transportation_lot";
    private static final int CODE_TRANSPORTATION_LOT = 300000000;

    public TransportationLotDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public List<TransportationLot> find() {
        ArrayList<TransportationLot> transportationLots = new ArrayList<>();

        AggregateIterable output = collection.aggregate(Arrays.asList(
                Aggregates.lookup("aliquot", "_id", "transportationLotId", "aliquotList")));
        for (Object result : output) {
            Document document = (Document) result;
            transportationLots.add(TransportationLot.deserialize(document.toJson()));
        }
        return transportationLots;
    }

    @Override
    public ObjectId persist(TransportationLot transportationLot) {
        Document parsed = Document.parse(TransportationLot.serialize(transportationLot));
        parsed.remove("aliquotList");
        parsed.remove("_id");
        super.persist(parsed);
        return (ObjectId) parsed.get("_id");
    }

    @Override
    public Integer getLastTransportationLotCode() {
        Integer newLotCode = new Integer(CODE_TRANSPORTATION_LOT);
        FindIterable lastTransportationLotCode = super.findLast().projection(new Document("code", -1).append("_id", 0));
        for (Object result : lastTransportationLotCode) {
            Document document = (Document) result;
            newLotCode = Integer.parseInt(document.get("code").toString());
        }
        return newLotCode;
    }

    @Override
    public TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException {
        Document parsed = Document.parse(TransportationLot.serialize(transportationLot));
        parsed.remove("aliquotList");
        parsed.remove("_id");
        UpdateResult updateLotData = collection.updateOne(eq("code", transportationLot.getCode()), new Document("$set", parsed));

        if (updateLotData.getMatchedCount() == 0) {
            throw new DataNotFoundException(new Throwable("Transportation Lot not found"));
        }

        return transportationLot;
    }


    @Override
    public void delete(String code) throws DataNotFoundException {
        Document updateResult = collection.findOneAndReplace(new Document("code", code), new Document("objectType","DeletedTransportationLot").append("code", code));
        if (updateResult.size() == 0) {
            throw new DataNotFoundException(new Throwable("Transportation Lot does not exist"));
        }
    }


    @Override
    public String checkIfThereInTransport(String aliquotCode) {
        Document document = collection.find(eq("aliquotList.code", aliquotCode)).first();
        if (document != null) {
            TransportationLot lot = TransportationLot.deserialize(document.toJson());
            return lot.getCode();
        } else {
            return null;
        }
    }

    @Override
    public HashSet<Document> getAliquotsInfoInTransportationLots() throws DataNotFoundException {
        Document projection = new Document("aliquotsInfo", 1);
        HashSet<Document> aliquotsInfos = new HashSet<>();

        FindIterable<Document> documents = collection.find().projection(projection);
        documents.forEach((Block<? super Document>) document -> {
            List<Document> aliquotsInfo = (List<Document>) document.get("aliquotsInfo");
            if (aliquotsInfo != null) {
                aliquotsInfos.addAll(aliquotsInfo);
            }
        });
        aliquotsInfos.remove(null);
        return aliquotsInfos;
    }

    @Override
    public TransportationLot findByCode(String code) throws DataNotFoundException {
        Document document = collection.aggregate(
                Arrays.asList(
                        Aggregates.match(eq("code", code)),
                        Aggregates.match(eq("objectType", "TransportationLot")),
                        Aggregates.lookup("aliquot", "_id", "transportationLotId", "aliquotList")
                )).first();

        if (document != null) {
            return TransportationLot.deserialize(document.toJson());
        } else {
            throw new DataNotFoundException(new Throwable("Transportation Lot not found"));
        }
    }

    @Override
    public TransportationLot find(ObjectId transportationLotId) throws DataNotFoundException {
        Document result = collection.find(eq("_id", transportationLotId)).first();
        if (result == null) {
            throw new DataNotFoundException(new Throwable("Transportation Lot not found"));
        }
        return TransportationLot.deserialize(result.toJson());
    }
}
