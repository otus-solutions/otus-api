package br.org.otus.laboratory.configuration;

import static com.mongodb.client.model.Filters.exists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.configuration.lot.receipt.LotReceiptCustomMetadata;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.google.gson.GsonBuilder;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.aliquot.AliquotConfigurationQueryBuilder;
import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;

public class LaboratoryConfigurationDaoBean extends MongoGenericDao<Document> implements LaboratoryConfigurationDao {

  private static final String COLLECTION_NAME = "laboratory_configuration";
  private static final String LABORATORY_CONFIGURATION_OBJECT_TYPE = "LaboratoryConfiguration";
  private static final String TRANSPORTATION = "transportation_lot";
  private static final String EXAM = "exam_lot";
  private static final Integer DEFAULT_CODE = 300000000;

  public LaboratoryConfigurationDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public LaboratoryConfiguration find() throws DataNotFoundException {
    Document result = collection.find(new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE)).first();
    if(result == null){
      throw new DataNotFoundException(LABORATORY_CONFIGURATION_OBJECT_TYPE + " was not found.");
    }
    return LaboratoryConfiguration.deserialize(result.toJson());
  }

  @Override
  public Boolean getCheckingExist() {
    Document result = collection.find(new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE)).first();
    return (result != null);
  }

  @Override
  public AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException {
    Document first = collection.find(new Document(OBJECT_TYPE_PATH, "AliquotExamCorrelation")).first();
    if (first == null) {
      throw new DataNotFoundException(new Throwable("Aliquot exam correlation document not found."));
    }
    return AliquotExamCorrelation.deserialize(first.toJson());
  }

  public List<String>  getExamName(List<String> centerAliquots) {
    List<String> exams = null;

    ArrayList<Bson> pipeline = new ArrayList<Bson>();
    pipeline.add(parseQuery("{$match:{objectType:\"AliquotExamCorrelation\"}}"));
    pipeline.add(parseQuery("{$unwind:\"$aliquots\"}"));
    pipeline.add(new Document("$match", new Document("aliquots.name", new Document("$in", centerAliquots))));
    pipeline.add(parseQuery("{$unwind:\"$aliquots.exams\"}"));
    pipeline.add(parseQuery("{$group:{_id:\"$aliquots.exams\"}}"));
    pipeline.add(parseQuery("{$sort:{\"_id\":1}}"));
    pipeline.add(parseQuery("{$group:{_id:{},exams:{$push:\"$_id\"}}}"));

    Document resultsDocument = collection.aggregate(pipeline).first();

    if (resultsDocument != null) {
      exams = (ArrayList<String>) resultsDocument.get("exams");
    }

    return exams;
  }

  @Override
  public List<String> getAliquotsExams(List<String> aliquots) {
    List<String> exams = null;

    ArrayList<Bson> pipeline = new ArrayList<Bson>();
    pipeline.add(parseQuery("{$match:{objectType:\"AliquotExamCorrelation\"}}"));
    pipeline.add(parseQuery("{$unwind:\"$aliquots\"}"));
    pipeline.add(new Document("$match", new Document("aliquots.name", new Document("$in", aliquots))));
    pipeline.add(parseQuery("{$unwind:\"$aliquots.exams\"}"));
    pipeline.add(parseQuery("{$group:{_id:{},exams:{$addToSet:\"$aliquots.exams\"}}}"));

    Document resultsDocument = collection.aggregate(pipeline).first();

    if (resultsDocument != null) {
      exams = (ArrayList<String>) resultsDocument.get("exams");
    }

    return exams;
  }

  private Bson parseQuery(String stage) {
    return new GsonBuilder().create().fromJson(stage, Document.class);
  }

  public void persist(LaboratoryConfiguration laboratoryConfiguration) {
    super.persist(LaboratoryConfiguration.serialize(laboratoryConfiguration));
  }


  @Override
  public String createNewLotCodeForTransportation(Integer code) {
    if (getLastInsertion(TRANSPORTATION) < code) {
      restoreLotConfiguration(TRANSPORTATION, code);
    }

    Document updateLotCode = collection.findOneAndUpdate(exists("lotConfiguration.lastInsertionTransportation"),
      new Document("$inc", new Document("lotConfiguration.lastInsertionTransportation", 1)),
      new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));

    LaboratoryConfiguration laboratoryConfiguration = LaboratoryConfiguration.deserialize(updateLotCode.toJson());

    return laboratoryConfiguration.getLotConfiguration().getLastInsertionTransportation().toString();
  }

  @Override
  public String createNewLotCodeForExam(Integer code) {
    if (getLastInsertion(EXAM) < code) {
      restoreLotConfiguration(EXAM, code);
    }

    Document updateLotCode = collection.findOneAndUpdate(exists("lotConfiguration.lastInsertionExam"),
      new Document("$inc", new Document("lotConfiguration.lastInsertionExam", 1)),
      new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));

    LaboratoryConfiguration laboratoryConfiguration = LaboratoryConfiguration.deserialize(updateLotCode.toJson());

    return laboratoryConfiguration.getLotConfiguration().getLastInsertionExam().toString();
  }

  @Override
  public Integer getLastInsertion(String lot) {
    Document output = collection.aggregate(Arrays.asList(
      Aggregates.match(new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE)),
      Aggregates.project(new Document("lotConfiguration.lastInsertionTransportation",
        new Document("$ifNull", Arrays.asList("$lotConfiguration.lastInsertionTransportation", 0)))
        .append("lotConfiguration.lastInsertionExam",
          new Document("$ifNull", Arrays.asList("$lotConfiguration.lastInsertionExam", 0)))))).first();

    LaboratoryConfiguration laboratoryConfiguration;
    laboratoryConfiguration = LaboratoryConfiguration.deserialize(output.toJson());
    switch (lot) {
      case TRANSPORTATION:
        return laboratoryConfiguration.getLotConfiguration().getLastInsertionTransportation();
      case EXAM:
        return laboratoryConfiguration.getLotConfiguration().getLastInsertionExam();
      default:
        return DEFAULT_CODE;
    }

  }

  @Override
  public void restoreLotConfiguration(String config, Integer code) {
    switch (config) {
      case TRANSPORTATION:
        collection.updateOne(new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE), new Document("$set", new Document("lotConfiguration.lastInsertionTransportation", code)));
        break;
      case EXAM:
        collection.updateOne(new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE), new Document("$set", new Document("lotConfiguration.lastInsertionExam", code)));
        break;
    }
  }

  @Override
  public Integer updateLastTubeInsertion(int quantity) {
    Document query = new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE)
      .append("codeConfiguration.lastInsertion", new Document("$exists", true));

    Document updateLotCode = collection.findOneAndUpdate(query, new Document("$inc", new Document("codeConfiguration.lastInsertion", quantity)),
      new FindOneAndUpdateOptions().returnDocument(ReturnDocument.BEFORE));
    return ((Document) updateLotCode.get("codeConfiguration")).getInteger("lastInsertion");
  }

  @Override
  public Integer updateUnattachedLaboratoryLastInsertion() {
    Document query = new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE)
      .append("codeConfiguration.unattachedLaboratoryLastInsertion", new Document("$exists", true));

    Document updateLotCode = collection.findOneAndUpdate(query, new Document("$inc", new Document("codeConfiguration.unattachedLaboratoryLastInsertion", 1)),
      new FindOneAndUpdateOptions().returnDocument(ReturnDocument.BEFORE));
    return ((Document) updateLotCode.get("codeConfiguration")).getInteger("unattachedLaboratoryLastInsertion");
  }

  @Override
  public ArrayList listCenterAliquots(String center) throws DataNotFoundException {
    ArrayList<Bson> pipeline = new AliquotConfigurationQueryBuilder().getCenterAliquotsQuery(center);
    Document first = collection.aggregate(pipeline).first();
    ArrayList centerAliquots = first.get("centerAliquots", ArrayList.class);

    if (centerAliquots == null || centerAliquots.size() == 0) {
      throw new DataNotFoundException("Any exams available found for the given center: \"" + center + "\"");
    }
    return centerAliquots;
  }

  @Override
  public List<TubeCustomMetadata> getTubeCustomMedataData(String tubeType) throws DataNotFoundException {

    Document query = new Document(OBJECT_TYPE_PATH, TubeCustomMetadata.OBJECT_TYPE);
    query.put("type", tubeType);

    FindIterable<Document> results = collection.find(query);
    if(results == null){
      throw new DataNotFoundException("Tube custom metadata of type " + tubeType + "was not found.");
    }

    List<TubeCustomMetadata> tubeCustomMetadata = new ArrayList<>();

    MongoCursor<Document> iterator = results.iterator();
    while(iterator.hasNext()){
      tubeCustomMetadata.add(TubeCustomMetadata.deserialize(iterator.next().toJson()));
    }

    return tubeCustomMetadata;
  }

  @Override
  public List<TubeCustomMetadata> getTubeCustomMedataData() {

    Document query = new Document(OBJECT_TYPE_PATH, TubeCustomMetadata.OBJECT_TYPE);

    FindIterable<Document> results = collection.find(query);

    List<TubeCustomMetadata> tubeCustomMetadata = new ArrayList<>();

    MongoCursor<Document> iterator = results.iterator();
    while(iterator.hasNext()){
      tubeCustomMetadata.add(TubeCustomMetadata.deserialize(iterator.next().toJson()));
    }

    return tubeCustomMetadata;
  }

  @Override
  public List<LotReceiptCustomMetadata> getLotReceiptCustomMetadata() throws DataNotFoundException {

    Document query = new Document(OBJECT_TYPE_PATH, LotReceiptCustomMetadata.OBJECT_TYPE);

    FindIterable<Document> results = collection.find(query);
    if(results == null){
      throw new DataNotFoundException("No lot receipt custom metadata was found.");
    }

    List<LotReceiptCustomMetadata> lotReceiptCustomMetadata = new ArrayList<>();

    MongoCursor<Document> iterator = results.iterator();
    while(iterator.hasNext()){
      lotReceiptCustomMetadata.add(LotReceiptCustomMetadata.deserialize(iterator.next().toJson()));
    }

    return lotReceiptCustomMetadata;
  }
}
