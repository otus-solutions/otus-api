package br.org.otus.monitoring.builder;


import com.google.gson.GsonBuilder;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityStatusQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ActivityStatusQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public List<Bson> build() {
    return this.pipeline;
  }

  public ActivityStatusQueryBuilder matchFieldCenter(String center) {
    pipeline.add(Aggregates.match(new Document("participantData.fieldCenter.acronym", center)));
    return this;
  }

  public ActivityStatusQueryBuilder project2() {
    Document p2 = parseQuery("{\n" +
      "        $project: {\n" +
      "            status:\n" +
      "                {\n" +
      "                    $switch: {\n" +
      "                        branches: [\n" +
      "                            {case: {$eq: [\"$lastStatus.name\", \"FINALIZED\"]}, then: 1},\n" +
      "                            {case: {$eq: [\"$lastStatus.name\", \"SAVED\"]}, then: 0}\n" +
      "                        ],\n" +
      "                        default: -1\n" +
      "                    }\n" +
      "                },\n" +
      "            rn:1,\n" +
      "            center:1,\n" +
      "            acronym:1\n" +
      "        }\n" +
      "    }");

    pipeline.add(p2);
    return this;
  }

  public ActivityStatusQueryBuilder project() {
    Document projection = new Document();

    projection.put("_id", 0);
    projection.put("rn", "$participantData.recruitmentNumber");
    projection.put("acronym", "$surveyForm.surveyTemplate.identity.acronym");
    projection.put("lastStatus",
      new Document("$arrayElemAt",
        Arrays.asList(
          new Document(
            "$slice",
            Arrays.asList("$statusHistory", -1)
          ),
          0)
      )
    );
    pipeline.add(Aggregates.project(projection));
    return this;
  }

  public ActivityStatusQueryBuilder groupByParticipant() {
//    pipeline.add(Aggregates.group(
//      parseQuery("{\n" +
//        "            _id: {\n" +
//        "                rn: \"$rn\"\n" +
//        "\n" +
//        "            },\n" +
//        "            activities: {\n" +
//        "                $addToSet: \"$$ROOT\"\n" +
//        "            }\n" +
//        "        }")));
    pipeline.add(parseQuery("{\n" +
      "        $group: {\n" +
      "            _id: {\n" +
      "                rn: \"$rn\"\n" +
      "\n" +
      "            },\n" +
      "            activities: {\n" +
      "                $addToSet: \"$$ROOT\"\n" +
      "            }\n" +
      "        }\n" +
      "    }"));
    return this;
  }


//  ===================

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }
}
