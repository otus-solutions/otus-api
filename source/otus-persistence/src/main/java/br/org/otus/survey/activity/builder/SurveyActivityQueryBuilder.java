package br.org.otus.survey.activity.builder;

import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivityQueryBuilder {

  public static final String ACRONYM_PATH = "surveyForm.acronym";
  public static final String VERSION_PATH = "surveyForm.version";
  public static final String DISCARDED_PATH = "isDiscarded";
  public static final String TEMPLATE_PATH = "surveyForm.surveyTemplate";
  public static final String RECRUITMENT_NUMBER_PATH = "participantData.recruitmentNumber";
  public static final String CATEGORY_NAME_PATH = "category.name";
  public static final String CATEGORY_LABEL_PATH = "category.label";
  public static final String IS_DISCARDED = "isDiscarded";
  public static final String ID_PATH = "_id";
  public static final String STATUS_HISTORY_NAME = "statusHistory.name";
  public static final String FINALIZED = "FINALIZED";
  private static final String SET = "$set";
  private static final String PARTICIPANT_DATA_EMAIL = "participantData.email";
  private static final String STAGE_PATH = "stageId";

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

  public static ArrayList<Bson> getActivityIdsQuery(String acronym, Integer version, Boolean isDiscardedValue,
                                                    List<String> activityIdsToExcludeOfQuery){

    String idsToExcludeExpression = "";
    if(activityIdsToExcludeOfQuery != null && !activityIdsToExcludeOfQuery.isEmpty()){
      idsToExcludeExpression = "\""+ID_PATH+"\": { $not: { $in: " + activityIdsToExcludeOfQuery.toString() + "} },\n";
    }

    String isDiscardedExpression = "";
    if(isDiscardedValue != null){
      isDiscardedExpression = "\""+DISCARDED_PATH+"\": " + isDiscardedValue.toString() +",\n";
    }

    ArrayList<Bson> pipeline = new ArrayList<>();

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $match: {\n" +
      "                " + idsToExcludeExpression +
      "                " + isDiscardedExpression +
      "                \""+ACRONYM_PATH+"\": "+ acronym + ",\n" +
      "                \""+VERSION_PATH+"\": "+ version +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $sort: { _id: 1 }\n" +
      "        }"));

    return pipeline;
  }
}
