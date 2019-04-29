package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.monitoring.builder.ExamsProgressQueryBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.persistence.ExamFlagReportDao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExamFlagReportDaoBean extends MongoGenericDao<Document> implements ExamFlagReportDao {

    public static final String COLLECTION_NAME = "exam_results";

    public ExamFlagReportDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public Document getExamProgressReport(String center, LinkedList<String> surveyAcronyms, ArrayList<Long> centerRns) throws DataNotFoundException {
        List<Bson> query = new ExamsProgressQueryBuilder().getExamResultsStatusQuery(center, surveyAcronyms, centerRns);

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
