package br.org.otus.user.pendency.builder;

import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;

public class UserActivityPendencyQueryBuilder {

  public static final String FINALIZED_STATUS = "FINALIZED";

  private ArrayList<Bson> pipeline;

  public ArrayList<Bson> getListAllPendenciesByUserQuery(String userRole, String userEmail) {
    return getListPendenciesByUserQuery(userRole, userEmail, "");
  }

  public ArrayList<Bson> getListOpenedPendenciesByUserQuery(String userRole, String userEmail) {
    return getListPendenciesByUserQuery(userRole, userEmail, getStatusCondition("ne"));
  }

  public ArrayList<Bson> getListDonePendenciesByUserQuery(String userRole, String userEmail) {
    return getListPendenciesByUserQuery(userRole, userEmail, getStatusCondition("eq"));
  }

  private ArrayList<Bson> getListPendenciesByUserQuery(String userRole, String userEmail, String statusCondition) {
    pipeline = new ArrayList<>();
    addLookupMatchingActivityPendency(statusCondition);
    addMatchByPendencyUser(userRole, userEmail);
    addSelectedFieldsFromActivityLookupResult();
    return pipeline;
  }

  private String getStatusCondition(String operator) {
    return ",\n{ $" + operator + ": [\"" + FINALIZED_STATUS + "\", { $arrayElemAt: [ \"$statusHistory.name\", -1 ] } ] }";
  }

  private void addLookupMatchingActivityPendency(String statusCondition) {
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $lookup: {\n" +
      "            from:\"activity\",\n" +
      "            let: {\n" +
      "                activityId: \"$activityId\"\n" +
      "            },\n" +
      "            pipeline: [\n" +
      "                {\n" +
      "                    $match: {\n" +
      "                        $expr: {\n" +
      "                            $and: [\n" +
      "                                { $eq: [\"$$activityId\", \"$_id\"] },\n" +
      "                                { $eq: [false, \"$isDiscarded\"] }" +
      "                                " + statusCondition +
      "                            ]\n" +
      "                        }\n" +
      "                    }\n" +
      "                },\n" +
      "                {\n" +
      "                    $project: {\n" +
      "                        'recruitmentNumber': '$participantData.recruitmentNumber',\n" +
      "                        'name': '$surveyForm.name',\n" +
      "                        'acronym': '$surveyForm.acronym',\n" +
      "                        'lastStatusName': { $arrayElemAt: [ \"$statusHistory.name\", -1 ] },\n" +
      "                        'externalID': 1\n" +
      "                    }\n" +
      "                }\n" +
      "            ],\n" +
      "            as:\"activityInfo\"\n" +
      "        }\n" +
      "    }"));
  }

  private void addMatchByPendencyUser(String userRole, String userEmail) {
    pipeline.add(ParseQuery.toDocument("{ \n" +
      "        $match: {\n" +
      "            $expr: { \n" +
      "                $and: [\n" +
      "                    { $eq: [ \"$" + userRole + "\", " + userEmail + " ] },\n" +
      "                    { $gt: [ { $size: \"$activityInfo\"}, 0] }\n" +
      "                ]\n" +
      "            }\n" +
      "        } \n" +
      "    }"));
  }

  private void addSelectedFieldsFromActivityLookupResult() {
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $addFields: { 'activityInfo': { $arrayElemAt: [\"$activityInfo\", 0]} }\n" +
      "    }"));
  }

}
