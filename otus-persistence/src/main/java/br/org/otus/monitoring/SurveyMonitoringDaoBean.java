package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.survey.activity.ActivityInapplicabilityDaoBean;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.monitoring.ParticipantActivityReportDto;
import org.ccem.otus.persistence.SurveyMonitoringDao;

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
      aggregateMatch(new Document("isDiscarded", false)),
      lookupActivities(rn),
      lookupInapplicability(rn),
      aggregateProjection(
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

  private Bson aggregateMatch(Document query) {
    return Aggregates.match(query);
  }

  private Bson aggregateProjection(Document projection) {
    return Aggregates.project(projection);
  }

  private Bson lookupActivities(Long rn) {
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
      new Document("$eq", Arrays.asList("isDiscarded", false)),
      new Document("$eq", Arrays.asList("$surveyForm.surveyTemplate.identity.acronym", "$$acronym"))
    )
    )
    );

    Document projectExpr = new Document("statusHistory", new Document("$arrayElemAt", Arrays.asList("$statusHistory", -1)));


    return Arrays.asList(
      new Document("$match", matchExpr),
      new Document("$project", projectExpr)
    );
  }

  private Bson lookupInapplicability(Long rn) {
    return new Document("$lookup", new Document("from", ActivityInapplicabilityDaoBean.COLLECTION_NAME)
      .append("let", new Document("acronym", "$surveyTemplate.identity.acronym"))
      .append("pipeline", lookupPipelineInapplicability(rn))
      .append("as", "doesNotApply")
    );
  }

  private List lookupPipelineInapplicability(Long rn) {
    Document matchExpr = new Document(
      "$expr", new Document(
      "$and", Arrays.asList(
      new Document("$eq", Arrays.asList("$recruitmentNumber", rn)),
      new Document("$eq", Arrays.asList("$acronym", "$$acronym"))
    )
    )
    );

    return Arrays.asList(
      new Document("$match", matchExpr)
    );
  }
}
