package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.monitoring.ParticipantExamReportDto;
import org.ccem.otus.persistence.ExamMonitoringDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class ExamMonitoringDaoBean extends MongoGenericDao<Document> implements ExamMonitoringDao {

    private static final String COLLECTION_NAME = "exam_result";

    public ExamMonitoringDaoBean() {
        super(COLLECTION_NAME, Document.class);}

    @Override
    public ArrayList<ParticipantExamReportDto> getParticipantExams(Long rn) {

        List<Bson> aggregation = Arrays.asList(
                aggregateMatch(new Document("recruitmentNumber", rn)),
                groupExams(),
//                aggregateAddFields(new Document("allExams",Variavel)),
                Aggregates.unwind("$allExams"),
                aggregateAddFields(new Document("$addFields",new Document("$arrayElemAt",
                        Arrays.asList(new Document("$filter",new Document("input","$exams")
                                .append("as","exam")
                                .append("cond",new Document("$eq",Arrays.asList("$$exam.name","$allExams")))),0)
                ))),
                aggregateProjection(new Document("name","$allExams")
                        .append("quantity",new Document("$cond",Arrays.asList(new Document("$ifNull",Arrays.asList("$examFound","")))))),
                lookupInapplicability(),
                aggregateProjection(
                        new Document("name", "$_id")
                                .append("_id", "0")
                                .append("quantity", 1)
                                .append("doesNotApply", new Document("$arrayElemAt", Arrays.asList("$notApply", 0)))
                )
        );

        MongoCursor<Document> iterator = collection.aggregate(aggregation).iterator();

        ArrayList<ParticipantExamReportDto> dtos = new ArrayList<>();

        try {
            while (iterator.hasNext()) {
                dtos.add(ParticipantExamReportDto.deserialize(iterator.next().toJson()));
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

    private Bson aggregateAddFields(Document addFields) {
        return Aggregates.addFields((List<Field<?>>) addFields);
    }

    private Bson groupExams() {

        ArrayList<Bson> group = new ArrayList<>();

        group.add(new Document("$group", new Document("_id", new Document("examName", "$examName"))
                .append("examId", "$examId")));

        group.add(new Document("$group", new Document("_id", new Document("examId", "$examId"))));
        group.add(new Document("$group", new Document("_id", new Document("examName", "$_id.examName"))));

        return (Bson) group;
    }

    private Bson lookupInapplicability() {
        return new Document("$lookup", new Document("from", "exam_inapplicability")
                .append("localField", "_id")
                .append("foreignField", "name")
                .append("as", "notApply")
        );
    }
}

