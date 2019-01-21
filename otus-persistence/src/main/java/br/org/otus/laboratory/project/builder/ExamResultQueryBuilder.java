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
    Document match = parseQuery("{ $match:{\"objectType\":\"ExamResults\",\"aliquotValid\":true}}");

    pipeline.add(match);
    return this;
  }

  public ExamResultQueryBuilder getSortingByExamName() {
    Document sort = parseQuery("{$sort:{\"resultName\":1}}");

    pipeline.add(sort);
    return this;
  }
  
  public ExamResultQueryBuilder getExamResultsGroupByRecruitmentNumber() {
    Document sort = parseQuery("{\n" + 
        "        $group: {\n" + 
        "            _id: \"$recruitmentNumber\",\n" + 
        "            results:\n" + 
        "            {\n" + 
        "                $push: {\n" + 
        "                    \"resultName\": \"$resultName\",\n" + 
        "                    \"value\": \"$value\",\n" + 
        "                    \"releaseDate\": \"$releaseDate\"\n" + 
        "                }\n" + 
        "            }\n" + 
        "        }\n" + 
        "    }");

    pipeline.add(sort);
    return this;
  }
  
  public ExamResultQueryBuilder getProjectionOfExamResultsToExtraction() { // TODO: Melhorar o nome do método!
    Document project = parseQuery("$project: {\n" + 
        "            recruitmentNumber: \"$_id\",\n" + 
        "            _id: 0,\n" + 
        "            results: \"$results\"\n" + 
        "        }");
    
    pipeline.add(project);
    return this;
  }

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }

}
