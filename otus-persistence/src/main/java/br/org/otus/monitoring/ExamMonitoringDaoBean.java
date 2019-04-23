package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.monitoring.ParticipantExamReportDto;
import org.ccem.otus.persistence.ExamMonitoringDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class ExamMonitoringDaoBean extends MongoGenericDao<Document> implements ExamMonitoringDao {

    @Inject
    private LaboratoryConfigurationDao laboratoryConfigurationDao;

    private static final String COLLECTION_NAME = "exam_result";

    public ExamMonitoringDaoBean() {
        super(COLLECTION_NAME, Document.class);}

    @Override
    public ArrayList<ParticipantExamReportDto> getParticipantExams(Long rn) {
        List<String> exams = laboratoryConfigurationDao.getAggregateExams();

        ArrayList<Bson> pipeline = new ArrayList<Bson>();
        pipeline.add(parseQuery("{$match:{\"recruitmentNumber\":"+rn+"}}"));
        pipeline.add(parseQuery("{$group:{_id:{\"examName\":\"$examName\"}}}"));
        pipeline.add(parseQuery("{$group:{_id:{\"examId\":\"$examId\"}}}"));
        pipeline.add(parseQuery("{$group:{_id:{\"examName\":\"$_id.examName\"}}}"));
        pipeline.add(parseQuery("{$addFields:{\"allExams\":"+exams+"}}"));
        pipeline.add(parseQuery("{$unwind:\"$allExams\"}"));
        pipeline.add(parseQuery("{$addFields:{examFound:{$arrayElemAt:[$filter:{" +
                "input:\"$exams\",as:\"exam\"," +
                "cond:{" +
                "$eq:[\"$$exam.name\",\"$allExams\"]" +
                "}},0]}}}"));
        pipeline.add(parseQuery("{$project:{name:\"$allExams\"," +
                "quantity:{$cond:[{$ifNull:[\"$examFound\",false]},\"$examFound.quantity\",0]}" +
                "}}"));
        pipeline.add(parseQuery("{$lookup:{from:\"exam_inapplicability\"," +
                "let:{name:\"$name\",rn:\"$recruitmentNumber\"}," +
                "pipeline:[{$match:{$expr:{ $and:[{$eq:[\"$name\",\"$$name\"]}," +
                "{$eq:[\"$recruitmentNumber\","+rn+"]}]}}}," +
                "{$project:{\"_id\":0,\"name\":0,\"recruitmentNumber\":0}}],as:\"examInapplicability\"}}"));

        MongoCursor<Document> resultsDocument = super.aggregate(pipeline).iterator();

        ArrayList<ParticipantExamReportDto> dtos = new ArrayList<>();

        try {
            while (resultsDocument.hasNext()) {
                dtos.add(ParticipantExamReportDto.deserialize(resultsDocument.next().toJson()));
            }
        } finally {
            resultsDocument.close();
        }

        return dtos;
    }

    private Bson parseQuery(String stage) {
        return new GsonBuilder().create().fromJson(stage, Document.class);
    }

}

