package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.monitoring.ParticipantActivityReportDto;
import org.ccem.otus.persistence.SurveyMonitoringDao;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class SurveyMonitoringDaoBean extends MongoGenericDao<Document> implements SurveyMonitoringDao {


  private static final String COLLECTION_NAME = "survey";

  public SurveyMonitoringDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }


  @Override
  public ArrayList<ParticipantActivityReportDto> getParticipantActivities(Long rn) {
    List<Bson> aggregation = Arrays.asList(
      match(new Document("isDiscarded", false)),
      lookupAct(rn),
      lookupNotApply(rn),
      project(
        new Document("acronym", "$surveyTemplate.identity.acronym")
          .append("name", "$surveyTemplate.identity.name")
          .append("doesNotApply", new Document("$arrayElemAt", Arrays.asList("$doesNotApply", 0)))
          .append("activities", 1)
      )
    );

    MongoCursor<Document> iterator = collection.aggregate(aggregation).iterator();

    ArrayList<ParticipantActivityReportDto> dtos = new ArrayList<>();

    try {
      while (iterator.hasNext()) {
        dtos.add(ParticipantActivityReportDto.deserialize(iterator.next().toJson()));
      }
    } finally {
      iterator.close();
    }

    return dtos;

  }

  private Document deserializeDocument(String json) {
    return getGsonBuilder().fromJson(json, Document.class);
  }

  private ArrayList deserializeArray(String json) {
    return getGsonBuilder().fromJson(json, ArrayList.class);
  }


  private Gson getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder.create();
  }

  private Bson match(Document query) {
    return Aggregates.match(query);
  }

  private Bson project(Document projection) {
    return Aggregates.project(projection);
  }

  private Bson lookupAct(Long rn) {
    return new Document("$lookup", new Document("from", "activity")
      .append("let", new Document("acronym", "$surveyTemplate.identity.acronym"))
      .append("pipeline", lookupPipeline(rn))
      .append("as", "activities")
    );
  }

  private List lookupPipeline(Long rn) {

    Document matchExpr = new Document(
      "$expr", new Document(
      "$and", Arrays.asList(
      new Document("$eq", Arrays.asList("$participantData.recruitmentNumber", rn)),
      new Document("$eq", Arrays.asList("$surveyForm.surveyTemplate.identity.acronym", "$$acronym"))
    )
    )
    );

    Document projectExpr = new Document("statusHistory", new Document("$slice", Arrays.asList("$statusHistory", -1)));


    return Arrays.asList(
      new Document("$match", matchExpr),
      new Document("$project", projectExpr)
    );
  }

  private Bson lookupNotApply(Long rn) {
    return new Document("$lookup", new Document("from", "activity_inapplicability")
      .append("let", new Document("acronym", "$surveyTemplate.identity.acronym"))
      .append("pipeline", lookupPipelineNotApply(rn))
      .append("as", "doesNotApply")
    );
  }

  private List lookupPipelineNotApply(Long rn) {
    Document matchExpr = new Document(
      "$expr", new Document(
      "$and", Arrays.asList(
      new Document("$eq", Arrays.asList("$recruitmentNumber", rn)),
      new Document("$eq", Arrays.asList("$acronym", "$$acronym"))
    )
    )
    );

    Document projectExpr = new Document("statusHistory", new Document("$slice", Arrays.asList("$statusHistory", -1)));

    return Arrays.asList(
      new Document("$match", matchExpr),
      new Document("$project", projectExpr)
    );
  }
}
