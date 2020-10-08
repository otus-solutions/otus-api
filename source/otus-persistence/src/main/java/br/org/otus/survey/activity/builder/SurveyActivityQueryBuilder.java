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
      "                $expr: {\n" +
      "                    $and: [\n" +
      "                        { $eq: [\"$participantData.recruitmentNumber\", "+ rn + "] },\n" +
      "                        { $eq: [\"$isDiscarded\", false] },\n" +
      "                        { $in: [\"$surveyForm.acronym\", "+permittedSurveys+"] }\n" +
      "                    ]\n" +
      "                }\n" +
      "            }\n" +
      "        },\n" +
      "        {\n" +
      "            $group: {\n" +
      "                _id: { \n" +
      "                    stageID: \"$stageID\",\n" +
      "                    acronym:\"$surveyForm.acronym\"\n" +
      "                },\n" +
      "                \"activities\": { \n" +
      "                    \"$push\": {\n" +
      "                        _id: \"$_id\",\n" +
      "                        \"objectType\": \"ActivityBasicModel\",\n" +
      "                    \t\"acronym\": \"$surveyForm.acronym\",\n" +
      "                    \t\"name\": \"$surveyForm.name\",\n" +
      "                    \t\"mode\": \"$mode\",\n" +
      "                    \t\"category\": \"$category.name\",\n" +
      "                        lastStatus: { $slice: [ \"$statusHistory\", -1 ] },\n" +
      "                        \"externalID\": \"$externalID\"\n" +
      "                    }\n" +
      "                }\n" +
      "            }\n" +
      "            \n" +
      "        },\n" +
      "        {\n" +
      "            $group:{\n" +
      "                _id: \"$_id.stageID\",\n" +
      "                acronyms: { $push:\"$$ROOT\" }\n" +
      "            }\n" +
      "        },\n" +
      "        {\n" +
      "            $sort: { \"_id\": 1 }\n" +
      "        }"));
    return pipeline;
  }
}
