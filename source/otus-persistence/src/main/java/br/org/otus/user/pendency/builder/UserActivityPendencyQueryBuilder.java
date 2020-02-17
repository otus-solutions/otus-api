package br.org.otus.user.pendency.builder;

import br.org.otus.persistence.pendency.dto.SortingCriteria;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyDto;
import br.org.otus.persistence.pendency.dto.UserActivityPendencyRequestFilterDto;
import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserActivityPendencyQueryBuilder {

  public static final String ACTIVITY_ID_FIELD = "activityId";
  public static final String FINALIZED_STATUS = "FINALIZED";
  public static final String NO_STATUS = "";

  private static final String ACTIVITY_INFO = "activityInfo";

  private ArrayList<Bson> pipeline;

  public ArrayList<Bson> getAllPendenciesWithFiltersQuery(UserActivityPendencyDto userActivityPendencyDto) {
    return getPendenciesWithFiltersQuery(userActivityPendencyDto);
  }

  public ArrayList<Bson> getAllPendenciesByUserQuery(String userRole, String userEmail) {
    return getPendenciesByUserQuery(userRole, userEmail, NO_STATUS);
  }

  public ArrayList<Bson> getOpenedPendenciesByUserQuery(String userRole, String userEmail) {
    return getPendenciesByUserQuery(userRole, userEmail, getOpenedStatusCondition());
  }

  public ArrayList<Bson> getDonePendenciesByUserQuery(String userRole, String userEmail) {
    return getPendenciesByUserQuery(userRole, userEmail, getDoneStatusCondition());
  }

  private ArrayList<Bson> getPendenciesWithFiltersQuery(UserActivityPendencyDto userActivityPendencyDto) {
    pipeline = new ArrayList<>();
    addLookupMatchingActivityPendencyFilters(getStatusConditionFromDto(userActivityPendencyDto.getFilterDto().getStatus()));
    addMatchByPendencyFilters(getActivityFilterExpressionsFromDto(userActivityPendencyDto.getFilterDto()));
    addSelectedFieldsFromActivityLookupResult();
    addSkip(userActivityPendencyDto.getCurrentQuantity());
    addLimit(userActivityPendencyDto.getQuantityToGet());
    addSortingCriteria(userActivityPendencyDto.getSortingCriteria());
    return pipeline;
  }

  private ArrayList<Bson> getPendenciesByUserQuery(String userRole, String userEmail, String statusCondition) {
    pipeline = new ArrayList<>();
    addLookupMatchingActivityPendencyFilters(statusCondition);
    addMatchByPendencyUser(userRole, userEmail);
    addSelectedFieldsFromActivityLookupResult();
    return pipeline;
  }

  private String getStatusConditionFromDto(String userActivityPendencyDtoStatus){
    if(userActivityPendencyDtoStatus==null){
      return NO_STATUS;
    }
    Map<String, String> statusMap = new HashMap<>();
    statusMap.put(UserActivityPendencyRequestFilterDto.FINALIZED_STATUS, getDoneStatusCondition());
    statusMap.put(UserActivityPendencyRequestFilterDto.NOT_FINALIZED_STATUS, getOpenedStatusCondition());
    return statusMap.get(userActivityPendencyDtoStatus);
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

  private void addLookupMatchingActivityPendencyFilters(String activityFilterExpressions) {
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

  private void addMatchByPendencyFilters(String filterEquations) {
    try {
      pipeline.add(ParseQuery.toDocument("{ \n" +
        "        $match: {\n" +
        "            $expr: { \n" +
        "               $and: [ \n" +
//        "            " + filterEquations +
        "                 { $gt: [ { $size: \"$" + ACTIVITY_INFO + "\"}, 0] }\n" +
        "               ]\n" +
        "            }\n" +
        "        } \n" +
        "    }"));
    }catch (Exception e){
      throw e;
    }
  }

  private void addMatchByPendencyUser(String userRole, String userEmail) {
    pipeline.add(ParseQuery.toDocument("{ \n" +
      "        $match: {\n" +
      "            $expr: { \n" +
      "                $and: [\n" +
      "                    " + getFilterExpressionByUser(userRole, userEmail) + ",\n" +
      "                    { $gt: [ { $size: \"$"+ACTIVITY_INFO+"\"}, 0] }\n" +
      "                ]\n" +
      "            }\n" +
      "        } \n" +
      "    }"));
  }

  private String getFilterExpressionByUser(String userRole, String userEmail){
    return "{ $eq: [ \"$" + userRole + "\", " + userEmail + " ] }";
  }

  private void addSelectedFieldsFromActivityLookupResult() {
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $addFields: { '"+ACTIVITY_INFO+"': { $arrayElemAt: [\"$"+ACTIVITY_INFO+"\", 0]} }\n" +
      "    }"));
  }

  private String getActivityFilterExpressionsFromDto(UserActivityPendencyRequestFilterDto userActivityPendencyRequestFilterDto){
    if(userActivityPendencyRequestFilterDto==null){
      return "";
    }
    String filterExpressions = "";
    filterExpressions += getUserFilterFromDto("requester", userActivityPendencyRequestFilterDto.getRequesters());
    filterExpressions += getUserFilterFromDto("receiver", userActivityPendencyRequestFilterDto.getReceivers());
    filterExpressions += getActivityFilterFromDto("acronym", userActivityPendencyRequestFilterDto.getAcronym());
    filterExpressions += getActivityFilterFromDto("recruitmentNumber", userActivityPendencyRequestFilterDto.getRn());
    return filterExpressions;
  }
  private String getUserFilterFromDto(String userRole, String[] filterValues){
    try{
      return "{ $in: [ \"$"+userRole+"\", [ \"" + String.join("\", \"", filterValues) + "\" ] ] },";
    }catch (NullPointerException e){
      return "";
    }
  }
  private String getActivityFilterFromDto(String activityField, Object filterValue){
    try{
      return "{ $eq: [ \"$"+ACTIVITY_INFO+"."+activityField+"\": " + filterValue.toString() + " },";
    }catch (NullPointerException e){
      return "";
    }
  }

  private void addSkip(int quantityToSkip){
    pipeline.add(ParseQuery.toDocument("{ $skip: " +  quantityToSkip + " }"));
  }

  private void addLimit(int quantityToGet){
    pipeline.add(ParseQuery.toDocument("{ $limit: "+ quantityToGet +" }"));
  }

  private void addSortingCriteria(SortingCriteria[] sortingCriteria){
    if(sortingCriteria==null || sortingCriteria.length == 0){
      return;
    }
    String criteriaStr = "";
    for (SortingCriteria criteria: sortingCriteria) {
      criteriaStr += criteria.getFieldName() + ": " + criteria.getMode() + ",";
    }
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $sort: { " + criteriaStr + " }\n" +
      "    }"));
  }

}
