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

  public ActivityStatusQueryBuilder limit(Integer quantity) {
    pipeline.add(Aggregates.limit(quantity));
    return this;
  }

  public ActivityStatusQueryBuilder projectLastStatus() {
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

  public ActivityStatusQueryBuilder getStatusValue() {
    Document projection = parseQuery("{\n" +
        "        $project: {\n" +
        "            status:\n" +
        "                {\n" +
        "                    $switch: {\n" +
        "                        branches: [\n" +
        "                            {case: {$eq: [\"$lastStatus.name\", \"CREATED\"]}, then: -1},\n" +
//      "                            {case: {$eq: [\"$lastStatus.name\", \"NOT MANDATORY\"]}, then: 0},\n" + // Not required - not implemented yet
        "                            {case: {$eq: [\"$lastStatus.name\", \"SAVED\"]}, then: 1},\n" +
        "                            {case: {$eq: [\"$lastStatus.name\", \"FINALIZED\"]}, then: 2}\n" +
        "                        ],\n" +
        "                        default: -1\n" +
        "                    }\n" +
        "                },\n" +
        "            statusDate:\"$lastStatus.name\",\n" +
        "            rn:1,\n" +
        "            center:1,\n" +
        "            acronym:1\n" +
        "        }\n" +
        "    }");

    pipeline.add(projection);
    return this;
  }

  public ActivityStatusQueryBuilder projectId() {
    pipeline.add(parseQuery("     {\n" +
        "         $project:{\n" +
        "             \"rn\": \"$_id.rn\",\n" +
        "             \"activities\": 1,\n" +
        "             \"_id\": 0\n" +
        "         }\n" +
        "     }"));

    return this;
  }

  public ActivityStatusQueryBuilder sortByDate() {
    // descending order. When passed to hashmap, the oldest for each acronym will be kept
    pipeline.add(parseQuery("  {\n" +
        "    $sort: {\n" +
        "      \"statusDate\": -1\n" +
        "    }\n" +
        "  }"));

    return this;
  }

  public ActivityStatusQueryBuilder removeStatusDate() {
    pipeline.add(parseQuery("{\n" +
        "    $project: {\n" +
        "      \"statusDate\": 0\n" +
        "    }\n" +
        "  }"));

    return this;
  }

  public ActivityStatusQueryBuilder groupByParticipant() {
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
