package br.org.otus.monitoring.builder;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ExamsFlagReportQueryBuilder {

  private ArrayList<Bson> pipeline;

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }

//  private ArrayList parsePipeline(String pipeline) {
//    ArrayList arrayList = getGsonBuilder().fromJson(pipeline, ArrayList.class);
//    return arrayList;
//  }
//
//  private Gson getGsonBuilder () {
//    return new GsonBuilder().create();
//  }

  public ExamsFlagReportQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public ArrayList<Bson> getExamResultsStatusQuery(LinkedList<String> allPossibleExams, ArrayList<Long> centerRns) {
    matchRnList(centerRns);
    add();
    addHeaders(allPossibleExams);
    return pipeline;
  }

  private void matchRnList(ArrayList<Long> centerRns) {
    pipeline.add(parseQuery("{\"$match\":{\"recruitmentNumber\":{\"$in\":" + centerRns + "}}}"));
  }

  private void add() {
    pipeline.add(parseQuery("{\"$project\":{\"recruitmentNumber\":1,\"examName\":1}}"));
    pipeline.add(parseQuery("{\"$group\":{\"_id\":\"$recruitmentNumber\",\"exams\":{\"$addToSet\":\"$examName\"}}}"));
  }

  private void addHeaders(LinkedList<String> headers) {
//    pipeline.addAll(parsePipeline("[{\"$addFields\":{\"headers\":\"headers\"}},{\"$unwind\":\"$headers\"}]"));
    pipeline.add(new Document("$addFields", new Document("headers",headers)));
    pipeline.add(parseQuery("{\"$unwind\":\"$headers\"}"));
    //
    pipeline.add(parseQuery("{\"$addFields\":{\"found\":{\"$filter\":{\"input\":\"$exams\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item\",\"$headers\"]}}}}}"));
    pipeline.add(parseQuery("{\"$group\":{\"_id\":\"$_id\",\"filtered\":{\"$push\":{\"$cond\":[{\"$gt\":[{\"$size\":\"$found\"},0]},1,0]}}}}"));
    pipeline.add(parseQuery("{\"$group\":{\"_id\":{},\"index\":{\"$push\":\"$_id\"},\"data\":{\"$push\":\"$filtered\"}}}"));

  }


}
