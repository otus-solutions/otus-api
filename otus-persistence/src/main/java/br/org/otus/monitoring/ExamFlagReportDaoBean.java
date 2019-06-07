package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import br.org.otus.monitoring.builder.ExamFlagReportQueryBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.persistence.ExamFlagReportDao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExamFlagReportDaoBean extends MongoGenericDao<Document> implements ExamFlagReportDao {

    public static final String COLLECTION_NAME = "exam_result";

    public ExamFlagReportDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public Document getExamProgressReport(LinkedList<String> surveyAcronyms, ArrayList<Long> centerRns, List<Document> examInapplicabilities) throws DataNotFoundException {
        List<Bson> query = new ExamFlagReportQueryBuilder().getExamResultsStatusQuery(surveyAcronyms, centerRns, examInapplicabilities);

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
