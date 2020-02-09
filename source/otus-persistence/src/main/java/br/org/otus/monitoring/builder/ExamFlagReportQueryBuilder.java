package br.org.otus.monitoring.builder;


import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExamFlagReportQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ExamFlagReportQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public ArrayList<Bson> getExamResultsStatusQuery(LinkedList<String> allPossibleExams, ArrayList<Long> centerRns, List<Document> EIS) {
    matchRnList(centerRns);
    collectParticipantExams(centerRns);
    buildFlagReport(allPossibleExams, EIS);
    return pipeline;
  }

  private void matchRnList(ArrayList<Long> centerRns) {
    pipeline.add(ParseQuery.toDocument("{\"$match\":{\"recruitmentNumber\":{\"$in\":" + centerRns + "}}}"));
  }

  private void collectParticipantExams(ArrayList<Long> centerRns) {
    pipeline.add(ParseQuery.toDocument("{\"$project\":{\"recruitmentNumber\":1,\"examName\":1}}"));
    pipeline.add(ParseQuery.toDocument("{\"$group\":{\"_id\":\"$recruitmentNumber\",\"exams\":{\"$addToSet\":\"$examName\"}}}"));
    pipeline.add(ParseQuery.toDocument("{\"$group\":{\"_id\":{},\"participantList\":{\"$push\":{\"recruitmentNumber\":\"$_id\",\"exams\":\"$exams\"}}}}"));
    pipeline.add(ParseQuery.toDocument("{\"$addFields\":{\"allRns\":" + centerRns + "}}"));
    pipeline.add(ParseQuery.toDocument("{\"$unwind\":\"$allRns\"}"));
    pipeline.add(ParseQuery.toDocument("{\"$addFields\":{" +
      "\"participantFound\":{" +
      "   \"$arrayElemAt\":[" +
      "       {\"$filter\":{" +
      "           \"input\":\"$participantList\",\"as\":\"participant\"," +
      "           \"cond\":{\"$eq\":[\"$$participant.recruitmentNumber\",\"$allRns\"]}" +
      "}},0]}}}"));
    pipeline.add(ParseQuery.toDocument("{\"$project\":{\"_id\":\"$allRns\",\"exams\":{\"$ifNull\":[\"$participantFound.exams\",[]]}}}"));
  }

  private void buildFlagReport(LinkedList<String> headers, List<Document> EIS) {
    pipeline.add(new Document("$addFields", new Document("headers", headers)));
    pipeline.add(ParseQuery.toDocument("{\"$unwind\":\"$headers\"}"));

    pipeline.add(ParseQuery.toDocument("{\"$addFields\":{\"found\":{\"$filter\":{\"input\":\"$exams\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item\",\"$headers\"]}}}}}"));


    pipeline.add(ParseQuery.toDocument("{$addFields:{" +
      "        examInapplicatibityFound:{" +
      "            $filter:{" +
      "                input: " + new GsonBuilder().create().toJson(EIS) + ", as: \"examInnaplicability\"," +
      "                cond: {" +
      "                    $and:[" +
      "                        {$eq:[\"$$examInnaplicability.recruitmentNumber\",\"$_id\"]}," +
      "                        {$eq:[\"$$examInnaplicability.name\", \"$headers\"]}" +
      "                        ]" +
      "                }" +
      "            }" +
      "        }}}"));


    //sets 1 if the participant has the exam, -1 if ausent and 0 if inapplicability
    pipeline.add(ParseQuery.toDocument("{$group:{\n" +
      "        _id:\"$_id\"," +
      "        filtered:{" +
      "            $push:{" +
      "                $cond:[{" +
      "                    $gt:[{$size: \"$examInapplicatibityFound\"},0]" +
      "                },0," +
      "                {$cond:[{" +
      "                    $gt:[{$size:\"$found\"},0]" +
      "                },1,-1]}]" +
      "        }" +
      "    }}}"));

    // adapt the result to the flag report format (expected by D3 (js library))
    pipeline.add(ParseQuery.toDocument("{\"$group\":{\"_id\":{},\"index\":{\"$push\":\"$_id\"},\"data\":{\"$push\":\"$filtered\"}}}"));
  }
}