package br.org.otus.monitoring;

  import br.org.mongodb.MongoGenericDao;
  import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
  import br.org.otus.monitoring.builder.LaboratoryProgressQueryBuilder;
  import br.org.otus.monitoring.builder.LaboratoryStatusQueryBuilder;
  import org.bson.Document;
  import org.bson.conversions.Bson;
  import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
  import org.ccem.otus.persistence.FlagReportDao;
  import org.jetbrains.annotations.NotNull;

  import java.util.LinkedList;
  import java.util.List;

public class ExamFlagReportDaoBean extends MongoGenericDao<Document> {

  public static final String COLLECTION_NAME = "exam_results";

  public ExamFlagReportDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

//  @Override
  public Document getActivitiesProgressReport(LinkedList<String> recruitmentNumbers) throws DataNotFoundException {
    String center = "center";
    List<Bson> query = new LaboratoryStatusQueryBuilder().getExamResultsStatusQuery(center, recruitmentNumbers);

    return getDocument(query);
  }

  @NotNull
  private Document getDocument(List<Bson> query) throws DataNotFoundException {
    Document result = collection.aggregate(query).allowDiskUse(true).first();

    if (result == null) {
      throw new DataNotFoundException("There are no results");
    }

    return result;
  }

}
