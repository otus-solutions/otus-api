package br.org.otus.monitoring.builder;


import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActivityStatusQueryBuilder {

    private ArrayList<Bson> pipeline;

    public ActivityStatusQueryBuilder() {
        this.pipeline = new ArrayList<>();
    }

    public ArrayList<Bson> getActivityStatusQuery(String center, LinkedList<String> surveyAcronyms) {
        addMatchFieldCenterStage(center);
        addBuildDataStages(surveyAcronyms, null);
        return pipeline;
    }

    public ArrayList<Bson> getActivityStatusQueryWithInapplicability(String center, LinkedList<String> surveyAcronyms, Document activityInapplicabilities) {
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
        pipeline.add(ParseQuery.toDocument("{\n" +
                "    $match: {\n" +
                "      \"participantData.fieldCenter.acronym\": " + center + ",\n" +
                "      \"isDiscarded\":false" +
                "    }\n" +
                "  }"));
    }

    private void addMatchIsDiscardedStage() {
        pipeline.add(ParseQuery.toDocument("{\n" +
                "    $match: {\n" +
                "      \"isDiscarded\":false" +
                "    }\n" +
                "  }"));
    }

    private void addBuildDataStages(LinkedList<String> surveyAcronyms, Document AIS) {
        pipeline.add(ParseQuery.toDocument("{\n" +
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
        pipeline.add(ParseQuery.toDocument("{\n" +
                "    $sort: {\n" +
                "      lastStatus_Date: 1\n" +
                "    }\n" +
                "  }"));


        pipeline.add(ParseQuery.toDocument("{\n" +
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
        pipeline.add(ParseQuery.toDocument("{\n" +
                "    $addFields: {\n" +
                "      \"headers\": " + surveyAcronyms + "\n" +
                "    }\n" +
                "  }"));
        pipeline.add(ParseQuery.toDocument("{\n" +
                "    $unwind: \"$headers\"\n" +
                "  }"));
        pipeline.add(ParseQuery.toDocument("{\n" +
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

        pipeline.add(ParseQuery.toDocument("{" +
                "    $addFields:{" +
                "        activityInapplicabilities:{" +
                "            $filter: {" +
                "                input:" + new GsonBuilder().create().toJson(AIS.get("AI")) + ", as: \"activityInapplicalibity\"," +
                "                cond: {" +
                "                    $and:[" +
                "                        {$eq:[\"$$activityInapplicalibity.recruitmentNumber\",\"$_id\"]}," +
                "                        {$eq:[\"$$activityInapplicalibity.acronym\",\"$headers\"]}" +
                "    ]}}}}}"));


        pipeline.add(ParseQuery.toDocument("{\n" +
                "        $group:{\n" +
                "            _id:\"$_id\",\n" +
                "            filteredActivities:{\n" +
                "                $push:{\n" +
                "                    $cond:[\n" +
                "                        {$gt:[{$size:\"$activityInapplicabilities\"},0]},\n" +
                "                        {\n" +
                "                            status: 0,\n" +
                "                            rn:\"$_id\",\n" +
                "                            acronym:\"$headers\"\n" +
                "                        },\n" +
                "                        { \n" +
                "                            $cond:[\n" +
                "                                {$gt:[{$size:\"$activityFound\"},0]},\n" +
                "                                {$arrayElemAt:[\"$activityFound\",-1]},\n" +
                "                                { \n" +
                "                                    status: null,\n" +
                "                                    rn:'$_id',\n" +
                "                                    acronym:'$headers'\n" +
                "                                }\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ]\n" +
                "                    \n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }"));


        pipeline.add(ParseQuery.toDocument("{\n" +
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
