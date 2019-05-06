package br.org.otus.laboratory.configuration;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.aliquot.AliquotConfigurationQueryBuilder;
import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import com.google.gson.GsonBuilder;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.exists;

public class LaboratoryConfigurationDaoBean extends MongoGenericDao<Document> implements LaboratoryConfigurationDao {

  private static final String COLLECTION_NAME = "laboratory_configuration";
  private static final String TRANSPORTATION = "transportation_lot";
  private static final String EXAM = "exam_lot";
  private static final Integer DEFAULT_CODE = 300000000;

  public LaboratoryConfigurationDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public LaboratoryConfiguration find() {
    Document query = new Document("objectType", "LaboratoryConfiguration");

    Document first = collection.find(query).first();

    return LaboratoryConfiguration.deserialize(first.toJson());
  }

  @Override
  public AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException {
    Document query = new Document("objectType", "AliquotExamCorrelation");

    Document first = collection.find(query).first();

    if (first == null) {
      throw new DataNotFoundException(new Throwable("Aliquot exam correlation document not found."));
    }

    return AliquotExamCorrelation.deserialize(first.toJson());
  }

  @Override
  public List<String> getAliquotsExams(List<String> aliquots) {
      List<String> exams = null;

      ArrayList<Bson> pipeline = new ArrayList<Bson>();
      pipeline.add(parseQuery("{$match:{objectType:\"AliquotExamCorrelation\"}}"));
      pipeline.add(parseQuery("{$unwind:\"$aliquots\"}"));
      pipeline.add(new Document("$match",new Document("aliquots.name",new Document("$in",aliquots))));
      pipeline.add(parseQuery("{$unwind:\"$aliquots.exams\"}"));
      pipeline.add(parseQuery("{$group:{_id:{},exams:{$addToSet:\"$aliquots.exams\"}}}"));

      Document resultsDocument = collection.aggregate(pipeline).first();

      if(resultsDocument != null) {
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
                Aggregates.match(new Document("objectType", "LaboratoryConfiguration")),
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
                collection.updateOne(new Document("objectType", "LaboratoryConfiguration"), new Document("$set", new Document("lotConfiguration.lastInsertionTransportation", code)));
                break;
            case EXAM:
                collection.updateOne(new Document("objectType", "LaboratoryConfiguration"), new Document("$set", new Document("lotConfiguration.lastInsertionExam", code)));
                break;
        }
    }

  @Override
  public Integer updateLastTubeInsertion(int quantity) {
    Document query = new Document("objectType", "LaboratoryConfiguration")
      .append("codeConfiguration.lastInsertion", new Document("$exists", true));

    Document updateLotCode = collection.findOneAndUpdate(query, new Document("$inc", new Document("codeConfiguration.lastInsertion", quantity)),
        new FindOneAndUpdateOptions().returnDocument(ReturnDocument.BEFORE));
    return ((Document) updateLotCode.get("codeConfiguration")).getInteger("lastInsertion");
  }

    @Override
    public ArrayList listCenterAliquots(String center) throws DataNotFoundException {
        ArrayList<Bson> pipeline = new AliquotConfigurationQueryBuilder().getCenterAliquotsQuery(center);
        Document first = collection.aggregate(pipeline).first();
        ArrayList centerAliquots = first.get("centerAliquots", ArrayList.class);

        if(centerAliquots == null || centerAliquots.size() == 0) {
            throw new DataNotFoundException("Any exams available found for the given center: \"" + center + "\"");
        }
        return centerAliquots;
    }
}
