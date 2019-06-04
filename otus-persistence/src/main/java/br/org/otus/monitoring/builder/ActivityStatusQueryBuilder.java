package br.org.otus.monitoring.builder;


import com.google.gson.GsonBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Aggregates;
import org.bson.BSON;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ActivityStatusQueryBuilder {

  private ArrayList<Bson> pipeline;

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }

  public ActivityStatusQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public ArrayList<Bson> getActivityStatusQuery(String center, LinkedList<String> surveyAcronyms) {
    addMatchFieldCenterStage(center);
    addBuildDataStages(surveyAcronyms, null);
    return pipeline;
  }

  public ArrayList<Bson> getActivityStatusQueryWithInapplicability(String center, LinkedList<String> surveyAcronyms, List<Document> activityInapplicabilities) {
    addMatchFieldCenterStage(center);
    addBuildDataStages(surveyAcronyms, activityInapplicabilities);
    return pipeline;
  }

  public ArrayList<Bson> getActivityStatusQuery(LinkedList<String> surveyAcronyms) {
    addMatchIsDiscardedStage();
    addBuildDataStages(surveyAcronyms, null);
    return pipeline;
  }

  private void addMatchFieldCenterStage(String center) {
    pipeline.add(parseQuery("{\n" +
            "    $match: {\n" +
            "      \"participantData.fieldCenter.acronym\": "+ center +",\n" +
            "      \"isDiscarded\":false" +
            "    }\n" +
            "  }"));
  }

  private void addMatchIsDiscardedStage() {
    pipeline.add(parseQuery("{\n" +
            "    $match: {\n" +
            "      \"isDiscarded\":false" +
            "    }\n" +
            "  }"));
  }

  private void addBuildDataStages(LinkedList<String> surveyAcronyms, List<Document> AIS) {
    pipeline.add(parseQuery("{\n" +
            "    $project: {\n" +
            "      _id: 0,\n" +
            "      rn: \"$participantData.recruitmentNumber\",\n" +
            "      acronym: \"$surveyForm.acronym\",\n" +
            "      lastStatus_Date: {\n" +
            "        $arrayElemAt: [\n" +
            "          {\n" +
            "            $slice: [\n" +
            "              \"$statusHistory.date\",\n" +
            "              -1\n" +
            "            ]\n" +
            "          },\n" +
            "          0\n" +
            "        ]\n" +
            "      },\n" +
            "      lastStatus_Name: {\n" +
            "        $arrayElemAt: [\n" +
            "          {\n" +
            "            $slice: [\n" +
            "              \"$statusHistory.name\",\n" +
            "              -1\n" +
            "            ]\n" +
            "          },\n" +
            "          0\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  }"));
    pipeline.add(parseQuery("{\n" +
            "    $sort: {\n" +
            "      lastStatus_Date: 1\n" +
            "    }\n" +
            "  }"));

//    pipeline.add(parseQuery("{\n" +
//            "    $addFields:{\n" +
//            "        activityInapplicabilities:{ \n" +
//            "            $filter: { \n" +
//            "                input: "+ AIS +", as: \"activityInapplicalibity\",\n" +
//            "                cond: {\n" +
//            "                    $and:[\n" +
//            "                        {$eq:[\"$$activityInapplicalibity.recruitmentNumber\",\"$_id\"]},\n" +
//            "                        {$eq:[\"$$activityInapplicalibity.acronym\",\"$headers\"]}\n" +
//            "                        ]\n" +
//            "                    }\n" +
//            "                }\n" +
//            "            }\n" +
//            "        }\n" +
//            "    }"));

    pipeline.add(parseQuery("{\n" +
            "    $group: {\n" +
            "      _id:\"$rn\",\n" +
            "      activities: {\n" +
            "        $push: {\n" +
            "          status: {\n" +
            "            $cond: [\n" +
            "              {\n" +
            "                $eq: [\n" +
            "                  \"$lastStatus_Name\",\n" +
            "                  \"CREATED\"\n" +
            "                ]\n" +
            "              },\n" +
            "              -1,\n" +
            "              {\n" +
            "                $cond: [\n" +
            "                  {\n" +
            "                    $eq: [\n" +
            "                      \"$lastStatus_Name\",\n" +
            "                      \"SAVED\"\n" +
            "                    ]\n" +
            "                  },\n" +
            "                  1,\n" +
            "                  {\n" +
            "                    $cond: [\n" +
            "                      {\n" +
            "                        $eq: [\n" +
            "                          \"$lastStatus_Name\",\n" +
            "                          \"FINALIZED\"\n" +
            "                        ]\n" +
            "                      },\n" +
            "                      2,\n" +
            "                      -1\n" +
            "                    ]\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          rn: \"$rn\",\n" +
            "          acronym: \"$acronym\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }"));
    pipeline.add(parseQuery("{\n" +
            "    $addFields: {\n" +
            "      \"headers\": "+ surveyAcronyms +"\n" +
            "    }\n" +
            "  }"));
    pipeline.add(parseQuery("{\n" +
            "    $unwind: \"$headers\"\n" +
            "  }"));
    pipeline.add(parseQuery("{\n" +
            "    $addFields: {\n" +
            "      \"activityFound\": {\n" +
            "        $filter: {\n" +
            "          input: \"$activities\",\n" +
            "          as: \"item\",\n" +
            "          cond: {\n" +
            "            $eq: [\n" +
            "              \"$$item.acronym\",\n" +
            "              \"$headers\"\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }"));


//    pipeline.add(parseQuery("{\n" +
//            "        $group:{\n" +
//            "            _id:\"$_id\",\n" +
//            "            filteredActivities:{\n" +
//            "                $push:{\n" +
//            "                    $cond:[\n" +
//            "                        {$gt:[{$size:\"$activityInapplicabilities\"},0]},\n" +
//            "                        {\n" +
//            "                            status: 0,\n" +
//            "                            rn:\"$_id\",\n" +
//            "                            acronym:\"$headers\"\n" +
//            "                        },\n" +
//            "                        { \n" +
//            "                            $cond:[\n" +
//            "                                {$gt:[{$size:\"$activityFound\"},0]},\n" +
//            "                                {$arrayElemAt:[\"$activityFound\",-1]},\n" +
//            "                                { \n" +
//            "                                    status: null,\n" +
//            "                                    rn:'$_id',\n" +
//            "                                    acronym:'$headers'\n" +
//            "                                }\n" +
//            "                            ]\n" +
//            "                        }\n" +
//            "                    ]\n" +
//            "                    \n" +
//            "                }\n" +
//            "            }\n" +
//            "        }\n" +
//            "    }"));

    pipeline.add(parseQuery("{\n" +
            "    $group: {\n" +
            "      _id: \"$_id\",\n" +
            "      filteredActivities: {\n" +
            "        $push: {\n" +
            "          $cond: [\n" +
            "            {\n" +
            "              $gt: [\n" +
            "                {\n" +
            "                  $size: \"$activityFound\"\n" +
            "                },\n" +
            "                0\n" +
            "              ]\n" +
            "            },\n" +
            "            {\n" +
            "              $arrayElemAt: [\n" +
            "                \"$activityFound\",\n" +
            "                -1\n" +
            "              ]\n" +
            "            },\n" +
            "            {\n" +
            "              status: null,\n" +
            "              rn: \"$_id\",\n" +
            "              acronym: \"$headers\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }"));

    pipeline.add(parseQuery("{\n" +
            "    $group: {\n" +
            "      _id: {\n" +
            "      },\n" +
            "      index: {\n" +
            "        $push: \"$_id\"\n" +
            "      },\n" +
            "      data: {\n" +
            "        $push: \"$filteredActivities.status\"\n" +
            "      }\n" +
            "    }\n" +
            "  }"));
  }
}
