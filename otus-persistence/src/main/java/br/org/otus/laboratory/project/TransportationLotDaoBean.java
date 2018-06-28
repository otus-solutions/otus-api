package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.aliquot.WorkAliquotFactory;
import br.org.otus.laboratory.project.transportation.TransportationLot;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TransportationLotDaoBean extends MongoGenericDao<Document> implements TransportationLotDao {
    private static final String COLLECTION_NAME = "transportation_lot";

    @Inject
    private ParticipantLaboratoryDao participantLaboratoryDao;
    @Inject
    private ParticipantDao participantDao;
    @Inject
    private LaboratoryConfigurationDao laboratoryConfigurationDao;

    public TransportationLotDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void persist(TransportationLot transportationLot) {
        transportationLot.setCode(laboratoryConfigurationDao.createNewLotCodeForTransportation());

        super.persist(TransportationLot.serialize(transportationLot));
    }

    @Override
    public TransportationLot update(TransportationLot transportationLot) throws DataNotFoundException {
        Document parsed = Document.parse(TransportationLot.serialize(transportationLot));
        parsed.remove("_id");

        UpdateResult updateLotData = collection.updateOne(eq("code", transportationLot.getCode()),
                new Document("$set", parsed), new UpdateOptions().upsert(false));

        if (updateLotData.getMatchedCount() == 0) {
            throw new DataNotFoundException(
                    new Throwable("Transportation Lot not found"));
        }

        return transportationLot;
    }

    @Override
    public List<TransportationLot> find() {
        ArrayList<TransportationLot> transportationLots = new ArrayList<>();

        FindIterable<Document> result = collection.find();
        result.forEach((Block<Document>) document -> transportationLots.add(TransportationLot.deserialize(document.toJson())));
        return transportationLots;
    }

    @Override
    public void delete(String id) throws DataNotFoundException {
        DeleteResult deleteResult = collection.deleteOne(eq("code", id));
        if (deleteResult.getDeletedCount() == 0) {
            throw new DataNotFoundException(new Throwable("Transportation Lot does not exist"));
        }
    }

    @Override
    public List<WorkAliquot> getAliquots() throws DataNotFoundException {
        return WorkAliquotFactory.getAliquotList(participantLaboratoryDao, participantDao);
        
    }
    
    public List<WorkAliquot> getAliquotsByPeriod(String initialDate, String finalDate, String fieldCenterAcronym) throws DataNotFoundException {        
        return WorkAliquotFactory.getAliquotListByPeriod(participantLaboratoryDao, participantDao, initialDate, finalDate, fieldCenterAcronym);
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
}
