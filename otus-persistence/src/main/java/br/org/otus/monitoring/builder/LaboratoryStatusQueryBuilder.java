package br.org.otus.monitoring.builder;


import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.LinkedList;

public class LaboratoryStatusQueryBuilder {

  private ArrayList<Bson> pipeline;

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }

  public LaboratoryStatusQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public ArrayList<Bson> getExamResultsStatusQuery(String center, LinkedList<String> allPossibleExams) {

    return pipeline;
  }

  public void addCenterMatchStage(String center) {
    parseQuery("{\"$match\":{\"recruitmentNumber\":{\"$in\":" + "" + "}}}");
  }

  public void add() {
    parseQuery("{\"$project\":{\"recruitmentNumber\":1,\"examName\":1}}");
    parseQuery("{\"$group\":{\"_id\":\"$recruitmentNumber\",\"exams\":{\"$addToSet\":\"$examName\"}}}");

  }
}
