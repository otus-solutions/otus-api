package br.org.otus.user.pendency.builder;

import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;

public class UserActivityPendencyQueryBuilder {

  public static final String ACTIVITY_ID_FIELD = "activityId";
  public static final String FINALIZED_STATUS = "FINALIZED";
  public static final String NO_STATUS = "";

  private static final String ACTIVITY_INFO = "activityInfo";

  private ArrayList<Bson> pipeline;

  public ArrayList<Bson> getListAllPendenciesByUserQuery(String userRole, String userEmail) {
    return getListPendenciesByUserQuery(userRole, userEmail, NO_STATUS);
  }

  public ArrayList<Bson> getListOpenedPendenciesByUserQuery(String userRole, String userEmail) {
    return getListPendenciesByUserQuery(userRole, userEmail, getOpenedStatusCondition());
  }

  public ArrayList<Bson> getListDonePendenciesByUserQuery(String userRole, String userEmail) {
    return getListPendenciesByUserQuery(userRole, userEmail, getDoneStatusCondition());
  }

  private ArrayList<Bson> getListPendenciesByUserQuery(String userRole, String userEmail, String statusCondition) {
    pipeline = new ArrayList<>();
    addLookupMatchingActivityPendency(statusCondition);
    addMatchByPendencyUser(userRole, userEmail);
    addSelectedFieldsFromActivityLookupResult();
    return pipeline;
  }

  private String getOpenedStatusCondition(){
    return getStatusCondition("ne");
  }

  private String getDoneStatusCondition(){
    return getStatusCondition("eq");
  }

  private String getStatusCondition(String operator) {
    return ",\n{ $" + operator + ": [\"" + FINALIZED_STATUS + "\", { $arrayElemAt: [ \"$statusHistory.name\", -1 ] } ] }";
  }

  private void addLookupMatchingActivityPendency(String activityFilterExpressions) {
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $lookup: {\n" +
      "            from:\"activity\",\n" +
      "            let: {\n" +
      "              "+ACTIVITY_ID_FIELD+": \"$"+ACTIVITY_ID_FIELD+"\"\n" +
      "            },\n" +
      "            pipeline: [\n" +
      "                {\n" +
      "                    $match: {\n" +
      "                        $expr: {\n" +
      "                            $and: [\n" +
      "                                { $eq: [\"$$"+ACTIVITY_ID_FIELD+"\", \"$_id\"] },\n" +
      "                                { $eq: [false, \"$isDiscarded\"] }" +
      "                                " + activityFilterExpressions +
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
      "            as:\""+ACTIVITY_INFO+"\"\n" +
      "        }\n" +
      "    }"));
  }

  private void addMatchByPendencyUser(String userRole, String userEmail) {
    pipeline.add(ParseQuery.toDocument("{ \n" +
      "        $match: {\n" +
      "            $expr: { \n" +
      "                $and: [\n" +
      "                    { $eq: [ \"$" + userRole + "\", " + userEmail + " ] },\n" +
      "                    { $gt: [ { $size: \"$"+ACTIVITY_INFO+"\"}, 0] }\n" +
      "                ]\n" +
      "            }\n" +
      "        } \n" +
      "    }"));
  }

  private void addSelectedFieldsFromActivityLookupResult() {
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $addFields: { '"+ACTIVITY_INFO+"': { $arrayElemAt: [\"$"+ACTIVITY_INFO+"\", 0]} }\n" +
      "    }"));
  }

}
