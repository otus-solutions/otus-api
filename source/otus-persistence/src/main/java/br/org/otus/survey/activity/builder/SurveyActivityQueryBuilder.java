package br.org.otus.survey.activity.builder;

import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivityQueryBuilder {

  public ArrayList<Bson> getSurveyActivityListByStageAndAcronymQuery(long rn, List<String> permittedSurveys){

    ArrayList<Bson> pipeline = new ArrayList<>();

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $match: {\n" +
      "                \"surveyForm.acronym\": { $in: "+ permittedSurveys.toString() +" },\n" +
      "                \"participantData.recruitmentNumber\": " + rn + ",\n" +
      "                \"isDiscarded\": false\n" +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $lookup: {\n" +
      "                from:\"activity_permission\",\n" +
      "                let: {\n" +
      "                    acronym: \"$surveyForm.acronym\",\n" +
      "                    version: \"$surveyForm.version\"\n" +
      "                },\n" +
      "                pipeline: [\n" +
      "                    {\n" +
      "                        $match: { \n" +
      "                            $expr: {\n" +
      "                                $and: [\n" +
      "                                    { $eq: [\"$acronym\", \"$$acronym\"] },\n" +
      "                                    { $eq: [\"$version\", \"$$version\"] }\n" +
      "                                ]\n" +
      "                            }\n" +
      "                        }\n" +
      "                    },\n" +
      "                    {\n" +
      "                        $project: {\n" +
      "                            '_id': 0,\n" +
      "                            \"emails\": \"$exclusiveDisjunction\"\n" +
      "                        }\n" +
      "                    }\n" +
      "                ],\n" +
      "                as:\"permissionUsers\"\n" +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $addFields: {\n" +
      "                \"permissionUsers\": { $ifNull: [ { $arrayElemAt: [\"$permissionUsers\", 0] }, [] ] } \n" +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $match: {\n" +
      "                $expr: {\n" +
      "                    $eq: [ 0, \n" +
      "                        {\n" +
      "                            $size: {\n" +
      "                                $filter:{\n" +
      "                                    \"input\": \"$statusHistory\",\n" +
      "                                    \"as\": \"statusHistory\",\n" +
      "                                    \"cond\": {\n" +
      "                                        $and: [\n" +
      "                                            { $in: [ \"$$statusHistory.name\", [\"SAVED\", \"FINALIZED\"]] },\n" +
      "                                            { $not: { $eq: [ userEmail, \"$$statusHistory.user.email\" ] } },  \n" +
      "                                            { $in: [ \"$$statusHistory.user.email\", \"$permissionUsers.emails\"] }\n" +
      "                                        ]\n" +
      "                                    }\n" +
      "                                }\n" +
      "                            }\n" +
      "                        }\n" +
      "                    ]\n" +
      "                }\n" +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("  {\n" +
      "            $group: {\n" +
      "                _id: { \n" +
      "                    stageId: \"$stageId\",\n" +
      "                    acronym:\"$surveyForm.acronym\"\n" +
      "                },\n" +
      "                activities: { \n" +
      "                    $push: {\n" +
      "                        _id: \"$_id\",\n" +
      "                        objectType: \"ActivityBasicModel\",\n" +
      "                        acronym: \"$surveyForm.acronym\",\n" +
      "                        name: \"$surveyForm.name\",\n" +
      "                        mode: \"$mode\",\n" +
      "                        category: \"$category.name\",\n" +
      "                        lastStatus: { $arrayElemAt: [ \"$statusHistory\", -1 ] },\n" +
      "                        externalID: \"$externalID\"\n" +
      "                    }\n" +
      "                }\n" +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $group:{\n" +
      "                _id: \"$_id.stageId\",\n" +
      "                acronyms: { $push: \"$$ROOT\" }\n" +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $sort: { _id: 1 }\n" +
      "        }"));

    return pipeline;
  }
}
