package br.org.otus.survey.activity.builder;

import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivityQueryBuilder {

  public ArrayList<Bson> getSurveyActivityListByStageAndAcronymQuery(long rn, List<String> permittedSurveys){

    String acronymsCondition = "{ $in: [\"$surveyForm.acronym\", "+ permittedSurveys.toString() +"] },\n";
    if(permittedSurveys.isEmpty()){
      acronymsCondition = "";
    }

    ArrayList<Bson> pipeline = new ArrayList<>();

    pipeline.add(ParseQuery.toDocument("{\n" +
        "            $match: {\n" +
        "                $expr: {\n" +
        "                    $and: [\n" +
      "                       " + acronymsCondition +
        "                        { $eq: [\"$participantData.recruitmentNumber\", "+ rn + "] },\n" +
        "                        { $eq: [\"$isDiscarded\", false] }\n" +
        "                    ]\n" +
        "                }\n" +
        "            }\n" +
        "        }"));

    pipeline.add(ParseQuery.toDocument("  {\n" +
      "            $group: {\n" +
      "                _id: { \n" +
      "                    stageID: \"$stageID\",\n" +
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
      "                _id: \"$_id.stageID\",\n" +
      "                acronyms: { $push: \"$$ROOT\" }\n" +
      "            }\n" +
      "        }"));

    pipeline.add(ParseQuery.toDocument("{\n" +
      "            $sort: { _id: 1 }\n" +
      "        }"));

    return pipeline;
  }
}
