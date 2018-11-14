package br.org.otus.laboratory.participant;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class AliquotDaoBean extends MongoGenericDao<Document> implements AliquotDao {
  private static final String COLLECTION_NAME = "aliquot";

  public AliquotDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public List<Aliquot> getAliquots() {
    List<Aliquot> aliquots = new ArrayList<>();

    FindIterable<Document> result = collection.find();
    result.forEach((Block<Document>) document -> aliquots.add(Aliquot.deserialize(document.toJson())));

    return aliquots;
  }
  
  @Override
  public void persist(Aliquot aliquot) {
    super.persist(Aliquot.serialize(aliquot));
  }

  @Override
  public void create(Aliquot aliquot) {
    Document parsed = Document.parse(Aliquot.serialize(aliquot));
    collection.insertOne(parsed);
  }

  @Override
  public void create(List<Aliquot> aliquotList) {
    ArrayList<Document> documents = new ArrayList<>();

    aliquotList.forEach(aliquot -> {
      documents.add(Document.parse(Aliquot.serialize(aliquot)));
    });

    collection.insertMany(documents);
  }

  @Override
  public List<Aliquot> list(Long recruitmentNumber) {
    MongoCursor<Document> cursor = collection.find(eq("recruitmentNumber", recruitmentNumber)).iterator();
    ArrayList<Aliquot> aliquots = new ArrayList<>();

    try {
      while (cursor.hasNext()) {
        aliquots.add(Aliquot.deserialize(cursor.next().toJson()));
      }
    } finally {
      cursor.close();
    }

    return aliquots;
  }

  @Override
  public boolean exists(String code) {
    Document first = collection.find(eq("code", code)).first();
    if (first != null) {
      return true;
    }
    return false;

  }

    @Override
    public void delete(String code) throws DataNotFoundException {
        DeleteResult deleteResult = collection.deleteOne(new Document("code", code));

        if(deleteResult.getDeletedCount() == 0){
            throw new DataNotFoundException(new Throwable("Aliquot code not found."), code);
        }
    }

    @Override
    public Aliquot find(String code) throws DataNotFoundException {
        Document result = collection.find(eq("code", code)).first();
        if(result == null ){
            throw new DataNotFoundException(new Throwable("Aliquot not found"));
        }
        return Aliquot.deserialize(result.toJson());
    }

    @Override
    public void updateExamLotId(ArrayList<String> codeList, ObjectId loId) throws DataNotFoundException {
      Document query = new Document("code", new Document("$in", codeList));

      collection.updateMany(new Document("examLotId", loId), new Document("$set", new Document("examLotId", null)));
      if (!codeList.isEmpty()) {
          UpdateResult updateManyResult = collection.updateMany(query, new Document("$set", new Document("examLotId", loId)));
          if (updateManyResult.getMatchedCount() == 0) {
              throw new DataNotFoundException(new Throwable("aliquots not found"));
          }
      }
  }

    @Override
    public Aliquot getAliquot(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) throws DataNotFoundException {
        Document document = collection.find(eq("code", transportationAliquotFiltersDTO.getCode())).first();
        if (document == null) {
            throw new DataNotFoundException(
                    new Throwable("Aliquot not found."));
        }
        Aliquot aliquot = Aliquot.deserialize(document.toJson());
        return aliquot;
    }

    @Override
    public List<Aliquot> getAliquotsByPeriod(TransportationAliquotFiltersDTO transportationAliquotFiltersDTO) {
        List<Aliquot> aliquots = new ArrayList<>();

        FindIterable<Document> result = collection.find(
                and (
                  gte("aliquotCollectionData.processing", transportationAliquotFiltersDTO.getInitialDate()),
                  lte("aliquotCollectionData.processing", transportationAliquotFiltersDTO.getFinalDate()),
                  eq("fieldCenter.acronym", transportationAliquotFiltersDTO.getFieldCenter()),
                  eq("transportationLotId", null)
                ));

        result.forEach((Block<Document>) document -> aliquots.add(Aliquot.deserialize(document.toJson())));
        return aliquots;
    }

    @Override
    public void addToTransportationLot(ArrayList<String> codeList, ObjectId transportationLotId) throws DataNotFoundException {
        Document query = new Document("code",new Document("$in", codeList));
        UpdateResult updateManyResult = collection.updateMany(query,new Document("$set", new Document("transportationLotId", transportationLotId)));

        if (updateManyResult.getMatchedCount() == 0) {
            throw new DataNotFoundException(new Throwable("Aliquot not found"));
        }
    }

    @Override
    public void updateTransportationLotId(ArrayList<String> codeList, ObjectId loId) throws DataNotFoundException {
        Document query = new Document("code", new Document("$in", codeList));

        collection.updateMany(new Document("transportationLotId", loId), new Document("$set", new Document("transportationLotId", null)));
        if (!codeList.isEmpty()) {
            UpdateResult updateManyResult = collection.updateMany(query, new Document("$set", new Document("transportationLotId", loId)));
            if (updateManyResult.getMatchedCount() != codeList.size()) {
                throw new DataNotFoundException(new Throwable("aliquots not found"));
            }
        }
    }
}
