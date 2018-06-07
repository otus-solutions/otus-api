package org.ccem.otus.model.dataSources.exam;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.dataSources.ReportDataSource;

public class ExamDataSource extends ReportDataSource<ExamDataSourceResult> {

    public static String SIGN_ID = "$_id";
    public static String SIGN_EQ = "$eq";
    public static String SIGN_EXPR = "$expr";
    public static String SIGN_MATCH = "$match";
    public static String SIGN_EXISTS = "$exists";
    public static String SIGN_LOOKUP = "$lookup";
    public static String SIGN_EXAM_ID = "$examId";
    public static String SIGN_EXAM_OID = "$$exam_oid";

    public static String AS = "as";
    public static String ID = "_id";
    public static String LET = "let";
    public static String FROM = "from";
    public static String NAME = "name";
    public static String EXAM = "Exam";
    public static String PIPELINE = "pipeline";
    public static String EXAM_OID = "exam_oid";
    public static String OBJECT_TYPE = "objectType";
    public static String EXAM_RESULTS = "examResults";
    public static String ALIQUOT_VALID = "aliquotValid";
    public static String COLLECTION_EXAM_RESULT = "exam_result";
    public static String RECRUITMENT_NUMBER = "recruitmentNumber";
    public static String EXAM_RESULTS_RECRUITMENT_NUMBER = "examResults.recruitmentNumber";

    private ExamDataSourceFilters filters;

    @Override
    public void addResult(ExamDataSourceResult result) {
        super.getResult().add(result);
    }

    @Override
    public ArrayList<Document> buildQuery(Long recruitmentNumber) {
        return this.buildQueryToExamSendingLot(recruitmentNumber);
    }

    public ArrayList<Document> buildQueryToExamResults(ObjectId objectId, Long recruitmentNumber) {
        ArrayList<Document> query = new ArrayList<>();

        Document match = new Document(SIGN_MATCH, new Document(ID, objectId)
                .append(OBJECT_TYPE, EXAM).append(NAME, this.filters.getExamName())
        );
        query.add(match);

        Document lookup = new Document(SIGN_LOOKUP, new Document(FROM, COLLECTION_EXAM_RESULT)
                .append(LET, new Document(EXAM_OID, SIGN_ID))
                .append(PIPELINE, Arrays.asList(
                        new Document(SIGN_MATCH, new Document(RECRUITMENT_NUMBER, recruitmentNumber)
                                .append(ALIQUOT_VALID, Boolean.TRUE)
                                .append(SIGN_EXPR, new Document(SIGN_EQ, Arrays.asList(SIGN_EXAM_ID, SIGN_EXAM_OID)))
                        )
                ))
                .append(AS, EXAM_RESULTS)
        );
        query.add(lookup);

        Document matchHasExamResult = new Document(SIGN_MATCH, new Document(EXAM_RESULTS_RECRUITMENT_NUMBER, new Document(SIGN_EXISTS, 1)));
        query.add(matchHasExamResult);

        return query;
    }

    private ArrayList<Document> buildQueryToExamSendingLot(Long recruitmentNumber) {
        ArrayList<Document> query = new ArrayList<>();

        Document match = new Document(
                "$match",
                new Document("objectType", "ExamResults")
                        .append("examName", this.filters.getExamName())
                        .append("recruitmentNumber", recruitmentNumber)
        );
        query.add(match);

        Document lookup = new Document(
                "$lookup",
                new Document("from", "exam_sending_lot")
                        .append("localField", "examSendingLotId")
                        .append("foreignField", "_id")
                        .append("as", "sendingLot")
        );
        query.add(lookup);

        Document matchFieldCenter = new Document("$match", new Document("sendingLot.fieldCenter.acronym", this.filters.getFieldCenter()));
        query.add(matchFieldCenter);

        Document sort = new Document("$sort",
                new Document("sendingLot.realizationDate", 1)
                        .append("_id", 1)
        );
        query.add(sort);

        Document limitToResults = new Document("$limit", 1);
        query.add(limitToResults);

        Document project = new Document("$project", new Document("_id", 1)
                .append("examSendingLotId", 1)
                .append("examId", 1)
                .append("objectType", 1)
                .append("aliquotCode", 1)
                .append("examName", 1)
                .append("resultName", 1)
                .append("value", 1)
                .append("aliquotValid", 1)
                .append("releaseDate", 1)
                .append("observations", 1)
                .append("recruitmentNumber", 1)
                .append("sex", 1)
                .append("birthdate", 1)
                .append("sendingLot", 1)
                .append("forcedSave", 1)
        );
        query.add(project);

        return query;
    }

}
