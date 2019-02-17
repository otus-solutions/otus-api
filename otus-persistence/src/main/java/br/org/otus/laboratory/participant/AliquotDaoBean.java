package br.org.otus.laboratory.participant;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationAliquotFiltersDTO;
import com.mongodb.Block;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class AliquotDaoBean extends MongoGenericDao<Document> implements AliquotDao {
  private static final String COLLECTION_NAME = "aliquot";

  private static final String EXAM = "EXAM";
  private static final String STORAGE = "STORAGE";

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
  public List<Aliquot> getExamLotAliquots(ObjectId lotOId) {
    FindIterable<Document> documents = collection.find(new Document("$or", Arrays.asList(new Document("examLotData.id",lotOId),new Document("examLotId",lotOId)))).sort(new Document("examLotData.position",1));

    ArrayList<Aliquot> aliquotList = new ArrayList<>();
    for (Object oneDocument : documents) {
      Document next = (Document) oneDocument;
      aliquotList.add(Aliquot.deserialize(next.toJson()));
    }

    return aliquotList;
  }

  @Override
  public void updateExamLotId(ArrayList<String> codeList, ObjectId lotId) throws DataNotFoundException {
    collection.updateMany(new Document("$or", Arrays.asList(new Document("examLotData.id",lotId),new Document("examLotId",lotId))), new Document("$set", new Document("examLotId", null).append("examLotData",null)));
    if (!codeList.isEmpty()) {
      codeList.forEach(aliquotCode -> collection.updateOne(new Document("code",aliquotCode), new Document("$set", new Document("examLotData", new Document("id", lotId).append("position",codeList.indexOf(aliquotCode))))));
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

    ArrayList<String> roles = new ArrayList<>();
    roles.add(EXAM);

    if (transportationAliquotFiltersDTO.getRole().equals(STORAGE)){
      roles.add(transportationAliquotFiltersDTO.getRole());
    }

    FindIterable<Document> result = collection.find(
      and (
        gte("aliquotCollectionData.processing", transportationAliquotFiltersDTO.getInitialDate()),
        lte("aliquotCollectionData.processing", transportationAliquotFiltersDTO.getFinalDate()),
        eq("fieldCenter.acronym", transportationAliquotFiltersDTO.getFieldCenter()),
        eq("transportationLotId", null),
        in("role", roles)
        )
    );

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
