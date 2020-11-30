package br.org.otus.monitoring.builder;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityStatusQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ActivityStatusQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public ArrayList<Bson> getActivityStatusQuery(List<Long> centerRecruitmentNumbers,
                                                LinkedList<String> surveyAcronyms, Document activityInapplicabilities) {
    addMatchFieldCenterRns(centerRecruitmentNumbers);
    addBuildDataStages(surveyAcronyms, activityInapplicabilities);
    return pipeline;
  }

  public ArrayList<Bson> getActivityStatusQuery(LinkedList<String> surveyAcronyms, Document activityInapplicabilities) {
    addMatchIsDiscardedStage();
    addBuildDataStages(surveyAcronyms, activityInapplicabilities);
    return pipeline;
  }

  private void addMatchFieldCenterRns(List<Long> centerRecruitmentNumbers) {
    pipeline.add(ParseQuery.toDocument("{ \n" +
      "        $match: {\n" +
      "            \"participantData.recruitmentNumber\": { $in: " + centerRecruitmentNumbers.toString() +" },\n" +
      "            \"isDiscarded\": false\n" +
      "        }\n" +
      "    }"));
  }

  private void addMatchIsDiscardedStage() {
    pipeline.add(ParseQuery.toDocument("{" +
      "    $match: {" +
      "      \"isDiscarded\":false" +
      "    }" +
      "  }"));
  }

  private void addBuildDataStages(LinkedList<String> surveyAcronyms, Document AIS) {
    pipeline.add(ParseQuery.toDocument("{" +
      "    \"$project\": {" +
      "      \"_id\": 0.0," +
      "      \"rn\": \"$participantData.recruitmentNumber\"," +
      "      \"acronym\": \"$surveyForm.acronym\"," +
      "      \"lastStatus_Date\": {" +
      "        \"$arrayElemAt\": [" +
      "          {" +
      "            \"$slice\": [" +
      "              \"$statusHistory.date\"," +
      "              -1.0" +
      "            ]" +
      "          }," +
      "          0.0" +
      "        ]" +
      "      }," +
      "      \"lastStatus_Name\": {" +
      "        \"$arrayElemAt\": [" +
      "          {" +
      "            \"$slice\": [" +
      "              \"$statusHistory.name\"," +
      "              -1.0" +
      "            ]" +
      "          }," +
      "          0.0" +
      "        ]" +
      "      }" +
      "    }" +
      "  }"));
    pipeline.add(ParseQuery.toDocument("{" +
      "    $sort: {" +
      "      lastStatus_Date: 1" +
      "    }" +
      "  }"));
    pipeline.add(ParseQuery.toDocument("{" +
      "    \"$group\": {" +
      "      \"_id\": \"$rn\"," +
      "      \"activities\": {" +
      "        \"$push\": {" +
      "          \"status\": {" +
      "            \"$cond\": [" +
      "              {" +
      "                \"$eq\": [" +
      "                  \"$lastStatus_Name\"," +
      "                  \"CREATED\"" +
      "                ]" +
      "              }," +
      "              -1.0," +
      "              {" +
      "                \"$cond\": [" +
      "                  {" +
      "                    \"$eq\": [" +
      "                      \"$lastStatus_Name\"," +
      "                      \"SAVED\"" +
      "                    ]" +
      "                  }," +
      "                  1.0," +
      "                  {" +
      "                    \"$cond\": [" +
      "                      {" +
      "                        \"$eq\": [" +
      "                          \"$lastStatus_Name\"," +
      "                          \"FINALIZED\"" +
      "                        ]" +
      "                      }," +
      "                      2.0," +
      "                      -1.0" +
      "                    ]" +
      "                  }" +
      "                ]" +
      "              }" +
      "            ]" +
      "          }," +
      "          \"rn\": \"$rn\"," +
      "          \"acronym\": \"$acronym\"" +
      "        }" +
      "      }" +
      "    }" +
      "  }"));
    pipeline.add(ParseQuery.toDocument("{$addFields:{activityInapplicabilities:{$arrayElemAt:[{$filter:{\"input\":" + new GsonBuilder().create().toJson(AIS.get("participantAI")) + ",\"as\":\"activityInapplicalibity\",\"cond\":{\"$and\":[{\"$eq\":[\"$$activityInapplicalibity.rn\",\"$_id\"]}]}}},0]}}}"));
    pipeline.add(ParseQuery.toDocument("{" +
      "      \"$addFields\":{" +
      "          \"activityInapplicabilities\":{" +
      "              \"$cond\":[" +
      "                  {" +
      "                      \"$ifNull\":[\"$activityInapplicabilities\",false]" +
      "                  }," +
      "                  \"$activityInapplicabilities\"," +
      "                  []" +
      "                  ]" +
      "          }" +
      "      }" +
      "  }"));
    pipeline.add(ParseQuery.toDocument("{" +
      "    $addFields: {" +
      "      \"headers\": " + surveyAcronyms + "" +
      "    }" +
      "  }"));
    pipeline.add(ParseQuery.toDocument("{" +
      "    $unwind: \"$headers\"" +
      "  }"));
    pipeline.add(ParseQuery.toDocument("{" +
      "    \"$addFields\": {" +
      "      \"activityFound\": {" +
      "        \"$filter\": {" +
      "          \"input\": \"$activities\"," +
      "          \"as\": \"item\"," +
      "          \"cond\": {" +
      "            \"$eq\": [" +
      "              \"$$item.acronym\"," +
      "              \"$headers\"" +
      "            ]" +
      "          }" +
      "        }" +
      "      }," +
      "      \"inapplicabilityFound\": {" +
      "        \"$gt\": [" +
      "          {" +
      "            \"$size\": {" +
      "              \"$filter\": {" +
      "                \"input\": \"$activityInapplicabilities.AI\"," +
      "                \"as\": \"item\"," +
      "                \"cond\": {" +
      "                  \"$eq\": [" +
      "                    \"$$item.acronym\"," +
      "                    \"$headers\"" +
      "                  ]" +
      "                }" +
      "              }" +
      "            }" +
      "          }," +
      "          0" +
      "        ]" +
      "      }" +
      "    }" +
      "  }"));

    pipeline.add(ParseQuery.toDocument("{" +
      "    \"$group\": {" +
      "      \"_id\": \"$_id\"," +
      "      \"filteredActivities\": {" +
      "        \"$push\": {" +
      "          \"$cond\": [" +
      "            \"$inapplicabilityFound\"," +
      "            {" +
      "              \"status\": 0.0," +
      "              \"rn\": \"$_id\"," +
      "              \"acronym\": \"$headers\"" +
      "            }," +
      "            {" +
      "              \"$cond\": [" +
      "                {" +
      "                  \"$gt\": [" +
      "                    {" +
      "                      \"$size\": \"$activityFound\"" +
      "                    }," +
      "                    0.0" +
      "                  ]" +
      "                }," +
      "                {" +
      "                  \"$arrayElemAt\": [" +
      "                    \"$activityFound\"," +
      "                    -1.0" +
      "                  ]" +
      "                }," +
      "                {" +
      "                  \"status\": null," +
      "                  \"rn\": \"$_id\"," +
      "                  \"acronym\": \"$headers\"" +
      "                }" +
      "              ]" +
      "            }" +
      "          ]" +
      "        }" +
      "      }" +
      "    }" +
      "  }"));

    pipeline.add(ParseQuery.toDocument("{" +
      "    $group: {" +
      "      _id: {" +
      "      }," +
      "      index: {" +
      "        $push: \"$_id\"" +
      "      }," +
      "      data: {" +
      "        $push: \"$filteredActivities.status\"" +
      "      }" +
      "    }" +
      "  }"));
  }
}
