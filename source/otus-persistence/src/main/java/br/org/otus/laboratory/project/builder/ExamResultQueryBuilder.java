package br.org.otus.laboratory.project.builder;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.GsonBuilder;

public class ExamResultQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ExamResultQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public List<Bson> build() {
    return this.pipeline;
  }

  public ExamResultQueryBuilder getExamResultsWithAliquotValid() {
    pipeline.add(this.parseQuery("{$match:{\"objectType\":\"ExamResults\",\"isValid\":true}}"));

    return this;
  }

  public ExamResultQueryBuilder getSortingByExamName() {
    pipeline.add(this.parseQuery("{$sort:{\"resultName\":1}}"));

    return this;
  }

  public ExamResultQueryBuilder getSortingByRecruitmentNumber() {
    pipeline.add(this.parseQuery("{$sort:{\"recruitmentNumber\":1}}"));

    return this;
  }

  public ExamResultQueryBuilder getGroupOfExamResultsToExtraction() {
    Document group = this.parseQuery("{\n" +
      "    $group: {\n" +
      "      _id: \"$recruitmentNumber\",\n" +
      "      results:{\n" +
      "        $push:{\n" +
      "          \"recruitmentNumber\":\"$recruitmentNumber\",\n" +
      "          \"code\":\"$code\",\n" +
      "          \"examName\":\"$examName\",\n" +
      "          \"resultName\":\"$resultName\",\n" +
      "          \"value\":\"$value\",\n" +
      "          \"releaseDate\":\"$releaseDate\",\n" +
      "          \"realizationDate\":\"$realizationDate\",\n" +
      "          \"observations\":\"$observations\",\n" +
      "          \"cutOffValue\":\"$cutOffValue\",\n" +
      "          \"extraVariables\":\"$extraVariables\"\n" +
      "        }\n" +
      "      }\n" +
      "    }\n" +
      "  }");

    pipeline.add(group);
    return this;
  }

  public ExamResultQueryBuilder getProjectionOfExamResultsToExtraction() {
    Document project = this.parseQuery("{\n" +
      "    $project: {\n" +
      "      recruitmentNumber: \"$_id\",\n" +
      "      _id: 0,\n" +
      "      results:\"$results\"\n" +
      "    }\n" +
      "  }");

    pipeline.add(project);
    return this;
  }

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }

}
