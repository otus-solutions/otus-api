package br.org.otus.monitoring.builder;


import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExamFlagReportQueryBuilder {

    private ArrayList<Bson> pipeline;

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }

    public ExamFlagReportQueryBuilder() {
        this.pipeline = new ArrayList<>();
    }

    public ArrayList<Bson> getExamResultsStatusQuery(LinkedList<String> allPossibleExams, ArrayList<Long> centerRns, List<Document> examInapplicabilities) {
        matchRnList(centerRns);
        collectParticipantExams(centerRns);
        buildFlagReport(allPossibleExams);
        return pipeline;
    }

    private void matchRnList(ArrayList<Long> centerRns) {
        pipeline.add(parseQuery("{\"$match\":{\"recruitmentNumber\":{\"$in\":" + centerRns + "}}}"));
    }

    private void collectParticipantExams(ArrayList<Long> centerRns) {
        pipeline.add(parseQuery("{\"$project\":{\"recruitmentNumber\":1,\"examName\":1}}"));
        pipeline.add(parseQuery("{\"$group\":{\"_id\":\"$recruitmentNumber\",\"exams\":{\"$addToSet\":\"$examName\"}}}"));
        pipeline.add(parseQuery("{\"$group\":{\"_id\":{},\"participantList\":{\"$push\":{\"recruitmentNumber\":\"$_id\",\"exams\":\"$exams\"}}}}"));
        pipeline.add(parseQuery("{\"$addFields\":{\"allRns\":" + centerRns + "}}"));
        pipeline.add(parseQuery("{\"$unwind\":\"$allRns\"}"));
        pipeline.add(parseQuery("{\"$addFields\":{" +
                "\"participantFound\":{" +
                "   \"$arrayElemAt\":[" +
                "       {\"$filter\":{" +
                "           \"input\":\"$participantList\",\"as\":\"participant\"," +
                "           \"cond\":{\"$eq\":[\"$$participant.recruitmentNumber\",\"$allRns\"]}" +
                "}},0]}}}"));
        pipeline.add(parseQuery("{\"$project\":{\"_id\":\"$allRns\",\"exams\":{\"$ifNull\":[\"$participantFound.exams\",[]]}}}"));
    }

    private void buildFlagReport(LinkedList<String> headers) {
        pipeline.add(new Document("$addFields", new Document("headers", headers)));
        pipeline.add(parseQuery("{\"$unwind\":\"$headers\"}"));

        pipeline.add(parseQuery("{\"$addFields\":{\"found\":{\"$filter\":{\"input\":\"$exams\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item\",\"$headers\"]}}}}}"));

        //sets 1 if the participant has the exam, 0 if doesn't
        pipeline.add(parseQuery("{\"$group\":{\"_id\":\"$_id\",\"filtered\":{\"$push\":{\"$cond\":[{\"$gt\":[{\"$size\":\"$found\"},0]},1,-1]}}}}"));

        // adapt the result to the flag report format (expected by D3 (js library))
        pipeline.add(parseQuery("{\"$group\":{\"_id\":{},\"index\":{\"$push\":\"$_id\"},\"data\":{\"$push\":\"$filtered\"}}}"));

    }
}
