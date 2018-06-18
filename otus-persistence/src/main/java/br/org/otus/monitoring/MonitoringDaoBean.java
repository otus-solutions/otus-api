package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.persistence.MonitoringDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;

@Stateless
public class MonitoringDaoBean extends MongoGenericDao<Document> implements MonitoringDao {

    public static final String COLLECTION_NAME = "activity";
    public static final String RN_PATH = "$participantData.recruitmentNumber";
    public static final String ACRONYM_PATH = "$surveyForm.surveyTemplate.identity.acronym";
    public static final String ACRONYM = "acronym";
    public static final String PROJECT = "$project";
    public static final String MONTH = "$month";
    public static final String MATCH = "$match";
    public static final String RN = "rn";
    public static final String UNWIND = "$unwind";
    public static final String FIELD_CENTER_PATH = "$fieldCenter.fieldCenter.acronym";


    public static final String DISCARDED_PATH = "isDiscarded";

    public MonitoringDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public List<MonitoringDataSourceResult> getAll() throws ValidationException {

        ArrayList<MonitoringDataSourceResult> monitoringData = new ArrayList<>();

        ArrayList<Document> query = new ArrayList<>();
        Document project = new Document(
                PROJECT,
                new Document(ACRONYM, ACRONYM_PATH)
                        .append("statusHistory", new Document("$slice", Arrays.asList("$statusHistory",-1) ))
                        .append(RN, RN_PATH)
                        .append(DISCARDED_PATH, 1)
        );
        query.add(project);

        Document project2 = new Document(
                PROJECT,
                new Document(ACRONYM, 1)
                        .append("status.date", "$statusHistory.date")
                        .append("status.name", "$statusHistory.name")
                        .append(RN,1)
                        .append(DISCARDED_PATH, 1)
        );
        query.add(project2);

        Document unwind = new Document(UNWIND,"$status.date");
        query.add(unwind);

        Document unwind2 = new Document(UNWIND,"$status.name");
        query.add(unwind2);

        Document project3 = new Document(
                PROJECT,
                new Document(ACRONYM, 1)
                        .append("status.month", new Document("$substr",Arrays.asList("$status.date",5,2)))
                        .append("status.year", new Document("$substr",Arrays.asList("$status.date",0,4)))
                        .append("status.name",1)
                        .append(RN,1)
                        .append(DISCARDED_PATH, 1)
                        .append("activityDate","$status.date")
                        .append("_id", 0)
        );
        query.add(project3);

        Document match = new Document(
                MATCH,
                new Document(DISCARDED_PATH, false)
                        .append("status.name", "FINALIZED")
        );
        query.add(match);

        Document lookup = new Document(
                "$lookup",
                new Document("from", "participant")
                        .append("localField", "rn")
                        .append("foreignField", "recruitmentNumber")
                        .append("as", "fieldCenter")
        );
        query.add(lookup);

        Document project4 = new Document(
                PROJECT,
                new Document(DISCARDED_PATH, 1)
                        .append(ACRONYM, 1)
                        .append(RN, 1)
                        .append("status", 1)
                        .append("fieldCenter", FIELD_CENTER_PATH)
                        .append("activityDate", 1)
        );
        query.add(project4);

        Document unwind3 = new Document(UNWIND,"$fieldCenter");
        query.add(unwind3);

        Document group = new Document(
                "$group",
                new Document("_id", new Document("fieldCenter", "$fieldCenter")
                        .append(ACRONYM, "$acronym")
                        .append("month", "$status.month")
                        .append("year", "$status.year"))
                        .append("sum", new Document("$sum", 1))
                        .append("firstActivityDate", new Document("$min", "$activityDate"))
        );
        query.add(group);

        Document project5 = new Document(
                PROJECT,
                new Document("fieldCenter", "$_id.fieldCenter")
                        .append("month", "$_id.month")
                        .append("year", "$_id.year")
                        .append(ACRONYM, "$_id.acronym")
                        .append("sum", "$sum")
                        .append("firstActivityDate", 1)
                        .append("_id", 0)
        );
        query.add(project5);

        Document sort = new Document(
                "$sort",
                new Document("fieldCenter", 1)
                        .append(ACRONYM, 1)
                        .append("year", 1)
                        .append("month", 1)
                        .append("sum", 1)
        );
        query.add(sort);

        AggregateIterable output = collection.aggregate(query);

        for (Object anOutput : output) {
            Document result = (Document) anOutput;
            monitoringData.add(MonitoringDataSourceResult.deserialize(result.toJson()));
        }

        return monitoringData;
    }

    @Override
    public MonitoringDataSourceResult get(String acronym) throws ValidationException {

        MonitoringDataSourceResult monitoringData = null;

        ArrayList<Document> query = new ArrayList<>();
        Document match0 = new Document(
                MATCH,
                new Document(DISCARDED_PATH, false)
                .append("surveyForm.surveyTemplate.identity.acronym", acronym)
        );
        query.add(match0);

        Document project = new Document(
                PROJECT,
                new Document(ACRONYM, ACRONYM_PATH)
                        .append("statusHistory", new Document("$slice", Arrays.asList("$statusHistory",-1) ))
                        .append(RN, RN_PATH)
                        .append(DISCARDED_PATH, 1)
        );
        query.add(project);

        Document project2 = new Document(
                PROJECT,
                new Document(ACRONYM, 1)
                        .append("status.date", "$statusHistory.date")
                        .append("status.name", "$statusHistory.name")
                        .append(RN,1)
                        .append(DISCARDED_PATH, 1)
        );
        query.add(project2);

        Document unwind = new Document(UNWIND,"$status.date");
        query.add(unwind);

        Document unwind2 = new Document(UNWIND,"$status.name");
        query.add(unwind2);

        Document project3 = new Document(
                PROJECT,
                new Document(ACRONYM, 1)
                        .append("status.month", new Document("$substr",Arrays.asList("$status.date",5,2)))
                        .append("status.year", new Document("$substr",Arrays.asList("$status.date",0,4)))
                        .append("status.name",1)
                        .append(RN,1)
                        .append(DISCARDED_PATH, 1)
                        .append("activityDate","$status.date")
                        .append("_id", 0)
        );
        query.add(project3);

        Document match = new Document(
                MATCH,
                new Document("status.name", "FINALIZED")
        );
        query.add(match);

        Document lookup = new Document(
                "$lookup",
                new Document("from", "participant")
                        .append("localField", "rn")
                        .append("foreignField", "recruitmentNumber")
                        .append("as", "fieldCenter")
        );
        query.add(lookup);

        Document project4 = new Document(
                PROJECT,
                new Document(DISCARDED_PATH, 1)
                        .append(ACRONYM, 1)
                        .append(RN, 1)
                        .append("status", 1)
                        .append("fieldCenter", FIELD_CENTER_PATH)
                        .append("activityDate", 1)
        );
        query.add(project4);

        Document unwind3 = new Document(UNWIND,"$fieldCenter");
        query.add(unwind3);

        Document group = new Document(
                "$group",
                new Document("_id", new Document("fieldCenter", "$fieldCenter")
                        .append(ACRONYM, "$acronym")
                        .append("month", "$status.month")
                        .append("year", "$status.year"))
                        .append("sum", new Document("$sum", 1))
                        .append("firstActivityDate", new Document("$min", "$activityDate"))
        );
        query.add(group);

        Document project5 = new Document(
                PROJECT,
                new Document("fieldCenter", "$_id.fieldCenter")
                        .append("month", "$_id.month")
                        .append("year", "$_id.year")
                        .append(ACRONYM, "$_id.acronym")
                        .append("sum", "$sum")
                        .append("firstActivityDate", 1)
                        .append("_id", 0)
        );
        query.add(project5);

        Document sort = new Document(
                "$sort",
                new Document("fieldCenter", 1)
                        .append(ACRONYM, 1)
                        .append("year", 1)
                        .append("month", 1)
                        .append("sum", 1)
        );
        query.add(sort);

        AggregateIterable output = collection.aggregate(query);

        for (Object anOutput : output) {
            Document result = (Document) anOutput;
            monitoringData = MonitoringDataSourceResult.deserialize(result.toJson());
        }

        return monitoringData;
    }

}
